package com.aliat.alm.Parser;

import java.io.BufferedReader;
import java.io.File;
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
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LoadFilesNokia extends DefaultHandler {
	// updated by Bassam 09 FEB 2022
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
	static String readfileNokiafrom;
	static String vfolderfrom;
	static String Gprovider;
	static String copyfileNokiato;
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
	static HashMap<String, String> vhmap = new HashMap<String, String>();
	static HashMap<String, String> vhmap2 = new HashMap<String, String>();
	static HashMap<String, String> vhmap10 = new HashMap<String, String>();
	static HashMap<String, String> vhmap12 = new HashMap<String, String>();


	static HashMap<String, String> nodeIDhmap = new HashMap<String, String>();

	static HashMap<String, HashMap<String, String>>allModules = new HashMap<String, HashMap<String,String>>();
	static HashMap<String, HashMap<String, String>>allCabinets = new HashMap<String, HashMap<String,String>>();
	static HashMap<String, HashMap<String, String>>allFANs = new HashMap<String, HashMap<String,String>>();
	 
	static String nodeId = null;
	static String nodeType = null;
	static String nodeModel = null;
	static String nodeName = null;
	static String hww;
	static String fileNamess;
	static String unique_Node_ID = null;	
		
	//static ArrayList<String> nodeIds = new ArrayList<String>();
	static ArrayList<String> antennaSerialnb = new ArrayList<String>();
	
	static List<Map<String,HashMap<String, String> >> RETs = new ArrayList <Map<String,HashMap<String, String> >>();
	
	public static void main(String[] args) throws Exception, SAXException {
		
		objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/"+"almconfig.dat"));
		

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
			 if (data.contains("readfileNokiafrom")) {
				 data1=data.split(";",-1);
				 readfileNokiafrom=data1[1];
				 System.out.println("readfileNokiafrom found: " + readfileNokiafrom);
				 data2=readfileNokiafrom.split("\\\\",-1);
				 vfolderfrom=data2[data2.length-1];
				 System.out.println("data2 found :" + data2[data2.length-1]);
				 Gprovider=vfolderfrom.substring(0,5);
				 System.out.println("Gprovider2 found: " + Gprovider);
			 }
			 if (data.contains("copyfileNokiato")) {
				 data1=data.split(";",-1);
				 copyfileNokiato=data1[1];
				 System.out.println("copyfileNokiato found: " + copyfileNokiato);
				 data2=copyfileNokiato.split("\\\\",-1);
				 vfolderto=data2[data2.length-1];
			 }

	 	}
	 	 objReader1.close();
	 	 
		 System.out.println("get circle value  :" + System.getProperty("user.dir")+"/"+"almcircle.dat");
		 objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/"+"almcircle.dat"));
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
				String logfilename="ParserLogNokia-"+dtf.format(now)+".log";

				//get only year from today date
				DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		    	LocalDateTime now1 = LocalDateTime.now();
		    	Gyear=dtf1.format(now1).substring(0,4);
		    	//System.out.println(Gyear);

		    	File folder = new File(readfileNokiafrom);
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
			    	 String sqlStmtinit2 = "select * from EXECUTE_DOAMIN_VENDOR_FILES where DOMAIN='Mobile Access Domain' and VENDOR='"+ Gprovider +"' and STATUS='IN PROCESS'";
					    ResultSet rsinit2 = stmtinit2.executeQuery(sqlStmtinit2);
					    rsinit2.last();
				 	    int totalrecinit2 = rsinit2.getRow();
				 	   rsinit2.beforeFirst();
				 	   if (totalrecinit2 == 0 ) {
				 		  PreparedStatement stmtinit = con.prepareStatement("insert into EXECUTE_DOAMIN_VENDOR_FILES (DOMAIN,VENDOR,CREATION_DATE,STATUS) values ('Mobile Access Domain', '"+ Gprovider +"',sysdate,'IN PROCESS')");
							 stmtinit.executeUpdate();
							 stmtinit.close();

							 Date before = new Date();
							 System.out.println("LoadFilesNokia inprocess...");
							 System.out.println("It will take a few minutes..");
							 logger.info("LoadFilesNokia inprocess...");
								for (File file : listOfFiles) {
									if (file.isFile()) {
										
										allFANs.clear(); allCabinets.clear(); allModules.clear(); 
										nodeIDhmap.clear(); 

								        String fichier =file.getName().toString();
								        fileNamess = file.getName().toString();
										// reading file from folder
								        readfile(fichier);

										 File source = new File(readfileNokiafrom+"/"+file.getName());
									     File dest = new File(copyfileNokiato+"/"+file.getName()+".bkp");

									     //coypy file after treating it to destination folder
									     copyFiles(source,dest);
                                         //delete file from source folder
									     deleteFiles(readfileNokiafrom+"/"+file.getName());


								    }
								}
                                // remove dupliacte node 
								GetduplicateFilename("Mobile Access Domain",Gprovider);
								// update file status to completed
								 stmtinit = con.prepareStatement("update EXECUTE_DOAMIN_VENDOR_FILES set STATUS ='COMPLETED' where DOMAIN='Mobile Access Domain' and VENDOR='"+ Gprovider +"' and STATUS='IN PROCESS'");
								 stmtinit.executeUpdate();
								 stmtinit.close();
								 
								 System.out.println("LoadFilesNokia completed");
								 logger.info("LoadFilesNokia completed");
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
	public static String readfile (String filename) throws Exception, SAXException {

	   	SAXParserFactory factory = SAXParserFactory.newInstance();
	   	
	   	// use this one to avoid this line in xml file <!DOCTYPE raml SYSTEM 'raml20.dtd'>
	   	factory.setValidating(false);
	  	factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

	   	SAXParser parser = factory.newSAXParser();
	   	parser.parse(new File(projpath+"/"+vfolderfrom+"/"+filename), new LoadFilesNokia());
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
	 
	 
	   private StringBuilder currentValue = new StringBuilder();
	 
	   private String tagName;
	   private String id;
	   private String attrName;
	   private String clazz;
	   private String distName;
	   private String tversion;
	   

	   public String getId() {
	   	return id;
	   }

	   public void setId(String id) {
	   	this.id = id;
	   }

	   public String getClazz() {
		return clazz;
	   }
	   
	   public void setClazz(String clazz) {
		this.clazz = clazz;
	   }
	
	   public String getDistName() {
		   	return distName;
		   }

	   public void setDistName(String distName) {
	   	this.distName = distName;
	   }

	   public String getTagName() {
	   	return tagName;
	   }

	   public void setTagName(String tagName) {
	   	this.tagName = tagName;
	   }

	   public void setAttrName(String attrName) {
		   	this.attrName = attrName;
		   }
	   public String getAttrName() {
		   	return attrName;
		   }
	   
	   public void setVersion(String tversion) {
		   	this.tversion = tversion;
		   }
	   public String getVersion() {
		   	return tversion;
		   }
	   
	   @Override
	   public void startDocument() throws SAXException {

		  // System.out.println("LoadFilesNokia inprocess...");
	   }
	   
	   @SuppressWarnings("rawtypes")
	@Override
	   public void endDocument() throws SAXException {
		 
		   try {
	   		   // update Atenna status = 1 where USERLABEL =INVENTORYUNITTYPE in NODE_ANTENNA
			   String InsertQry="Update NODE_MODULE SET ANTENNA_STATUS = '1' WHERE USERLABEL is not null and userlabel <> 'null' and USERLABEL IN (SELECT INVENTORYUNITTYPE FROM NODE_ANTENNA)";
			   
		   		 PreparedStatement stamtattr = con.prepareStatement(InsertQry); 
		   		stamtattr.executeUpdate();
		   		stamtattr.close();
		   		
			   allFANs.keySet().removeAll(vhmap12.keySet());
			   
		   		for (Map.Entry fan : allFANs.entrySet()) {
		   			
					 String parentDN = "PLMN-PLMN/"+allFANs.get(fan.getKey()).get("parentDN");
					 String distname = allFANs.get(fan.getKey()).get("distName");
					 
			    	 if((allCabinets.get(parentDN)) != null) {
			    		 
							String invUnitID = distname.substring(distname.indexOf("INVUNIT")+8, distname.length());
		
							String cabNo = parentDN.substring(parentDN.indexOf("INVUNIT")+8, parentDN.length());
							
					   		String distNamee = distname.substring(distname.indexOf("PLMN-PLMN")+10, distname.length());
					   		
							vcodeid= localgetseqNbr(25);  /// 25 to select module_id   allFANs.get(fan.getKey()).get("versionNumber")
				     		   vcodeid=Gyear+"_"+ "MODULE"+'_'+vcodeid;
							 String InsertQuery = "Insert into NODE_MODULE (MODULE_ID,CABINETNO,MODULENO,INVUNITID,IDENTIFICATIONCODE,CONFIGDN,INVUNITTYPE,PARENTDN,RUNTIMEDN,"
							 		+ "SERIALNUMBER,STATE,UNITPOSITION,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,SUBRACK_SPECIFIC_TYPE,USERLABEL,VENDORNAME,"
							 		+ "VERSION,DISTNAME,NODE_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,"
							 		+ "CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE)"
							 		+ " values ('"+vcodeid+"','"+cabNo+"','"+invUnitID+"','"+invUnitID+"','','"+allFANs.get(fan.getKey()).get("configDN")+"','"+allFANs.get(fan.getKey()).get("inventoryUnitType")+"',"
							 				+ "'"+allFANs.get(fan.getKey()).get("parentDN")+"','"+allFANs.get(fan.getKey()).get("runtimeDN")+"','"+allFANs.get(fan.getKey()).get("serialNumber")+"','"+allFANs.get(fan.getKey()).get("state")+"',"
							 						+ "'"+allFANs.get(fan.getKey()).get("unitPosition")+"','"+allFANs.get(fan.getKey()).get("vendorUnitFamilyType")+"','"+allFANs.get(fan.getKey()).get("vendorUnitTypeNumber")+"','',"
							 				+ "'','"+allFANs.get(fan.getKey()).get("vendorName")+"','"+allFANs.get(fan.getKey()).get("versionNumber")+"','"+distNamee+"',"
							 				+ "'"+allFANs.get(fan.getKey()).get("nodeID")+"',sysdate,'"+fileNamess+"','0','0','0','0','0','1',sysdate,'Mobile Access Domain','"+Gprovider+"','0')";
							 PreparedStatement stmtattr = con.prepareStatement(InsertQuery); 
					   		 stmtattr.executeUpdate();
					   		 stmtattr.close();
					   }
					    	 
					   if((allModules.get(parentDN)) != null) {
		
					    	 	HashMap<String, String> vhmap13 = new HashMap<String, String>();
					    	 	vhmap13.putAll(allModules.get(parentDN));
					    	 	String cabno = vhmap13.get("parentDN");
					    		 
								String cabNo = cabno.substring(cabno.indexOf("INVUNIT")+8, cabno.length());
								String invUnitID = distname.substring(distname.indexOf("INVUNIT")+8, distname.length());
								String modNo = parentDN.substring(parentDN.indexOf("INVUNIT")+8, parentDN.length());
							   	String distNamee = distname.substring(distname.indexOf("PLMN-PLMN")+10, distname.length());
							   		
								vcodeid= localgetseqNbr(26);  /// 26 to select submodule_id   allFANs.get(fan.getKey()).get("versionNumber")
					     		   vcodeid=Gyear+"_"+ "SUBMODULE"+'_'+vcodeid;
								String InsertQuery = "Insert into NODE_SUBMODULE (SUBMODULE_ID,CABINETNO,MODULENO,SUBMODULENO,IDENTIFICATIONCODE,INVUNITID,CONFIGDN,PARENTDN,RUNTIMEDN,SERIALNUMBER,"
										+ "UNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,"
										+ "VERSION,DISTNAME,NODE_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,"
										+ "CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE) "
										+ "values ('"+vcodeid+"','"+cabNo+"','"+modNo+"','"+invUnitID+"','','"+invUnitID+"','"+allFANs.get(fan.getKey()).get("configDN")+"','"+allFANs.get(fan.getKey()).get("parentDN")+"','"+allFANs.get(fan.getKey()).get("runtimeDN")+"','"+allFANs.get(fan.getKey()).get("serialNumber")+"',"
												+ "'"+allFANs.get(fan.getKey()).get("inventoryUnitType")+"','"+allFANs.get(fan.getKey()).get("vendorUnitFamilyType")+"','"+allFANs.get(fan.getKey()).get("vendorUnitTypeNumber")+"',"
														+ "'"+allFANs.get(fan.getKey()).get("vendorName")+"','"+allFANs.get(fan.getKey()).get("versionNumber")+"','"+distNamee+"','"+allFANs.get(fan.getKey()).get("nodeID")+"',"
												+ "sysdate,'"+fileNamess+"','0','0','0','0','0','1',sysdate,'Mobile Access Domain','"+Gprovider+"','0')";
								
								 PreparedStatement stmtattr = con.prepareStatement(InsertQuery); 
						   		 stmtattr.executeUpdate();
						   		 stmtattr.close();
					    }
		   		}

		   } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
               
	   }
	   
	   @Override
	   public void startElement(String uri, String localName, String qName,
	   		Attributes attributes) throws SAXException {
		   
	   	if(qName.equals("managedObject")){
	   		int size=attributes.getLength();
	   		StringBuffer stringBuffer =new StringBuffer();

	   		vhmap.clear();
	   		for(int i=0;i<size;i++){

	   			stringBuffer.append(attributes.getValue(i)+",");
	   			if(i == size-1){
	   				this.id  = attributes.getValue(3); //read id
	   				this.distName = attributes.getValue(2); //read destination name
	   				this.clazz = attributes.getValue(0); // read type HW means new
	   				this.tversion = attributes.getValue(1); // read version
	   			}
	   		}
	   		vhmap2.clear();
	   	}
	  	if(qName.equals("p")) {
	   		int sizee = attributes.getLength();
	   		StringBuffer stringBuffer =new StringBuffer();
	   		for(int i=0;i<sizee;i++){



	   			stringBuffer.append(attributes.getValue(i)+","+attributes.getQName(i)+","+currentValue.toString()+",");
	   			if(i == sizee-1){

	   				this.attrName  = attributes.getValue(i);
	   				this.tagName = attributes.getValue(i);

	   			}
	   		}
	   	}
	  	
	  	if(qName.equals("list")) {
	   		int sizee = attributes.getLength();
	   		StringBuffer stringBuffer =new StringBuffer();
	   		for(int i=0;i<sizee;i++){


	   			stringBuffer.append(attributes.getValue(i)+",");
	   			if(i == sizee-1){
	   				this.attrName  = attributes.getValue(i);
	   			}
	   			
	   			//System.out.println("stringBuffer3: "+stringBuffer.toString());
	   		}
	   	}
	  	this.tagName = qName;

}
	   
	   
	   
	   
	   
	   @Override
	   public void endElement(String uri, String localName, String qName)
	   		throws SAXException {

	   	try {
	   	if(qName.equals("managedObject")){
   		vhmap2.putAll(vhmap);
   		  		
   		ObjectMapper mapper = new ObjectMapper();
   		//System.out.println("vhmap is " +vhmap.toString());

   		//System.out.println("distName "+ distName);
   		String[] arrOfValuesdistName = getSplitdistName(distName);
   		
			String arr = mapper.writeValueAsString(arrOfValuesdistName);
			
	   		if(arr.contains("BSC") && arr.contains("BCF"))
	   		{
	   		    System.out.println("step 1");
	   			//System.out.println("BSC and BCF"+mapper.writeValueAsString(arrOfValuesdistName));
	   			String[] bsc = arrOfValuesdistName[1].split("-", -1);
	   			String[] bcf = arrOfValuesdistName[2].split("-", -1);
	   			String ndID1 = bsc[1];
	   			String ndID2 = bcf[1];
	   			String NodeIDD = ndID1 +"-"+ ndID2;
	   			//System.out.println("NooooooooodeIDDDD "+NodeIDD);
	   			nodeId = NodeIDD;
	   			if(vhmap2.containsKey("systemTitle"))
	   			nodeModel = vhmap2.get("systemTitle");
	   			else {
	   				nodeModel = "";
	   			}
	   			//nodeName = arrOfValuesdistName[1] +"/"+ arrOfValuesdistName[2];
	   			nodeType = "BTS";
	   			unique_Node_ID = arrOfValuesdistName[1] + "_" + arrOfValuesdistName[2];
	   			
	   		}
	   		if(arr.contains("BSC") && !arr.contains("BCF"))
	   		{
	   			System.out.println("step 2");
	   			//System.out.println("BSC "+mapper.writeValueAsString(arrOfValuesdistName));
	   			String[] bscc = arrOfValuesdistName[1].split("-", -1);
	   			nodeId = bscc[1];
	   			nodeModel = bscc[0];
	   			//nodeName = arrOfValuesdistName[1];
	   			nodeType = "BSC";
	   			unique_Node_ID = arrOfValuesdistName[1] ;
	   			
	   		}
	   		if(arr.contains("RNC") && arr.contains("WBTS"))
	   		{
	   			System.out.println("step 3");
	   			String[] rnc = arrOfValuesdistName[1].split("-", -1);
	   			String[] wbts = arrOfValuesdistName[2].split("-", -1);
	   			String ndID1 = rnc[1];
	   			String ndID2 = wbts[1];
	   			String NodeIDD = ndID1 +"-"+ ndID2;
	   			nodeId = NodeIDD;

	   			if(vhmap2.containsKey("systemTitle"))
		   			nodeModel = vhmap2.get("systemTitle");
	   			else {
	   				nodeModel = "";
	   			}
	   			
	   			//nodeName = arrOfValuesdistName[1] +"/"+ arrOfValuesdistName[2];
	   			nodeType = "NodeB";
	   			unique_Node_ID = arrOfValuesdistName[1] + "_" + arrOfValuesdistName[2];
	   		}
	   		if(arr.contains("RNC") && !arr.contains("WBTS"))
	   		{
	   			System.out.println("step 4");
	   			String[] rncc = arrOfValuesdistName[1].split("-", -1);
	   			nodeId = rncc[1];
	   			nodeModel = rncc[0];
	   			
	   			//nodeName = arrOfValuesdistName[1];
	   			nodeType = "RNC";
	   			unique_Node_ID = arrOfValuesdistName[1];
	   		}
	   		
	   		if(arr.contains("MRBTS"))
	   		{
	   			System.out.println("step 5");
	   			//System.out.println("BSC "+mapper.writeValueAsString(arrOfValuesdistName));
	   			String[] mrbts = arrOfValuesdistName[1].split("-", -1);
	   			nodeId = mrbts[1];

	   			if(vhmap2.containsKey("systemTitle"))
		   			nodeModel = vhmap2.get("systemTitle");
	   			else {
	   				nodeModel = "";
	   			}
	   			
	   			//nodeName = arrOfValuesdistName[1];
	   			nodeType = "eNodeB";
	   			unique_Node_ID = arrOfValuesdistName[1];
	   		}
			
			if(StringUtils.equalsIgnoreCase(clazz,"HW") )
			{
			    System.out.println("Type Clazz 1");
				//if(!nodeIds.contains(nodeId)) dont add it as new node
				if(!nodeIDhmap.containsKey("nodeId"))
				{
					//nodeIds.add(nodeId);
					
					 String codeid= localgetseqNbr(0);
					 codeid=Gyear+"_"+ "NODE"+'_'+codeid;
					 PreparedStatement stmt = con.prepareStatement("insert into NODE_ACTIVE (NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_SOURCE,NODE_MODEL,TECH_2G,TECH_3G,TECH_4G,TECH_5G,SITE_ID,CIRCLE_ID,CREATION_DATE,UPDATE_DATE,FILE_TYPE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,WARE_ID,VENDOR,SUPPLIER_ID,WARE_NAME,SUPPLIER_NAME  )"
					 		+ "values('" + codeid +"', '"+unique_Node_ID+"' ,'" + nodeId +"' ,'"+unique_Node_ID+"','"+nodeType+"','Mobile Access Domain','0','"+vhmap2.get("systemTitle")+"','0','0','0','0','"+vhmap2.get("locationName")+"','"+ circleid +"',sysdate,sysdate,'0','" + fileNamess +"','0','0','0','0','0','0','1','0','0','"+ Gprovider +"','0','"+vhmap2.get("locationName")+"','0') "); 
	                stmt.executeUpdate();
				     stmt.close();
				     
				     nodeIDhmap.put(nodeId,codeid);
				     
				     
				  // add NODE_HOSTVER and add new row in node_attribut when version is null
				     if ((vhmap2.get("systemReleaseVersion") !=null) && (vhmap2.get("softwareReleaseVersion")!= null)) {
						   //add in NODE_ACTIVE_ATTRIBUTE the new attribute HOSTVER
							Gcodeattributid="0";  // initialize Attributte primary Key
							addnewattribut ("NODE_HOSTVER","HOSTVER",codeid,nodeType,fileNamess,Gprovider );
							
							// get line field sequence number from cabinet based on nodepk
						   	vline=getlinefromtable("NODE_HOSTVER",codeid);
						   	
						   	 //get primary code for node_hostver
						     String hostcodeid= localgetseqNbr(8);
						     hostcodeid=Gyear+"_"+ "HOSTVER"+'_'+hostcodeid;
							 stmt = con.prepareStatement("insert into NODE_HOSTVER (HOSTVER_ID,HOSTVERTYPE,HOSTVER,SDESC,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE)"
							 		+ "values('" + hostcodeid +"', '"+vhmap2.get("systemReleaseVersion")+"' ,'" + vhmap2.get("softwareReleaseVersion") +"' ,'0','"+codeid+"','"+ Gcodeattributid + "',sysdate,'"+ fileNamess +"','0','0','0','0','0','1','"+ vline +"',sysdate,'Mobile Access Domain','"+ Gprovider +"','0') "); 
			                stmt.executeUpdate();
						    stmt.close(); 
				     }
				     
				  

				}

			}
				
			String InsertQuery = null;
			String todaydate = null;
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        	LocalDateTime now = LocalDateTime.now();
        	todaydate=dtf.format(now);
        	
			if(StringUtils.equalsIgnoreCase(this.clazz,"HW") && (((this.distName).contains("BSC") && (this.distName).contains("BCF")) || ((this.distName).contains("RNC") && (this.distName).contains("WBTS") )))
			{
				System.out.println("Type Clazz 2");
				String cabinetnb=distName.substring(distName.indexOf("HW")+3, distName.length());
					
				String distNamee = distName.substring(distName.indexOf("PLMN-PLMN")+10, distName.length());
					
				//add in NODE_ACTIVE_ATTRIBUTE the new attribute Cabinet
				Gcodeattributid="0";  // initialize Attributte primary Key
				addnewattribut ("NODE_CABINET","CABINET",nodeIDhmap.get(nodeId),nodeType,fileNamess,Gprovider );
				
				// get line field sequence number from cabinet based on nodepk
		    	vline=getlinefromtable("NODE_CABINET",nodeIDhmap.get(nodeId));
				
				 vcodeid= localgetseqNbr(7);  /// 7 to select cabinet_id 
		   		   vcodeid=Gyear+"_"+ "CABINET"+'_'+vcodeid;
		   		   InsertQuery="insert into NODE_CABINET (CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,"
		   		   		+ "VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,"
		   		   		+ "BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,"
		   		   		+ "TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR) "
		   		   		+ " values('" + vcodeid +"','"+ vhmap2.get("locationName") +"','"+cabinetnb+"','0',"
		   		   				+ "'0','0','0',"
		   		   				+ "'0','0',"
		   		   				+ "'" + vhmap2.get("vendorName") +"','" + vhmap2.get("SERIALNUMBER") +"','0',"
		   		   				+ "DATE '"+todaydate+"',DATE '"+todaydate+"',"
		   		   				+ " '"+distNamee+"','0','0',"
		   		   				+ "'0','0','"+vhmap2.get("cabinetType")+"',"
		   		   				+ "'0','0','0',"
		   		   				+ "'0','"+nodeIDhmap.get(nodeId)+"','"+ Gcodeattributid +"',sysdate,'" + fileNamess +"',"
		   		   				+ "'0','0','0','0','0','0','"+ vline +"','1',"
		   		   						+ "'0',sysdate,'Mobile Access Domain','" + Gprovider +"') ";
					
					   		 PreparedStatement stmtattr = con.prepareStatement(InsertQuery); 
					   		 stmtattr.executeUpdate();
					   		 stmtattr.close();
			}
			
			if(StringUtils.equalsIgnoreCase(this.clazz,"SUBRACK"))
			{
				System.out.println("Type Clazz 3");
				String[] valuesdistName = getSplitdistName(distName);

		   		String cabNumber = valuesdistName[3];
		   		cabNumber=cabNumber.substring(cabNumber.indexOf("HW")+3, cabNumber.length());
		   		
		   		String subRackNb = valuesdistName[4];
		   		subRackNb=subRackNb.substring(subRackNb.indexOf("SUBRACK")+8, subRackNb.length());
		   	
		   		String distNamee = distName.substring(distName.indexOf("PLMN-PLMN")+10, distName.length());
		   		
		   		
		   	    //add in NODE_ACTIVE_ATTRIBUTE the new attribute SUBRACK
				Gcodeattributid="0";  // initialize Attributte primary Key
				addnewattribut ("NODE_SUBRACK","SUBRACK",nodeIDhmap.get(nodeId),nodeType,fileNamess,Gprovider );
				
				
				// get line field sequence number from SUBRACK based on nodepk
		    	vline=getlinefromtable("NODE_SUBRACK",nodeIDhmap.get(nodeId));
				
				
     		   vcodeid= localgetseqNbr(11);  /// 11 to select subrack_id  
     		   vcodeid=Gyear+"_"+ "SUBRACK"+'_'+vcodeid;
 			   InsertQuery="insert into NODE_SUBRACK (SUBRACK_ID,SITEINDEX,CABINETNO,SUBRACKNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,FRAMETYPE,RACKFRAMENO,MODULENO,"
 			   		+ "INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,"
 			   		+ "UNITPOSITION,MANUFACTURERDATA,USERLABEL,BOMCODE,MODEL,ISSUENUMBER,BOMFRAMETYPE,CLEICODE,BOM,EXTINFO,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,"
 			   		+ "STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
 			   		+ "values('" + vcodeid +"','" + vhmap2.get("locationName") +"','" + cabNumber +"','" + subRackNb +"',"
 			   		+ "'0','" + vhmap2.get("subrackSpecificType") +"','0','0',"
 			   		+ "'0','0','0', '0',"
 			   		+ " '0','" + vhmap2.get("vendorName") +"','0','" + vhmap2.get("version") +"',"
 			   		+ "DATE '" + todaydate +"',DATE '" + todaydate +"','"+distNamee+"',"
 			   		+ "'0','0','0','0',"
 			   		+ "'0','0','0','0',"
 			   		+ "'0','" + nodeIDhmap.get(nodeId) +"','"+ Gcodeattributid +"',sysdate,'" + fileNamess +"','0','0','0','0',"
 			   		+ "'0','0' ,'"+ vline +"','1','Mobile Access Domain','" + Gprovider +"') ";
 			   
		   		 PreparedStatement stmtattr = con.prepareStatement(InsertQuery); 
		   		 stmtattr.executeUpdate();
		   		 stmtattr.close();

			}
			
			if(StringUtils.equalsIgnoreCase(this.clazz,"CABINET"))
			{
				System.out.println("Type Clazz 4");
				String cabinetnb = null;
				cabinetnb=distName.substring(distName.indexOf("CABINET")+8, distName.length());
					
					String distNamee = distName.substring(distName.indexOf("PLMN-PLMN")+10, distName.length());
					
					//add in NODE_ACTIVE_ATTRIBUTE the new attribute Cabinet
					Gcodeattributid="0";  // initialize Attributte primary Key
					addnewattribut ("NODE_CABINET","CABINET",nodeIDhmap.get(nodeId),nodeType,fileNamess,Gprovider );
					
					// get line field sequence number from cabinet based on nodepk
			    	vline=getlinefromtable("NODE_CABINET",nodeIDhmap.get(nodeId));
					
					
			  vcodeid= localgetseqNbr(7);  /// 7 to select cabinet_id 
   		   vcodeid=Gyear+"_"+ "CABINET"+'_'+vcodeid;
   		   InsertQuery="insert into NODE_CABINET (CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,"
   		   		+ "VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,"
   		   		+ "BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,"
   		   		+ "TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR) "
   		   		+ " values('" + vcodeid +"','"+ vhmap2.get("locationName") +"','"+cabinetnb+"','0',"
   		   				+ "'0','0','0',"
   		   				+ "'0','0',"
   		   				+ "'" + vhmap2.get("vendorName") +"','" + vhmap2.get("SERIALNUMBER") +"','0',"
   		   				+ "DATE '"+todaydate+"',DATE '"+todaydate+"',"
   		   				+ " '"+distNamee+"','0','0',"
   		   				+ "'0','0','"+vhmap2.get("cabinetType")+"',"
   		   				+ "'0','0','0',"
   		   				+ "'0','"+nodeIDhmap.get(nodeId)+"','"+ Gcodeattributid +"',sysdate,'" + fileNamess +"',"
   		   				+ "'0','0','0','0','0','0','"+ vline +"','1',"
   		   						+ "'0',sysdate,'Mobile Access Domain','" + Gprovider +"') ";
			
			   		 PreparedStatement stmtattr = con.prepareStatement(InsertQuery); 
			   		 stmtattr.executeUpdate();
			   		 stmtattr.close();
			}
			
			if(StringUtils.equalsIgnoreCase(this.clazz,"CARTRIDGE") )
			{
				System.out.println("Type Clazz 5");
		   		String[] valuesdistName = getSplitdistName(distName);

		   		String cabNumber = valuesdistName[3];
		   		cabNumber=cabNumber.substring(cabNumber.indexOf("CABINET")+8, cabNumber.length());
		   		
		   		String rackNb = valuesdistName[4];
		   		rackNb=rackNb.substring(rackNb.indexOf("CARTRIDGE")+10, rackNb.length());
		   		
		   		String distNamee = distName.substring(distName.indexOf("PLMN-PLMN")+10, distName.length());
		   		
		   	   //add in NODE_ACTIVE_ATTRIBUTE the new attribute SUBRACK
				Gcodeattributid="0";  // initialize Attributte primary Key
				addnewattribut ("NODE_SUBRACK","SUBRACK",nodeIDhmap.get(nodeId),nodeType,fileNamess,Gprovider );
				
				
				// get line field sequence number from SUBRACK based on nodepk
		    	vline=getlinefromtable("NODE_SUBRACK",nodeIDhmap.get(nodeId));
		   		
	     		   vcodeid= localgetseqNbr(11);  /// 11 to select subrack_id  
	     		   vcodeid=Gyear+"_"+ "SUBRACK"+'_'+vcodeid;
	 			   InsertQuery="insert into NODE_SUBRACK (SUBRACK_ID,SITEINDEX,CABINETNO,SUBRACKNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,FRAMETYPE,RACKFRAMENO,MODULENO,"
	 			   		+ "INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,"
	 			   		+ "UNITPOSITION,MANUFACTURERDATA,USERLABEL,BOMCODE,MODEL,ISSUENUMBER,BOMFRAMETYPE,CLEICODE,BOM,EXTINFO,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,"
	 			   		+ "STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
	 			   		+ "values('" + vcodeid +"','"+vhmap2.get("locationName")+"','" + cabNumber +"','" + rackNb +"',"
	 			   		+ "'0','"+vhmap2.get("cartridgeType")+"','0','0',"
	 			   		+ "'0','0','0', '0',"
	 			   		+ " '0','" + vhmap2.get("vendorName") +"','0','0',"
	 			   		+ "DATE '" + todaydate +"',DATE '" + todaydate +"','"+distNamee+"',"
	 			   		+ "'0','0','0','0',"
	 			   		+ "'0','0','0','0',"
	 			   		+ "'0','" + nodeIDhmap.get(nodeId) +"','"+ Gcodeattributid +"',sysdate,'" + fileNamess +"','0','0','0','0',"
	 			   		+ "'0','0' ,'"+ vline +"','1','Mobile Access Domain','" + Gprovider +"') ";
	 			   
			   		 PreparedStatement stmtattr = con.prepareStatement(InsertQuery); 
			   		 stmtattr.executeUpdate();
			   		 stmtattr.close();
			}
			
			if(StringUtils.equalsIgnoreCase(this.clazz,"RACK") )
			{
				System.out.println("Type Clazz 6");
		   		String[] valuesdistName = getSplitdistName(distName);

		   		String cabNumber = valuesdistName[3];
		   		cabNumber=cabNumber.substring(cabNumber.indexOf("CABINET")+8, cabNumber.length());
		   		
		   		String rackNb = valuesdistName[4];
		   		rackNb=rackNb.substring(rackNb.indexOf("RACK")+5, rackNb.length());
		   		
		   		String distNamee = distName.substring(distName.indexOf("PLMN-PLMN")+10, distName.length());
		   		
		   	    //add in NODE_ACTIVE_ATTRIBUTE the new attribute SUBRACK
				Gcodeattributid="0";  // initialize Attributte primary Key
				addnewattribut ("NODE_SUBRACK","SUBRACK",nodeIDhmap.get(nodeId),nodeType,fileNamess,Gprovider );
				
				
				// get line field sequence number from SUBRACK based on nodepk
		    	vline=getlinefromtable("NODE_SUBRACK",nodeIDhmap.get(nodeId));
		   		
	     		   vcodeid= localgetseqNbr(11);  /// 11 to select SUBrack_id  
	     		   vcodeid=Gyear+"_"+ "SUBRACK"+'_'+vcodeid;
	 			   InsertQuery="insert into NODE_SUBRACK (SUBRACK_ID,SITEINDEX,CABINETNO,SUBRACKNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,FRAMETYPE,RACKFRAMENO,MODULENO,"
	 			   		+ "INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,"
	 			   		+ "UNITPOSITION,MANUFACTURERDATA,USERLABEL,BOMCODE,MODEL,ISSUENUMBER,BOMFRAMETYPE,CLEICODE,BOM,EXTINFO,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,"
	 			   		+ "STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,DOMAIN,VENDOR) "
	 			   		+ "values('" + vcodeid +"','0','" + cabNumber +"','" + rackNb +"',"
	 			   		+ "'0','0','0','0',"
	 			   		+ "'0','0','0', '0',"
	 			   		+ " '0','" + vhmap2.get("vendorName") +"','0','0',"
	 			   		+ "DATE '" + todaydate +"',DATE '" + todaydate +"','0',"
	 			   		+ "'"+distNamee+"','0','0','0',"
	 			   		+ "'0','0','0','0',"
	 			   		+ "'0','" + nodeIDhmap.get(nodeId) +"','"+ Gcodeattributid + "',sysdate,'" + fileNamess +"','0','0','0','0',"
	 			   		+ "'0','0' ,'"+ vline +"','1','Mobile Access Domain','" + Gprovider +"') ";
	 			   
			   		 PreparedStatement stmtattr = con.prepareStatement(InsertQuery); 
			   		 stmtattr.executeUpdate();
			   		 stmtattr.close();
			}
			
			if(StringUtils.equalsIgnoreCase(this.clazz,"UNIT") && (this.distName).contains("RNC") && !(this.distName).contains("WBTS") )
			{
				System.out.println("Type Clazz 7");
				String[] valuesdistName = getSplitdistName(distName);

		   		String cabNumber = valuesdistName[3];
		   		cabNumber=cabNumber.substring(cabNumber.indexOf("CABINET")+8, cabNumber.length());
		   		
		   		String rackNb = valuesdistName[4];
		   		rackNb=rackNb.substring(rackNb.indexOf("RACK")+5, rackNb.length());
		   		
		   		String unitTypeslot = valuesdistName[5];
		   		unitTypeslot=unitTypeslot.substring(unitTypeslot.indexOf("UNIT")+5, unitTypeslot.length());
		   		
		   		String slotnb = null;String unittype = null;
		   		if(unitTypeslot.contains("motherboard")) 
		   		{
		   			slotnb=unitTypeslot.substring(unitTypeslot.indexOf("motherboard")+12, unitTypeslot.length());unittype = "motherboard";}
		   		if(unitTypeslot.contains("slot")) {slotnb=unitTypeslot.substring(unitTypeslot.indexOf("slot")+5, unitTypeslot.length());unittype = "slot";}
		   		if(unitTypeslot.contains("AMC")) {slotnb=unitTypeslot.substring(unitTypeslot.indexOf("AMC")+4, unitTypeslot.length());unittype = "AMC";}
		   		if(unitTypeslot.contains("power-supply")) {slotnb=unitTypeslot.substring(unitTypeslot.indexOf("power-supply")+13, unitTypeslot.length());unittype = "power-supply";}
		   		if(unitTypeslot.contains("ft")) {slotnb=unitTypeslot.substring(unitTypeslot.indexOf("ft")+3, unitTypeslot.length());unittype = "ft";}
		   		
		   		String distNamee = distName.substring(distName.indexOf("PLMN-PLMN")+10, distName.length());
		   		
		   	   //add in NODE_ACTIVE_ATTRIBUTE the new attribute BOARD
				Gcodeattributid="0";  // initialize Attributte primary Key
				addnewattribut ("NODE_BOARD","BOARD",nodeIDhmap.get(nodeId),nodeType,fileNamess,Gprovider );
				
				
				// get line field sequence number from SUBRACK based on nodepk
		    	vline=getlinefromtable("NODE_BOARD",nodeIDhmap.get(nodeId));

				   vcodeid= localgetseqNbr(5);  /// 5 to select Board_id 
	     		   vcodeid=Gyear+"_"+ "BOARD"+'_'+vcodeid;
	     		   
					 InsertQuery="insert into NODE_BOARD (BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,"
						 		+ "BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,"
						 		+ "DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,"
						 		+ "EXTINFO,APDEVINFO,WORKMODE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,"
						 		+ "TRANS_TYPE,LINE,ACTIVE_RECORD,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR) "
		         		   		+ "values('" + vcodeid +"','0','"+cabNumber+"','0',"
		         		   				+ "'"+rackNb+"','0','"+slotnb+"','0',"
		         		   				+ "'0','0','0',"
		         		   				+ "'0','" + vhmap2.get("unitType") +"','" + unittype +"',"
		         		   				+ " '0', '0', "
		         		   				+ "'"+vhmap2.get("vendorName")+"', '" + vhmap2.get("serialNumber") +"','" + vhmap2.get("version") +"',"
		         		   				+ " DATE '"+todaydate+"',DATE '"+todaydate+"','"+distNamee+"',"
		         		   				+ "'0','0','0','0',"
		         		   				+ "'0','0','0','0',"
		         		   				+ "'" + vhmap2.get("identificationCode") +"','0','0','0',"
		         		   				+ "'0','0','" + nodeIDhmap.get(nodeId) +"','"+ Gcodeattributid +"' ,sysdate,'" + fileNamess +"',"
		         		   				+ "'0','0','0','0','0','0',"
		         		   				+ "'"+ vline +"','1','0',sysdate,'Mobile Access Domain','" + Gprovider +"') ";
					 PreparedStatement stmtattr = con.prepareStatement(InsertQuery); 
			   		 stmtattr.executeUpdate();
			   		 stmtattr.close();
			}
			if(StringUtils.equalsIgnoreCase(this.clazz,"UNIT") && (this.distName).contains("BSC") && (this.distName).contains("BCF") )
			{
				System.out.println("Type Clazz 8");
		   		String[] valuesdistName = getSplitdistName(distName);
		   		String cabNumber = valuesdistName[3];
		   		cabNumber=cabNumber.substring(cabNumber.indexOf("HW")+3, cabNumber.length());
		   		String subRackNb = valuesdistName[4];
		   		subRackNb=subRackNb.substring(subRackNb.indexOf("SUBRACK")+8, subRackNb.length());
		   		String distNamee = distName.substring(distName.indexOf("PLMN-PLMN")+10, distName.length());
				vcodeid= localgetseqNbr(5);  /// 5 to select Board_id 
				
				
				//add in NODE_ACTIVE_ATTRIBUTE the new attribute BOARD
				Gcodeattributid="0";  // initialize Attributte primary Key
				addnewattribut ("NODE_BOARD","BOARD",nodeIDhmap.get(nodeId),nodeType,fileNamess,Gprovider );
				
				
				// get line field sequence number from SUBRACK based on nodepk
		    	vline=getlinefromtable("NODE_BOARD",nodeIDhmap.get(nodeId));
				
     		    vcodeid=Gyear+"_"+ "BOARD"+'_'+vcodeid;
				 InsertQuery="insert into NODE_BOARD (BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,"
					 		+ "BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,"
					 		+ "DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,"
					 		+ "EXTINFO,APDEVINFO,WORKMODE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,"
					 		+ "TRANS_TYPE,LINE,ACTIVE_RECORD,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR) "
	         		   		+ "values('" + vcodeid +"','0','"+cabNumber+"','"+subRackNb+"',"
	         		   				+ "'0','0','0','" + vhmap2.get("position") +"',"
	         		   				+ "'0','" + vhmap2.get("unitId") +"','',"
	         		   				+ "'0','" + vhmap2.get("unitType") +"','" + vhmap2.get("unitType") +"',"
	         		   				+ " '0', '0', "
	         		   				+ "'"+vhmap2.get("vendorName")+"', '" + vhmap2.get("serialNumber") +"','" + vhmap2.get("version") +"',"
	         		   				+ " DATE '"+todaydate+"',DATE '"+todaydate+"','" + distNamee +"',"
	         		   				+ "'0','0','0','0',"
	         		   				+ "'0','0','0','0',"
	         		   				+ "'" + vhmap2.get("identificationCode") +"','0','0','0',"
	         		   				+ "'0','" + vhmap2.get("availabilityStatus") +"','" + nodeIDhmap.get(nodeId) +"','"+ Gcodeattributid +"' ,sysdate,'" + fileNamess +"',"
	         		   				+ "'0','0','0','0','0','0',"
	         		   				+ "'"+ vline +"','1','0',sysdate,'Mobile Access Domain','" + Gprovider +"') ";
				 PreparedStatement stmtattr = con.prepareStatement(InsertQuery); 
		   		 stmtattr.executeUpdate();
		   		 stmtattr.close();
			}
			
			if(StringUtils.equalsIgnoreCase(this.clazz,"UNIT") && (this.distName).contains("BSC") && !(this.distName).contains("BCF"))
			{
				System.out.println("Type Clazz 9");
				//String distName = this.distName;
		   		String[] valuesdistName = getSplitdistName(distName);

		   		String cabNumber = valuesdistName[3];
		   		cabNumber=cabNumber.substring(cabNumber.indexOf("CABINET")+8, cabNumber.length());
		   		
		   		String subRackNb = valuesdistName[4];
		   		subRackNb=subRackNb.substring(subRackNb.indexOf("CARTRIDGE")+10, subRackNb.length());
		   		
				String dist=distName.substring(distName.indexOf("UNIT")+5, distName.length());

				 String pos = null;
				String boardModel = null;
				String distNamee = distName.substring(distName.indexOf("PLMN-PLMN")+10, distName.length());
				 
				if(dist.contains("SHM"))
				{pos=dist.substring(dist.indexOf("SHM")+4,dist.length()); boardModel = "SHM";}

				if(dist.contains("AIC"))
					{pos=dist.substring(dist.indexOf("AIC")+4, dist.length()); boardModel = "AIC";}
	
				if(dist.contains("AMC"))
					{pos=dist.substring(dist.indexOf("AMC")+4, dist.length()); boardModel = "AMC";}

				if(dist.contains("PEM"))
					{pos=dist.substring(dist.indexOf("PEM")+4, dist.length());boardModel = "PEM";}
				
				if(dist.contains("FAN"))
					{pos=dist.substring(dist.indexOf("_FAN")+5, dist.length()); boardModel = "FAN";}	
				
				
				//add in NODE_ACTIVE_ATTRIBUTE the new attribute BOARD
				Gcodeattributid="0";  // initialize Attributte primary Key
				addnewattribut ("NODE_BOARD","BOARD",nodeIDhmap.get(nodeId),nodeType,fileNamess,Gprovider );
				
				
				// get line field sequence number from BOARD based on nodepk
		    	vline=getlinefromtable("NODE_BOARD",nodeIDhmap.get(nodeId));
				

				   vcodeid= localgetseqNbr(5);  /// 5 to select Board_id 
	     		   vcodeid=Gyear+"_"+ "BOARD"+'_'+vcodeid;
	     		   
	     		  InsertQuery="insert into NODE_BOARD (BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,"
					 		+ "BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,"
					 		+ "DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,"
					 		+ "EXTINFO,APDEVINFO,WORKMODE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,"
					 		+ "TRANS_TYPE,LINE,ACTIVE_RECORD,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR) "
	         		   		+ "values('" + vcodeid +"','0','"+cabNumber+"',"
	         		   				+ "'"+subRackNb+"','0','0','"+vhmap2.get("position")+"',"
	         		   				+ "'" + pos +"','0','0','0',"
	         		   				+ "'0','" + vhmap2.get("unitTypeActual") +"','" + vhmap2.get("unitTypeExpected") +"',"
	         		   				+ " '0', '0', "
	         		   				+ "'0', '" + vhmap2.get("serialNumber") +"','" + vhmap2.get("version") +"',"
	         		   				+ " DATE '"+todaydate+"',DATE '"+todaydate+"','" + distNamee +"',"
	         		   				+ "'0','0','0','0',"
	         		   				+ "'0','0','0','0',"
	         		   				+ "'" + vhmap2.get("identificationCode") +"','"+boardModel+"','0','0',"
	         		   				+ "'0','" + vhmap2.get("operationalState") +"','" + nodeIDhmap.get(nodeId) +"','"+ Gcodeattributid +"' ,sysdate,'" + fileNamess +"',"
	         		   				+ "'0','0','0','0','0','0',"
	         		   				+ "'"+ vline +"','1','0',sysdate,'Mobile Access Domain','" + Gprovider +"') "; 
	     		  
	     		 PreparedStatement stmtattr = con.prepareStatement(InsertQuery); 
		   		 stmtattr.executeUpdate();
		   		 stmtattr.close();
			}

			if(StringUtils.equalsIgnoreCase(this.clazz,"MODULE") )
			{
				System.out.println("Type Clazz 10");
				String cabNumber = null;
				String moduleNb = null;
				//String distName = this.distName;
		   		String[] valuesdistName = getSplitdistName(distName);
		   		if(distName.contains("RNC") && distName.contains("WBTS"))
		   		{
			   		 cabNumber = valuesdistName[3];
			   		cabNumber=cabNumber.substring(cabNumber.indexOf("HW")+3, cabNumber.length());
			   		
			   		 moduleNb = valuesdistName[4];
			   		moduleNb=moduleNb.substring(moduleNb.indexOf("MODULE")+7, moduleNb.length());
		   		}
		   		if(distName.contains("MRBTS"))
		   		{
			   		 cabNumber = valuesdistName[2];
			   		cabNumber=cabNumber.substring(cabNumber.indexOf("HW")+3, cabNumber.length());
			   		
			   		 moduleNb = valuesdistName[3];
			   		moduleNb=moduleNb.substring(moduleNb.indexOf("MODULE")+7, moduleNb.length());
		   		}
		   		
		   		String distNamee = distName.substring(distName.indexOf("PLMN-PLMN")+10, distName.length());
		   		
		   		
		   	    //add in NODE_ACTIVE_ATTRIBUTE the new attribute BOARD
				Gcodeattributid="0";  // initialize Attributte primary Key
				addnewattribut ("NODE_MODULE","MODULE",nodeIDhmap.get(nodeId),nodeType,fileNamess,Gprovider );
				
				
				// get line field sequence number from SUBRACK based on nodepk
		    	vline=getlinefromtable("NODE_MODULE",nodeIDhmap.get(nodeId));
				 
				   vcodeid= localgetseqNbr(25);  /// 25 to select module_id 
	     		   vcodeid=Gyear+"_"+ "MODULE"+'_'+vcodeid;
				 InsertQuery = "Insert into NODE_MODULE (MODULE_ID,CABINETNO,MODULENO,IDENTIFICATIONCODE,SERIALNUMBER,SUBRACK_SPECIFIC_TYPE,USERLABEL,VENDORNAME,"
				 		+ "VERSION,DISTNAME,NODE_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,"
				 		+ "CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE,LINE,NODE_ATTR_PK,ALM_POSITION)"
				 		+ " values ('"+vcodeid+"','"+cabNumber+"','"+moduleNb+"','"+vhmap2.get("identificationCode")+"','"+vhmap2.get("serialNumber")+"','"+vhmap2.get("subrackSpecificType")+"',"
				 				+ "'"+vhmap2.get("userLabel")+"','"+vhmap2.get("vendorName")+"','"+vhmap2.get("version")+"','"+distNamee+"',"
				 				+ "'"+nodeIDhmap.get(nodeId)+"',sysdate,'"+fileNamess+"','0','0','0','0','0','1',sysdate,'Mobile Access Domain','"+Gprovider+"','0','"+ vline +"','"+ Gcodeattributid +"','0')";
				 PreparedStatement stmtattr = con.prepareStatement(InsertQuery); 
		   		 stmtattr.executeUpdate();
		   		 stmtattr.close();
			}

			if(StringUtils.equalsIgnoreCase(this.clazz,"SUBMODULE") )
			{
				System.out.println("Type Clazz 11");
				String cabNumber = null;
				String moduleNb = null;
				String subModuleNb = null;
				//String distName = this.distName;
		   		String[] valuesdistName = getSplitdistName(distName);
		   		if(distName.contains("RNC") && distName.contains("WBTS"))
		   		{
			   		 cabNumber = valuesdistName[3];
			   		cabNumber=cabNumber.substring(cabNumber.indexOf("HW")+3, cabNumber.length());
			   		
			   		 moduleNb = valuesdistName[4];
			   		moduleNb=moduleNb.substring(moduleNb.indexOf("MODULE")+7, moduleNb.length());
			   		
			   		subModuleNb = valuesdistName[5];
			   		subModuleNb=subModuleNb.substring(subModuleNb.indexOf("SUBMODULE")+10, subModuleNb.length());
			   		
		   		}
		   		if(distName.contains("MRBTS"))
		   		{
			   		 cabNumber = valuesdistName[2];
			   		cabNumber=cabNumber.substring(cabNumber.indexOf("HW")+3, cabNumber.length());
			   		
			   		 moduleNb = valuesdistName[3];
			   		moduleNb=moduleNb.substring(moduleNb.indexOf("MODULE")+7, moduleNb.length());
			   		
			   		subModuleNb = valuesdistName[4];
			   		subModuleNb=subModuleNb.substring(subModuleNb.indexOf("SUBMODULE")+10, subModuleNb.length());
		   		}

		   		String distNamee = distName.substring(distName.indexOf("PLMN-PLMN")+10, distName.length());
		   		
		   	   //add in NODE_ACTIVE_ATTRIBUTE the new attribute BOARD
				Gcodeattributid="0";  // initialize Attributte primary Key
				addnewattribut ("NODE_SUBMODULE","SUBMODULE",nodeIDhmap.get(nodeId),nodeType,fileNamess,Gprovider );
				
				
				// get line field sequence number from SUBRACK based on nodepk
		    	vline=getlinefromtable("NODE_SUBMODULE",nodeIDhmap.get(nodeId));
		   		
				   vcodeid= localgetseqNbr(26);  /// 26 to select submodule_id 
	     		   vcodeid=Gyear+"_"+ "SUBMODULE"+'_'+vcodeid;
				InsertQuery = "Insert into NODE_SUBMODULE (SUBMODULE_ID,CABINETNO,MODULENO,SUBMODULENO,IDENTIFICATIONCODE,SERIALNUMBER,UNITTYPE,VENDORNAME,"
						+ "VERSION,DISTNAME,NODE_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,"
						+ "CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE,LINE,NODE_ATTR_PK,ALM_POSITION) "
						+ "values ('"+vcodeid+"','"+cabNumber+"','"+moduleNb+"','"+subModuleNb+"','"+vhmap2.get("identificationCode")+"','"+vhmap2.get("serialNumber")+"',"
								+ "'"+vhmap2.get("unitType")+"','"+vhmap2.get("vendorName")+"','"+vhmap2.get("version")+"','"+distNamee+"','"+nodeIDhmap.get(nodeId)+"',"
								+ "sysdate,'"+fileNamess+"','0','0','0','0','0','1',sysdate,'Mobile Access Domain','"+Gprovider+"','0','"+ vline +"','"+ Gcodeattributid +"','0')";
				
				 PreparedStatement stmtattr = con.prepareStatement(InsertQuery); 
		   		 stmtattr.executeUpdate();
		   		 stmtattr.close();
			}
			
			if(StringUtils.equalsIgnoreCase(this.clazz,"INVUNIT"))
			{
				System.out.println("Type Clazz 12");
			     if( StringUtils.equalsIgnoreCase(vhmap2.get("vendorUnitFamilyType"),"CABINET") || StringUtils.equalsIgnoreCase(vhmap2.get("inventoryUnitType"),"AMIA AirScale Indoor Subrack")) ///StringUtils.equalsIgnoreCase(vhmap2.get("inventoryUnitType"),"CABINET") ||
			     {
			    	 HashMap<String, String> vhmap11 = new HashMap<String, String>();
			    	 
			    	 vhmap11.putAll(vhmap2);
			    	 allCabinets.put(vhmap2.get("distName"), vhmap11);
			    	 

			    	 String distNamee = distName.substring(distName.indexOf("PLMN-PLMN")+10, distName.length());

						String invUnitID = distNamee.substring(distNamee.indexOf("INVUNIT")+8, distNamee.length());
						
						//add in NODE_ACTIVE_ATTRIBUTE the new attribute Cabinet
						Gcodeattributid="0";  // initialize Attributte primary Key
						addnewattribut ("NODE_CABINET","CABINET",nodeIDhmap.get(nodeId),nodeType,fileNamess,Gprovider );
						
						// get line field sequence number from cabinet based on nodepk
				    	vline=getlinefromtable("NODE_CABINET",nodeIDhmap.get(nodeId));
						
					  vcodeid= localgetseqNbr(7);  /// 7 to select cabinet_id 
			   		   vcodeid=Gyear+"_"+ "CABINET"+'_'+vcodeid;
			   		   //String almposition =vhmap.get("CABINETNO");
			   		   InsertQuery="insert into NODE_CABINET (CABINET_ID,SITEINDEX,CABINETNO,INVENTORYUNITID,RACKTYPE,BOMRACKTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,"
			   		   		+ "VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,ISSUENUMBER,"
			   		   		+ "BOMCODE,EXTINFO,MODEL,USERLABEL,SHAREMODE,CLEICODE,BOM,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,"
			   		   		+ "TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR) "
			   		   		+ " values('" + vcodeid +"','0','0','"+invUnitID+"',"
			   		   				+ "'0','','"+vhmap2.get("inventoryUnitType")+"',"
			   		   				+ "'"+vhmap2.get("vendorUnitFamilyType")+"','"+vhmap2.get("vendorUnitTypeNumber")+"',"
			   		   				+ "'"+vhmap2.get("vendorName")+"','" + vhmap2.get("SERIALNUMBER") +"','"+vhmap2.get("version")+"',"
			   		   				+ "DATE '"+todaydate+"',DATE '"+todaydate+"',"
			   		   				+ " '"+distNamee+"','0','0',"
			   		   				+ "'0','0','"+vhmap2.get("cabinetType")+"',"
			   		   				+ "'0','0','0',"
			   		   				+ "'','"+nodeIDhmap.get(nodeId)+"','"+ Gcodeattributid + "',sysdate,'" + fileNamess +"',"
			   		   				+ "'0','0','0','0','0','0','"+ vline +"','1',"
			   		   						+ "'0',sysdate,'Mobile Access Domain','" + Gprovider +"') ";
						
						   		 PreparedStatement stmtattr = con.prepareStatement(InsertQuery); 
						   		 stmtattr.executeUpdate();
						   		 stmtattr.close();
			     }
			     

			     if(StringUtils.equalsIgnoreCase(vhmap2.get("vendorUnitFamilyType"),"BBMOD") || StringUtils.equalsIgnoreCase(vhmap2.get("vendorUnitFamilyType"),"SMOD") || StringUtils.equalsIgnoreCase(vhmap2.get("inventoryUnitType"),"ASIA AirScale Common") || StringUtils.equalsIgnoreCase(vhmap2.get("inventoryUnitType"),"Flexi System Module Outdoor FSMF")
			    		 			|| StringUtils.equalsIgnoreCase(vhmap2.get("vendorUnitFamilyType"),"PSM"))
			     {
			    	 System.out.println("Type Clazz 13");
			    	 HashMap<String, String> vhmap9 = new HashMap<String, String>();
			    	 
			    	 vhmap9.putAll(vhmap2);
			    	 
			    	 allModules.put(vhmap2.get("distName"), vhmap9);
			    	 
						String invUnitID = distName.substring(distName.indexOf("INVUNIT")+8, distName.length());
						
						String cabno = vhmap2.get("parentDN");
						String cabNo = null;
						if(!StringUtils.equalsIgnoreCase(cabno,null)){
						 cabNo = cabno.substring(cabno.indexOf("INVUNIT")+8, cabno.length());}
						
				   		String distNamee = distName.substring(distName.indexOf("PLMN-PLMN")+10, distName.length());
				   		
				   	   //add in NODE_ACTIVE_ATTRIBUTE the new attribute BOARD
						Gcodeattributid="0";  // initialize Attributte primary Key
						addnewattribut ("NODE_MODULE","MODULE",nodeIDhmap.get(nodeId),nodeType,fileNamess,Gprovider );
						
						
						// get line field sequence number from SUBRACK based on nodepk
				    	vline=getlinefromtable("NODE_MODULE",nodeIDhmap.get(nodeId));
				   		
						vcodeid= localgetseqNbr(25);  /// 25 to select module_id 
			     		   vcodeid=Gyear+"_"+ "MODULE"+'_'+vcodeid;
						 InsertQuery = "Insert into NODE_MODULE (MODULE_ID,CABINETNO,MODULENO,INVUNITID,IDENTIFICATIONCODE,CONFIGDN,INVUNITTYPE,PARENTDN,RUNTIMEDN,"
						 		+ "SERIALNUMBER,STATE,UNITPOSITION,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,SUBRACK_SPECIFIC_TYPE,USERLABEL,VENDORNAME,"
						 		+ "VERSION,DISTNAME,NODE_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,"
						 		+ "CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE,LINE,NODE_ATTR_PK,ALM_POSITION)"
						 		+ " values ('"+vcodeid+"','"+cabNo+"','"+invUnitID+"','"+invUnitID+"','0','"+vhmap2.get("configDN")+"','"+vhmap2.get("inventoryUnitType")+"',"
						 				+ "'"+vhmap2.get("parentDN")+"','"+vhmap2.get("runtimeDN")+"','"+vhmap2.get("serialNumber")+"','"+vhmap2.get("state")+"',"
						 						+ "'"+vhmap2.get("unitPosition")+"','"+vhmap2.get("vendorUnitFamilyType")+"','"+vhmap2.get("vendorUnitTypeNumber")+"','0',"
						 				+ "'0','"+vhmap2.get("vendorName")+"','"+vhmap2.get("versionNumber")+"','"+distNamee+"',"
						 				+ "'"+nodeIDhmap.get(nodeId)+"',sysdate,'"+fileNamess+"','0','0','0','0','0','1',sysdate,'Mobile Access Domain','"+Gprovider+"','0','"+ vline +"','"+ Gcodeattributid +"','0')";
						 PreparedStatement stmtattr = con.prepareStatement(InsertQuery); 
				   		 stmtattr.executeUpdate();
				   		 stmtattr.close();
			    	 /////////////////////////////////////////
			     }
			     
			     String configDN = vhmap2.get("configDN");
			     if(!StringUtils.equalsIgnoreCase(configDN,null)) {
			    	 System.out.println("Type Clazz 14"); 
			    	 String[] valuesConfigDN = getSplitdistName(configDN);
			    	 int length = valuesConfigDN.length;
			    	 
			    	 if(valuesConfigDN[length -1].contains("RMOD"))
			    	 {
							String invUnitID = distName.substring(distName.indexOf("INVUNIT")+8, distName.length());
					   		String distNamee = distName.substring(distName.indexOf("PLMN-PLMN")+10, distName.length());
					   		
					   	    //add in NODE_ACTIVE_ATTRIBUTE the new attribute BOARD
							Gcodeattributid="0";  // initialize Attributte primary Key
							addnewattribut ("NODE_ANTENNA","ANTENNA",nodeIDhmap.get(nodeId),nodeType,fileNamess,Gprovider );
							
							
							// get line field sequence number from SUBRACK based on nodepk
					    	vline=getlinefromtable("NODE_ANTENNA",nodeIDhmap.get(nodeId));
					   		
					    	 vcodeid= localgetseqNbr(15);  /// 15 to select Antena_id 
			          		   vcodeid=Gyear+"_"+ "ANTENNA"+'_'+vcodeid;
			          			   InsertQuery="insert into NODE_ANTENNA (ANTENNA_ID,INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,"
					  			   		+ "VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,"
					  			   		+ "ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,"
					  			   		+ "TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR) "
					  			   		+ "values('" + vcodeid +"','" + invUnitID +"','" + vhmap2.get("inventoryUnitType") +"','0',"
						   				+ "'0','" + vhmap2.get("vendorUnitFamilyType") +"','"+ vhmap2.get("vendorUnitTypeNumber")+"',"   ///" + vhmap2.get("vendorUnitTypeNumber") +"
										+ "'" + vhmap2.get("vendorName") +"','" + vhmap2.get("serialNumber") +"','" + vhmap2.get("versionNumber") +"',"
										+ "DATE '" + todaydate +"',DATE '" + todaydate +"',"
										+ "'" + distNamee +"','0',"
										+ "'" + vhmap2.get("inventoryUnitType") +"','0',"
										+ "'0','0','0',"
										+ "'0','" + nodeIDhmap.get(nodeId) +"','"+ Gcodeattributid +"',"
										+ "sysdate,'" + fileNamess +"','' ,'0','0','0',"
										+ "'0','0',"
										+ "'"+ vline +"','1',sysdate,'Mobile Access Domain','" + Gprovider +"') ";
			          			   
							   		 PreparedStatement stmtattr = con.prepareStatement(InsertQuery); 
							   		 stmtattr.executeUpdate();
							   		 stmtattr.close();
	 
			    	 }
			     }
			     
			     if(StringUtils.equalsIgnoreCase(vhmap2.get("vendorUnitFamilyType"),"CORE_SMOD") || StringUtils.equalsIgnoreCase(vhmap2.get("vendorUnitFamilyType"),"GNSSE") )
			     {
			    	    System.out.println("Type Clazz 15"); 
			    	    String parentDN = "PLMN-PLMN/"+vhmap2.get("parentDN");

						if((allModules.get(parentDN)) != null) {

						vhmap10.putAll(allModules.get(parentDN));}

						String cabNo = parentDN.substring(parentDN.indexOf("INVUNIT")+8, parentDN.length());
			    	 
						String invUnitID = distName.substring(distName.indexOf("INVUNIT")+8, distName.length());
						
						String modNo = parentDN.substring(parentDN.indexOf("INVUNIT")+8, parentDN.length());
						
				   		String distNamee = distName.substring(distName.indexOf("PLMN-PLMN")+10, distName.length());
				   		
				   	    //add in NODE_ACTIVE_ATTRIBUTE the new attribute BOARD
						Gcodeattributid="0";  // initialize Attributte primary Key
						addnewattribut ("NODE_SUBMODULE","SUBMODULE",nodeIDhmap.get(nodeId),nodeType,fileNamess,Gprovider );
						
						
						// get line field sequence number from SUBRACK based on nodepk
				    	vline=getlinefromtable("NODE_SUBMODULE",nodeIDhmap.get(nodeId));
				   		
						vcodeid= localgetseqNbr(26);  /// 26 to select submodule_id 
			     		   vcodeid=Gyear+"_"+ "SUBMODULE"+'_'+vcodeid;
						InsertQuery = "Insert into NODE_SUBMODULE (SUBMODULE_ID,CABINETNO,MODULENO,SUBMODULENO,IDENTIFICATIONCODE,INVUNITID,CONFIGDN,PARENTDN,RUNTIMEDN,SERIALNUMBER,"
								+ "UNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,"
								+ "VERSION,DISTNAME,NODE_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,"
								+ "CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE,LINE,NODE_ATTR_PK,ALM_POSITION) "
								+ "values ('"+vcodeid+"','"+cabNo+"','"+modNo+"','"+invUnitID+"','','"+invUnitID+"','"+vhmap2.get("configDN")+"','"+vhmap2.get("parentDN")+"','"+vhmap2.get("runtimeDN")+"','"+vhmap2.get("serialNumber")+"',"
										+ "'"+vhmap2.get("inventoryUnitType")+"','"+vhmap2.get("vendorUnitFamilyType")+"','"+vhmap2.get("vendorUnitTypeNumber")+"',"
												+ "'"+vhmap2.get("vendorName")+"','"+vhmap2.get("versionNumber")+"','"+distNamee+"','"+nodeIDhmap.get(nodeId)+"',"
										+ "sysdate,'"+fileNamess+"','0','0','0','0','0','1',sysdate,'Mobile Access Domain','"+Gprovider+"','0','"+ vline +"','"+ Gcodeattributid +"','0')";
						
						 PreparedStatement stmtattr = con.prepareStatement(InsertQuery); 
				   		 stmtattr.executeUpdate();
				   		 stmtattr.close();
			     }
			     
			     
			     if(StringUtils.equalsIgnoreCase(vhmap2.get("inventoryUnitType"),"FAN") )
			     {
			    	 System.out.println("Type Clazz 16");
		    		 HashMap<String, String> vhmap13 = new HashMap<String, String>();
			    		vhmap13.putAll(vhmap2);
			    		vhmap13.put("nodeID",nodeIDhmap.get(nodeId));
			    		allFANs.put(vhmap2.get("distName"), vhmap13);
			    		//logger.info("allFANs: "+allFANs);
			    		
			    	 String parentDN = "PLMN-PLMN/"+vhmap2.get("parentDN");
			    	 if((allCabinets.get(parentDN)) != null) {
			    		 
			    		 //insert fan as module
			    		 
							String invUnitID = distName.substring(distName.indexOf("INVUNIT")+8, distName.length());
							String cabno = vhmap2.get("parentDN");
							String cabNo = cabno.substring(cabno.indexOf("INVUNIT")+8, cabno.length());
					   		String distNamee = distName.substring(distName.indexOf("PLMN-PLMN")+10, distName.length());

					   		
					   	    //add in NODE_ACTIVE_ATTRIBUTE the new attribute BOARD
							Gcodeattributid="0";  // initialize Attributte primary Key
							addnewattribut ("NODE_MODULE","MODULE",nodeIDhmap.get(nodeId),nodeType,fileNamess,Gprovider );
							
							
							// get line field sequence number from SUBRACK based on nodepk
					    	vline=getlinefromtable("NODE_MODULE",nodeIDhmap.get(nodeId));
					   		
							vcodeid= localgetseqNbr(25);  /// 25 to select module_id 
				     		   vcodeid=Gyear+"_"+ "MODULE"+'_'+vcodeid;
							 InsertQuery = "Insert into NODE_MODULE (MODULE_ID,CABINETNO,MODULENO,INVUNITID,IDENTIFICATIONCODE,CONFIGDN,INVUNITTYPE,PARENTDN,RUNTIMEDN,"
							 		+ "SERIALNUMBER,STATE,UNITPOSITION,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,SUBRACK_SPECIFIC_TYPE,USERLABEL,VENDORNAME,"
							 		+ "VERSION,DISTNAME,NODE_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,"
							 		+ "CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE,LINE,NODE_ATTR_PK,ALM_POSITION)"
							 		+ " values ('"+vcodeid+"','"+cabNo+"','"+invUnitID+"','"+invUnitID+"','','"+vhmap2.get("configDN")+"','"+vhmap2.get("inventoryUnitType")+"',"
							 				+ "'"+vhmap2.get("parentDN")+"','"+vhmap2.get("runtimeDN")+"','"+vhmap2.get("serialNumber")+"','"+vhmap2.get("state")+"',"
							 						+ "'"+vhmap2.get("unitPosition")+"','"+vhmap2.get("vendorUnitFamilyType")+"','"+vhmap2.get("vendorUnitTypeNumber")+"','',"
							 				+ "'','"+vhmap2.get("vendorName")+"','"+vhmap2.get("versionNumber")+"','"+distNamee+"',"
							 				+ "'"+nodeIDhmap.get(nodeId)+"',sysdate,'"+fileNamess+"','0','0','0','0','0','1',sysdate,'Mobile Access Domain','"+Gprovider+"','0','"+ vline +"','"+ Gcodeattributid +"','0')";
							 PreparedStatement stmtattr = con.prepareStatement(InsertQuery); 
					   		 stmtattr.executeUpdate();
					   		 stmtattr.close();
			    	 
					   		 String dis = vhmap2.get("distName");
					   		 vhmap12.put(dis,"");
			    	 }
			    	 
			    	 if((allModules.get(parentDN)) != null) {
			    		 
			    		 //insert fan as submodule

								vhmap10.putAll(allModules.get(parentDN));
								
								String cabno = vhmap10.get("parentDN"); 
								String cabNo = cabno.substring(cabno.indexOf("INVUNIT")+8, cabno.length());
					    	 
								String invUnitID = distName.substring(distName.indexOf("INVUNIT")+8, distName.length());
								
								String modno = vhmap2.get("parentDN");
								String modNo = modno.substring(modno.indexOf("INVUNIT")+8, modno.length());
								
						   		String distNamee = distName.substring(distName.indexOf("PLMN-PLMN")+10, distName.length());
						   		
						   	 //add in NODE_ACTIVE_ATTRIBUTE the new attribute BOARD
								Gcodeattributid="0";  // initialize Attributte primary Key
								addnewattribut ("NODE_SUBMODULE","SUBMODULE",nodeIDhmap.get(nodeId),nodeType,fileNamess,Gprovider );
								
								
								// get line field sequence number from SUBRACK based on nodepk
						    	vline=getlinefromtable("NODE_SUBMODULE",nodeIDhmap.get(nodeId));
						   		
							vcodeid= localgetseqNbr(26);  /// 26 to select submodule_id 
				     		   vcodeid=Gyear+"_"+ "SUBMODULE"+'_'+vcodeid;
							InsertQuery = "Insert into NODE_SUBMODULE (SUBMODULE_ID,CABINETNO,MODULENO,SUBMODULENO,IDENTIFICATIONCODE,INVUNITID,CONFIGDN,PARENTDN,RUNTIMEDN,SERIALNUMBER,"
									+ "UNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,"
									+ "VERSION,DISTNAME,NODE_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,"
									+ "CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE,LINE,NODE_ATTR_PK,ALM_POSITION) "
									+ "values ('"+vcodeid+"','"+cabNo+"','"+modNo+"','"+invUnitID+"','','"+invUnitID+"','"+vhmap2.get("configDN")+"','"+vhmap2.get("parentDN")+"','"+vhmap2.get("runtimeDN")+"','"+vhmap2.get("serialNumber")+"',"
											+ "'"+vhmap2.get("inventoryUnitType")+"','"+vhmap2.get("vendorUnitFamilyType")+"','"+vhmap2.get("vendorUnitTypeNumber")+"',"
													+ "'"+vhmap2.get("vendorName")+"','"+vhmap2.get("versionNumber")+"','"+distNamee+"','"+nodeIDhmap.get(nodeId)+"',"
											+ "sysdate,'"+fileNamess+"','0','0','0','0','0','1',sysdate,'Mobile Access Domain','"+Gprovider+"','0','"+ vline +"','"+ Gcodeattributid +"','0')";
							
							 PreparedStatement stmtattr = con.prepareStatement(InsertQuery); 
					   		 stmtattr.executeUpdate();
					   		 stmtattr.close();
					   		 
					   		 String dis = vhmap2.get("distName");
					   		 vhmap12.put(dis,"");
			    	 }
			     }
			     //////////////////////////////////////////////


			    // String configDN = vhmap2.get("configDN");
		     if(!StringUtils.equalsIgnoreCase(configDN,null)) {
		    	 System.out.println("Type Clazz 17");
		    	 String[] valuesConfigDN = getSplitdistName(configDN);

		    	 String type = valuesConfigDN[valuesConfigDN.length -1];
		    	 if(type.contains("ALD") || type.contains("RETU") || type.contains("LNA"))
		    	 {
			    	 String unitID =null;
			    	 String serialnb = null;
			    	 String serialnumber = null;
			    	 serialnb = vhmap2.get("serialNumber");
			    			 if(!StringUtils.equalsIgnoreCase(serialnb, null)) {serialnumber = serialnb.trim();}
			    				 		    			 
			    			 String distNamee = distName.substring(distName.indexOf("PLMN-PLMN")+10, distName.length());

			    			 unitID=distName.substring(distName.indexOf("INVUNIT")+8, distName.length());

			    			//add in NODE_ACTIVE_ATTRIBUTE the new attribute BOARD
								Gcodeattributid="0";  // initialize Attributte primary Key
								addnewattribut ("NODE_ANTENNA","ANTENNA",nodeIDhmap.get(nodeId),nodeType,fileNamess,Gprovider );
								
								
								// get line field sequence number from SUBRACK based on nodepk
						    	vline=getlinefromtable("NODE_ANTENNA",nodeIDhmap.get(nodeId));
							 
			    	   vcodeid= localgetseqNbr(15);  /// 15 to select Antena_id 
          		       vcodeid=Gyear+"_"+ "ANTENNA"+'_'+vcodeid;
          			   InsertQuery="insert into NODE_ANTENNA (ANTENNA_ID,INVENTORYUNITID,INVENTORYUNITTYPE,ANTENNADEVICENO,PRODNR,VENDORUNITFAMILYTYPE,"
		  			   		+ "VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,"
		  			   		+ "DISTNAME,CONFIGDN,PARENTDN,RUNTIMEDN,ANTENNADEVICETYPE,ISSUENUMBER,BOMCODE,EXTINFO,MODEL,SERIALNUMBEREX,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,"
		  			   		+ "TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,LINE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR) "
		  			   		+ "values('" + vcodeid +"','" + unitID +"','" + vhmap2.get("inventoryUnitType") +"','',"
			   				+ "'','" + vhmap2.get("vendorUnitFamilyType") +"','"+vhmap2.get("vendorUnitTypeNumber")+"',"   ///" + vhmap2.get("vendorUnitTypeNumber") +"
							+ "'" + vhmap2.get("vendorName") +"','" + serialnumber +"','" + vhmap2.get("versionNumber") +"',"
							+ "DATE '" + todaydate +"',DATE '" + todaydate +"',"
							+ "'" + vhmap2.get("unitPosition") +"','',"
							+ "'"+distNamee+"','"+vhmap2.get("configDN")+"','"+vhmap2.get("parentDN")+"','"+vhmap2.get("runtimeDN")+"','','',"
							+ "'','','',"
							+ "'','" + nodeIDhmap.get(nodeId) +"','"+ Gcodeattributid +"',"
							+ "sysdate,'" + fileNamess +"','' ,'0','0','0',"
							+ "'','',"
							+ "'"+ vline +"','1',sysdate,'Mobile Access Domain','" + Gprovider +"') ";
          			   
				   		 PreparedStatement stmtattr = con.prepareStatement(InsertQuery); 
				   		 stmtattr.executeUpdate();
				   		 stmtattr.close();

			     }
		     }


			}
			
			if(StringUtils.equalsIgnoreCase(this.clazz,"FUUNIT"))
			{
				System.out.println("Type Clazz 18");
				String fuunitNo = null;
		   		String[] valuesdistName = getSplitdistName(distName);
		   		fuunitNo = valuesdistName[3];
		   		String[] valuesOfFuunit = getDashSplit(fuunitNo);

		   		///System.out.println("valuesOfFuunitLength: "+valuesOfFuunit.length);
		   		if(valuesOfFuunit.length == 3) {
		   		fuunitNo = valuesOfFuunit[2];
		   		}
		   		else {
		   			fuunitNo = null;
		   		}
		   		
		   		String vcodeidboard = null;
		   		if(!StringUtils.equalsIgnoreCase(vhmap2.get("supportedByUnit"),null))
		   		{
					String distNamee = vhmap2.get("supportedByUnit");
					 distNamee = distNamee.substring(distNamee.indexOf("PLMN-PLMN")+10, distNamee.length());
					 
		   			String suppByUnit = vhmap2.get("supportedByUnit");
		   			String[] valuesOfSuppByUnit = getSplitdistName(suppByUnit);
		   			
			   		String cabNumber = valuesOfSuppByUnit[3];
			   		cabNumber=cabNumber.substring(cabNumber.indexOf("CABINET")+8, cabNumber.length());
			   		
			   		String subRackNb = valuesOfSuppByUnit[4];
			   		subRackNb=subRackNb.substring(subRackNb.indexOf("CARTRIDGE")+10, subRackNb.length());
			   		
			   		String brdtypenb = valuesOfSuppByUnit[5];
			   		String pos = null;
			   		String boardModel = null;
			   		
			   		if(brdtypenb.contains("SHM"))
					{
			   			pos=brdtypenb.substring(brdtypenb.indexOf("SHM")+4, brdtypenb.length());
			   			boardModel = "SHM";
					}
					if(brdtypenb.contains("AIC"))
						{
						pos=brdtypenb.substring(brdtypenb.indexOf("AIC")+4, brdtypenb.length());
						boardModel = "AIC";
						}
					if(brdtypenb.contains("AMC"))
					{
						pos=brdtypenb.substring(brdtypenb.indexOf("AMC")+4, brdtypenb.length());
						boardModel = "AMC";
					}
					if(brdtypenb.contains("PEM"))
					{
						pos=brdtypenb.substring(brdtypenb.indexOf("PEM")+4, brdtypenb.length());
						boardModel = "PEM";
					}
					
					if(brdtypenb.contains("FAN"))
					{
						pos=brdtypenb.substring(brdtypenb.indexOf("_FAN")+5, brdtypenb.length());
						boardModel = "FAN";
					}
			   		
					//add in NODE_ACTIVE_ATTRIBUTE the new attribute BOARD
					Gcodeattributid="0";  // initialize Attributte primary Key
					addnewattribut ("NODE_BOARD","BOARD",nodeIDhmap.get(nodeId),nodeType,fileNamess,Gprovider );
					
					
					// get line field sequence number from SUBRACK based on nodepk
			    	vline=getlinefromtable("NODE_BOARD",nodeIDhmap.get(nodeId));
					
					 vcodeidboard= localgetseqNbr(5);  /// 5 to select Board_id 
		     		   vcodeidboard=Gyear+"_"+ "BOARD"+'_'+vcodeidboard;
		     		  InsertQuery="insert into NODE_BOARD (BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,INVENTORYUNITID,MODULENO,BOARDNAME,"
						 		+ "BOARDTYPE,INVENTORYUNITTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,DATEOFMANUFACTURE,"
						 		+ "DATEOFLASTSERVICE,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,BIOSVEREX,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,"
						 		+ "EXTINFO,APDEVINFO,WORKMODE,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,"
						 		+ "TRANS_TYPE,LINE,ACTIVE_RECORD,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR) "
		         		   		+ "values('" + vcodeidboard +"','','"+cabNumber+"',"
		         		   				+ "'"+subRackNb+"','','','',"
		         		   				+ "'"+pos+"','','','',"
		         		   				+ "'','" + vhmap2.get("unitTypeActual") +"','" + vhmap2.get("unitTypeExpected") +"',"
		         		   				+ " '', '', "
		         		   				+ "'', '','" + vhmap2.get("version") +"',"
		         		   				+ " DATE '"+todaydate+"',DATE '"+todaydate+"','"+distNamee+"',"
		         		   				+ "'','','','',"
		         		   				+ "'','','','',"
		         		   				+ "'','"+boardModel+"','','',"
		         		   				+ "'','','" + nodeIDhmap.get(nodeId) +"','"+ Gcodeattributid +"' ,sysdate,'" + fileNamess +"',"
		         		   				+ "'0','0','0','0','0','0',"
		         		   				+ "'"+ vline +"','1','',sysdate,'Mobile Access Domain','" + Gprovider +"') "; 
		     		  
		     		 PreparedStatement stmtattr = con.prepareStatement(InsertQuery); 
			   		 stmtattr.executeUpdate();
			   		 stmtattr.close();
			   		
			   		
		   		}
		   		
		   		String distNameee = distName.substring(distName.indexOf("PLMN-PLMN")+10, distName.length());

		   	  //add in NODE_ACTIVE_ATTRIBUTE the new attribute BOARD
				Gcodeattributid="0";  // initialize Attributte primary Key
				addnewattribut ("NODE_FUUNIT","FUUNIT",nodeIDhmap.get(nodeId),nodeType,fileNamess,Gprovider );
				
				
				// get line field sequence number from SUBRACK based on nodepk
		    	vline=getlinefromtable("NODE_FUUNIT",nodeIDhmap.get(nodeId));
		    	
		    	 vcodeid= localgetseqNbr(24);  /// 24 to select FUUNIT_ID
        		   vcodeid=Gyear+"_"+ "FUUNIT"+'_'+vcodeid;
   			   
        		   InsertQuery = "Insert into NODE_FUUNIT (FUUNIT_ID,FUUNITNO,FUNCTIONAL_UNIT_TYPE,SUPPORT_BY_UNIT,DISTNAME,NODE_PK,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,"
        		   		+ "FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE,LINE,NODE_ATTR_PK,ALM_POSITION)"
        		   		+ " values ('"+vcodeid+"','"+fuunitNo+"','"+vhmap2.get("functionalUnitType")+"','"+vcodeidboard+"','"+distNameee+"','"+nodeIDhmap.get(nodeId)+"',"
        		   				+ "DATE '"+ todaydate +"','"+fileNamess+"','0','0','0','0','0','1',sysdate,'Mobile Access Domain','"+Gprovider+"','0','"+ vline +"','"+ Gcodeattributid +"','0')";

   			   
			   		 PreparedStatement stmtattr = con.prepareStatement(InsertQuery); 
			   		 stmtattr.executeUpdate();
			   		 stmtattr.close();
			}
   		
	   }
			}
			
			catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error: "+e);
				logger.info("Error: "+e);
				e.printStackTrace();
				}
	   	
	   }
	   
	   @Override
	   public void characters(char ch[], int start, int length)
	   		throws SAXException {
	   	String s = new String(ch, start, length);
	   	if (!s.trim().isEmpty() && this.tagName.equals("p"))
	   	{

	   		//System.out.println("<p> "+this.id+","+this.attrName+","+s +"\n");
   		//System.out.println("<p> "+this.attrName+","+s +"\n");

	   		vhmap.put(this.attrName,s);
	   		vhmap.put("distName", this.distName);
	   		vhmap.put("class", this.clazz);
	   		vhmap.put("tversion", this.tversion);

	   	}
	   //	System.out.println("tagname: "+this.tagName+" S: "+s);
	   	
	   	
	   	if ( this.tagName.equals("list")) 
	   	{
			//System.out.println("<list> "+this.id+","+this.attrName+","+s +"\n");
	   }
	   }
		 
	    public void addnewattribut (String tablename,String attribat, String codeid,String nodetype,String filename,String vendor ) throws SQLException {
	    	PreparedStatement stmtinsert1=null;
	    	Statement stmt1 = null;
	    	
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

		    if (vline.equalsIgnoreCase("0")) {
		    	Gcodeattributid= localgetseqNbr(1);  /// 7 to select cabinet_id 
		    	Gcodeattributid=Gyear+"_"+ "ATTRIBUTE"+'_'+Gcodeattributid;
		    	
		    	vline  = Integer.toString(localcounter);
		    	 stmtinsert1 = con.prepareStatement("insert into  NODE_ACTIVE_ATTRIBUTE (NODE_ATTR_PK,ATTRIBUTE,ATTRIBUTE_TABLE,NODE_PK,NODE_TYPE,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR,TO_TRANS_SOURCE) "
		 			 		+  "values ('"+ Gcodeattributid  +"','"+ attribat +"', '"+ tablename +"','"+ codeid +"','" + nodetype + "',sysdate,'"+ filename +"','0','0','0','0','0','1','"+ vline +"','Mobile Access Domain','" + vendor +"','0')  ");
				  stmtinsert1.executeUpdate();
				  stmtinsert1.close();
		    } 
		    
	    	
	
			  
		    
	   		
	    }
	   
	    public String getlinefromtable (String tablename,String nodepk ) throws SQLException {
	    	Statement stmt1 = null;
	    	vline= "0";
	    	stmt1 = con.createStatement();   
		 	String sqlStmt = "select (max(line)+1) as line from " +tablename+" where node_pk='"+ nodepk +"'";     
		    ResultSet rs1 = stmt1.executeQuery(sqlStmt);
		    while (rs1.next()) { 
		    	vline=rs1.getString("line");
		    }
		    rs1.close();
		    stmt1.close();
		    
		    if (vline !=null) {
		    	//use vline extracted from query
		    }else {
		    	vline= "0";
		    }
	 	   		return vline;
	 	 }
	    
		 public static String[] getSplitdistName(String distName) {
			 String [] result =null;
			 
				String vvalue = distName;
				int count =0;
				for(int i = 0; i < vvalue.length() ; i++)
				{
				    if(vvalue.charAt(i) == '/')
				    {
				        count++;
				    }
				}
				String[] arrOfStrs = vvalue.split("/", count+1);
			 result = arrOfStrs;
			 return result;
		 }
		 
		 public static String[] getDashSplit(String distName) {
			 String [] result =null;
			 
				String vvalue = distName;
				int count =0;
				for(int i = 0; i < vvalue.length() ; i++)
				{
				    if(vvalue.charAt(i) == '-')
				    {
				        count++;
				    }
				}
				String[] arrOfStrs = vvalue.split("-", count+1);
			 result = arrOfStrs;
			 return result;
		 }

	   
		 private static void GetduplicateFilename(String vdomain , String vvendor) throws SQLException  {
			 try {
				Statement stmt1 = null;
				Statement stmt2 = null;
				Statement stmt3 = null;
				//Statement stmt4 = null;
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
			    
			    }
			 }catch(Exception e)  
				{  
					logger.info("error at GetduplicateFilename is :"+ e.toString());
					System.out.println("error at GetduplicateFilename is :"+ e.toString()); 
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
		     
		     stmt2 = con.prepareStatement("delete from  NODE_4G where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
		     stmt2.executeUpdate();
		     stmt2.close();
		     
		     stmt = con.prepareStatement("delete from  NODE_ANTENNA where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
		     stmt.executeUpdate();
		     stmt.close(); 
		     		 
		     stmt1 = con.prepareStatement("delete from  NODE_3G where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
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
		     
			 stmt1 = con.prepareStatement("delete from  NODE_FUUNIT where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
		     stmt1.executeUpdate();
		     stmt1.close();
		     
			 stmt2 = con.prepareStatement("delete from  NODE_MODULE where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
		     stmt2.executeUpdate();
		     stmt2.close();
		     
			 stmt = con.prepareStatement("delete from  NODE_SUBMODULE where " + fieldname +" = '" + fieldValue +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"); 
		     stmt.executeUpdate();
		     stmt.close();
			 }
			catch(Exception e)  
			{  
				logger.info("error at deleterowsinALLTABLES is :"+ e.toString());
				System.out.println("error at deleterowsinALLTABLES is :"+ e.toString()); 
				
			}
		     
		}
	   
		 private static String localgetseqNbr(int n1) throws SQLException {
			    String min = null ;

						Statement stmttype = null;			     
			         
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
					SeqName = "AUTO_DISCOVERY_LOGS_SEQ";
					break;
					
				case 22:
					SeqName = "AUTO_DISCOVERY_LOGS_DETAILS_SEQ";
					break;
				
				case 23:
					SeqName = "NODERET_SEQ";
					break;
					
				case 24:
					SeqName = "NODEFUUNIT_SEQ";
					break;
					
				case 25:
					SeqName = "NODEMODULE_SEQ";
					break;
					
				case 26:
					SeqName = "NODESUBMODULE_SEQ";
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
	   
}