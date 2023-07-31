package com.aliat.alm.Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.CopyOption;
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
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LoadFilesCIM {

	
	private static final CopyOption REPLACE_EXISTING = null;
	static Connection con;
	static Connection conalm;
	static PreparedStatement stmtp;
	static String Gyear=null;
	static HashMap<String, String> vhmap = new HashMap<String, String>();
	static String[] ResultNode =null;
	static String projpath=null;
	static String vadata=null;
	static String filetype =null;
	static String codeid="0";
	static String circleid="0";
	static int totsum;
	static Logger logger;
	static FileHandler fh;
	static String strCurrentLine1;
	static String logpath;
	static String db1path;
	static String db2path;
	static String readfileCIMfrom;
	static String copyfileCIMto;
	static String username1;
	static String password1;
	static String username2;
	static String password2;
	static String Gprovider;
	static String vfolderfrom;
	static String vfolderto;
	
	public static void main(String[] args) throws SecurityException, IOException, InterruptedException, SQLException {
	
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
			Gyear=dtf.format(now).substring(0,4);
			String lofilename="ParserCIMLog-"+dtf.format(now)+".log";
			 String projectpath=System.getProperty("user.dir");
			 projectpath=projectpath.substring(0, projectpath.length()-3);

			 
			 BufferedReader objReader1 = null;
			 
			 
			 objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/"+"almconfig.dat"));
			 while ((strCurrentLine1 = objReader1.readLine()) != null){
				 String data = strCurrentLine1;
				 String[] data1 ;
				 String[] data2 ;
				 
				 if (data.contains("projectpath")) {
					 data1=data.split(";",-1);
					 projpath=data1[1];
					 //System.out.println("projectpath found :" + projpath);
				 }
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
				 if (data.contains("db2path")) {
					 data1=data.split(";",-1);
					 db2path=data1[1];
					 username2=data1[2];
					 password2=data1[3];
					 //System.out.println("db2path found :" + db2path);
				 }
				 if (data.contains("readfileCIMfrom")) {
					 data1=data.split(";",-1);
					 readfileCIMfrom=data1[1];
					 //System.out.println("readfileAIMfrom found :" + readfileAIMfrom);
					 data2=readfileCIMfrom.split("/",-1);
					 vfolderfrom=data2[data2.length-1];
					 //System.out.println("data2 found :" + data2[2]);
					 Gprovider=vfolderfrom.substring(0,2);
					 //System.out.println("Gprovider2 found :" + Gprovider);
				 }
				 if (data.contains("copyfileCIMto")) {
					 data1=data.split(";",-1);
					 copyfileCIMto=data1[1];
					 //System.out.println("copyfileAIMto found :" + copyfileAIMto);
					 data2=copyfileCIMto.split("/",-1);
					 vfolderto=data2[data2.length-1];
				 }
			}
			 objReader1.close();
			 
			 objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/"+"almcircle.dat"));
			 while ((strCurrentLine1 = objReader1.readLine()) != null){
				 String data = strCurrentLine1;
				 String[] data1 ;
				 data1=data.split(";",-1);
				 circleid=data1[1];
			 }
			 objReader1.close();
			 
			 
			 DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
				conalm = DriverManager.getConnection (db1path,username1,password1);

				try {
				    //con= DriverManager.getConnection(dbURL,username,password);
				    con= DriverManager.getConnection(db2path,username2,password2);
				System.out.println("Connected to oracle DB");
				} catch (SQLException e) {
				       System.out.println("Opss, error");
				       e.printStackTrace();
				   }
				
				
				logger = Logger.getLogger("MyLog"); 
				logger.setUseParentHandlers(false);
				
				 fh = new FileHandler(System.getProperty("user.dir")+"/LOGS/"+lofilename);  
			        logger.addHandler(fh);
			        SimpleFormatter formatter = new SimpleFormatter();  
			        fh.setFormatter(formatter);
			        
			        
				//validate if the same process is running now if yes we cnnot run it twice until finish
				Statement stmtinit2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
		    	 String sqlStmtinit2 = "select * from EXECUTE_DOAMIN_VENDOR_FILES where DOMAIN='Mobile Access Domain CIM' and VENDOR='"+ Gprovider +"' and STATUS='IN PROCESS'";          
				    ResultSet rsinit2 = stmtinit2.executeQuery(sqlStmtinit2);
				    rsinit2.last();
			 	    int totalrecinit2 = rsinit2.getRow(); 
			 	   rsinit2.beforeFirst();
			 	   if (totalrecinit2 == 0 ) {
			 		  PreparedStatement stmtinit = con.prepareStatement("insert into EXECUTE_DOAMIN_VENDOR_FILES (DOMAIN,VENDOR,CREATION_DATE,STATUS) values ('Mobile Access Domain CIM', '"+ Gprovider +"',sysdate,'IN PROCESS')");
						 stmtinit.executeUpdate();
						 stmtinit.close();	
				
				
						 File folder = new File(projectpath+"/"+vfolderfrom);
							File[] listOfFiles = folder.listFiles();
							

										
							// This block configure the logger with handler and formatter  and PATH
							
					       
					        totsum=0;
							for (File file : listOfFiles) {
								if (file.isFile()) {
							        //System.out.println(file.getName());
							        String fichier =file.getName().toString();
									readfile(fichier); 
									
									 File source = new File(projectpath+"/"+vfolderfrom+"/"+file.getName());
								     File dest = new File(projectpath+"/"+vfolderto+"/"+file.getName()+".bkp");
								     copyFiles(source,dest);
								     deleteFiles(projectpath+"/"+vfolderfrom+"/"+file.getName());
								     totsum=totsum+1;
							    }
							}
							System.out.println("Reading ALL CIM FILES with number:  " + totsum);
							logger.info("Reading ALL CIM FILES with number:  " + totsum );
						 
						 
						 stmtinit = con.prepareStatement("update EXECUTE_DOAMIN_VENDOR_FILES set STATUS ='COMPLETED' where DOMAIN='Mobile Access Domain CIM' and VENDOR='"+ Gprovider +"' and STATUS='IN PROCESS'");
						 stmtinit.executeUpdate();
						 stmtinit.close();
				
			 	  } else {
			 		   System.out.println("A process already is runnig , please wait until process ending");
			 		   logger.info("A process already is runnig , please wait until process ending");
			 	   }
			 	  rsinit2.close();
			 	  stmtinit2.close();
				

			con.close();
			conalm.close();
	   }
	
	
	 public static String readfile (String filename){
	
			BufferedReader objReader = null;
			
	        String str = null;
	        String pass=null;
	        String strpwd = null;
	        String struser = null;
	        String[] hider =null;
	        String todaydate = null;
	        try{
	        	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        	LocalDateTime now = LocalDateTime.now();
	        	todaydate=dtf.format(now);
	        	//System.out.println(todaydate);
					
					
					 String strCurrentLine;
					BufferedReader objReader0 = new BufferedReader(new FileReader(projpath+"/"+vfolderfrom+"/"+filename));
					while ((strCurrentLine = objReader0.readLine()) != null) {
						String data = strCurrentLine;
						vadata=data.trim();
						System.out.println(vadata);
						ResultNode=getnodeidnametype(vadata,filename);
						}
					objReader0.close();
				
										
					 /// get seq id of nodeactive and fillin table NODE_ACTIVE
					// codeid= ParserGetSeqNbr.getseqNbr(0);
					// PreparedStatement stmt = con.prepareStatement("insert into NODE_ACTIVE values('" + codeid +"' ,'" + ResultNode[0] +"' ,'" + ResultNode[1] +"','" + ResultNode[2] +"','0','0','0','0','0','0','0',sysdate,sysdate,'" + filename +"') "); 
	                // stmt.executeUpdate();
				    // stmt.close();
					
					//logger.info("Node ID is  : " + ResultNode[0] +" ; " +"Node Name is  : " + ResultNode[1] +" ; " +"Node Type is  : " + ResultNode[2]);
					
				System.out.println("Reading Data LoadFilesCIM From " + filename + " COMPLETED");
				logger.info("Reading Data LoadFilesCIM From " + filename + " COMPLETED");
				
				
		        }   
	        		
	        catch (IOException e) {
					e.printStackTrace();
					logger.info("Error : "+ filename +"   "+e);
				} 
		        catch(Exception e){
		  	      System.err.println(e);
		  	    logger.info("Error : "+ filename +"   "+e);
		  	      e.printStackTrace();
		  	   }
			return null;
		 
	 }
	 
	 
	 public static String[] getnodeidnametype(String RowLine,String filename) throws IOException, SQLException {
		 
		 String [] result = null;
		 result =new String[4];
		 String Vname =null;
		 String Vname2 =null;
		 String bindid=null;
		 String bscName=null;
		 String btsfdn=null;
		 String btsname=null;
		 String btstype=null;
		 String FddTddInd=null;
		 String eNodeBFdn=null;
		 String eNodeBName=null;
		 String eNodeBType=null;
		 String NodeBFdn=null;
		 String NodeBName=null;
		 String NodeBType=null;
		 String RNCName=null;
		 String transid=null;
		 String vparentype="0";
		 int n ;
		 
		 
		 Vname=RowLine;
		 //READ BINDID line
		 if (Vname.contains("MBTS BINDID="))  {
				 n=0;
				 n = Vname.indexOf("MBTS BINDID=");
				 Vname=Vname.substring(n+13, Vname.length()-4);
				 bindid=Vname;
				 System.out.println("BINDID  is : " + bindid);
				 //logger.info("BINDID  is : " + bindid);
		 }
		//READ BTS line
		if (Vname.contains("BTS BSCName="))  {
				n=0;
				n = Vname.indexOf("BSCName=\"");
				Vname=Vname.substring(n+9, Vname.length()-4);
				Vname2=Vname;
				n = Vname2.indexOf("\"");
				Vname2=Vname2.substring(0, n);
				bscName=Vname2;
				System.out.println("BSCName  is : " + bscName); // value BSCName
				Vname=Vname.substring(n+1, Vname.length());
				//System.out.println("here1  is : " + Vname);
				//.info("BSCName  is : " + bscName);
				
				Vname2=Vname;
				n = Vname2.indexOf("\"");
				Vname2=Vname2.substring((n+1),Vname2.length());
				Vname=Vname.substring(n+1, Vname.length());
				Vname2=Vname;
				n = Vname2.indexOf("\"");
				Vname2=Vname2.substring(0, n);
				btsfdn=Vname2;
				System.out.println("BTSFdn  is : " + btsfdn); // to be splited to get Ne value and BTS value
				Vname=Vname.substring(n+1, Vname.length());
				//System.out.println("here2  is : " + Vname);
				
				
				// split btsfdnto get Ne value and BTS value
				String var1=btsfdn;
				n = Vname2.indexOf(",");
				String var2=var1.substring((n+1), var1.length());
				var1=var1.substring(0, n);
				
				n = var1.indexOf("=");
				var1=var1.substring((n+1), var1.length());
				System.out.println("var1 NE  is : " + var1);  //value NE
				n = var2.indexOf("=");
				var2=var2.substring((n+1), var2.length());
				System.out.println("var2 BTS  is : " + var2); //value BTS
				///////////////////////////////////////////////////

				Vname2=Vname;
				n = Vname2.indexOf("\"");
				Vname2=Vname2.substring((n+1),Vname2.length());
				Vname=Vname.substring(n+1, Vname.length());
				Vname2=Vname;
				n = Vname2.indexOf("\"");
				Vname2=Vname2.substring(0, n);
				btsname=Vname2;
				System.out.println("BTSName  is : " + btsname);//value BTSName
				Vname=Vname.substring(n+1, Vname.length());
				//System.out.println("here3  is : " + Vname);
				
				Vname2=Vname;
				n = Vname2.indexOf("\"");
				Vname2=Vname2.substring((n+1),Vname2.length());
				btstype=Vname2;
				System.out.println("BTSType  is : " + btstype); //value BTSType
				
				
				//Get Parent Type from Node_active
				 vparentype=GetParentType(var1,bscName);
				
				transid=Gyear+"_"+ "CHILDPARENT"+"_"+localgetseqNbr(0);
				 PreparedStatement stmt = con.prepareStatement("insert into NODE_CHILD_PARENT (ID,CREATION_DATE,LAST_MODIFIED_DATE,CHILD_ID_1,CHILD_NAME_1,CHILD_ID_2,CHILD_NAME_2,CHILD_TYPE,CHILD_MODEL,PARENT_ID,PARENT_NAME,PARENT_TYPE,PARENT_MODEL,FILE_NAME,TRANS_SOURCE,FROM_TRANS_ID,TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,DOMAIN,VENDOR) "
				 		+ " values('" + transid +"'  ,sysdate ,sysdate,'" + var2 +"','" + btsname + "','0','0','" + btstype + "','BTS','" + var1 +"', '" + bscName +"','" + vparentype + "','BSC','" + filename +"','0','0','0','0','1','Mobile Access Domain','"+ Gprovider +"') "); 
                 stmt.executeUpdate();
			     stmt.close();

		}
		//READ eNodeB line
		if (Vname.contains("eNodeB FddTddInd="))  {
				n=0;
				n = Vname.indexOf("FddTddInd=\"");
				Vname=Vname.substring(n+11, Vname.length()-4);
				Vname2=Vname;
				n = Vname2.indexOf("\"");
				Vname2=Vname2.substring(0, n);
				FddTddInd=Vname2;
				System.out.println("FddTddInd  is : " + FddTddInd);
				Vname=Vname.substring(n+1, Vname.length());
				//System.out.println("here1  is : " + Vname);
				//logger.info("FddTddInd  is : " + FddTddInd);
				
				Vname2=Vname;
				n = Vname2.indexOf("\"");
				Vname2=Vname2.substring((n+1),Vname2.length());
				Vname=Vname.substring(n+1, Vname.length());
				Vname2=Vname;
				n = Vname2.indexOf("\"");
				Vname2=Vname2.substring(0, n);
				eNodeBFdn=Vname2;
				System.out.println("eNodeBFdn  is : " + eNodeBFdn); //split to get value of NE
				Vname=Vname.substring(n+1, Vname.length());
				//System.out.println("here2  is : " + Vname);
				
				//split to get value of NE
				String var4=eNodeBFdn;
				n = var4.indexOf("=");
				var4=var4.substring((n+1), var4.length());
				System.out.println("enodeB value NE  is : " + var4); //value var4  is NE
				////////////////////////////////////////////////////
				
				Vname2=Vname;
				n = Vname2.indexOf("\"");
				Vname2=Vname2.substring((n+1),Vname2.length());
				Vname=Vname.substring(n+1, Vname.length());
				Vname2=Vname;
				n = Vname2.indexOf("\"");
				Vname2=Vname2.substring(0, n);
				eNodeBName=Vname2;
				System.out.println("eNodeBName  is : " + eNodeBName); //value eNodeBName
				Vname=Vname.substring(n+1, Vname.length());
				//System.out.println("here3  is : " + Vname);
				
				Vname2=Vname;
				n = Vname2.indexOf("\"");
				Vname2=Vname2.substring((n+1),Vname2.length());
				eNodeBType=Vname2;
				System.out.println("eNodeBType  is : " + eNodeBType); //value eNodeBType
				
				transid=Gyear+"_"+ "CHILDPARENT"+"_"+localgetseqNbr(0);
				PreparedStatement stmt1 = con.prepareStatement("insert into NODE_CHILD_PARENT (ID,CREATION_DATE,LAST_MODIFIED_DATE,CHILD_ID_1,CHILD_NAME_1,CHILD_ID_2,CHILD_NAME_2,CHILD_TYPE,CHILD_MODEL,PARENT_ID,PARENT_NAME,PARENT_TYPE,PARENT_MODEL,FILE_NAME,TRANS_SOURCE,FROM_TRANS_ID,TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,DOMAIN,VENDOR) "
				 		+ " values('" + transid +"'  ,sysdate ,sysdate,'" + var4 +"','" + eNodeBName + "','0','0','" + eNodeBType + "','eNodeB','0', '0','0','0','" + filename +"','0','0','0','0','1','Mobile Access Domain','"+ Gprovider +"') "); 
                 stmt1.executeUpdate();
			     stmt1.close();
			 
		 }
		//READ NodeB line
		if (Vname.contains("NodeB NodeBFdn="))  {
				n=0;
				n=0;
				n = Vname.indexOf("NodeBFdn=\"");
				Vname=Vname.substring(n+10, Vname.length()-4);
				Vname2=Vname;
				n = Vname2.indexOf("\"");
				Vname2=Vname2.substring(0, n);
				NodeBFdn=Vname2;
				System.out.println("NodeBFdn  is : " + NodeBFdn); // to be splieted to get value of NE
				Vname=Vname.substring(n+1, Vname.length());
				//System.out.println("here1  is : " + Vname);
				//logger.info("NodeBFdn  is : " + NodeBFdn);
				
				// Split to get value of NE
				String var3 =NodeBFdn;
				n = Vname2.indexOf("=");
				var3=var3.substring((n+1), var3.length());
				System.out.println("nodeB Ne value  is : " + var3);// value  var3 is the value of NE
				////////////////////////////////////////////////////
				Vname2=Vname;
				n = Vname2.indexOf("\"");
				Vname2=Vname2.substring((n+1),Vname2.length());
				Vname=Vname.substring(n+1, Vname.length());
				Vname2=Vname;
				n = Vname2.indexOf("\"");
				Vname2=Vname2.substring(0, n);
				NodeBName=Vname2;
				System.out.println("NodeBName  is : " + NodeBName); //value  NodeBName
				Vname=Vname.substring(n+1, Vname.length());
				//System.out.println("here2  is : " + Vname);
				
				Vname2=Vname;
				n = Vname2.indexOf("\"");
				Vname2=Vname2.substring((n+1),Vname2.length());
				Vname=Vname.substring(n+1, Vname.length());
				Vname2=Vname;
				n = Vname2.indexOf("\"");
				Vname2=Vname2.substring(0, n);
				NodeBType=Vname2;
				System.out.println("NodeBType  is : " + NodeBType);//value NodeBType
				Vname=Vname.substring(n+1, Vname.length());
				//System.out.println("here3  is : " + Vname);
				
				Vname2=Vname;
				n = Vname2.indexOf("\"");
				Vname2=Vname2.substring((n+1),Vname2.length());
				RNCName=Vname2;
				System.out.println("RNCName  is : " + RNCName);//value RNCName
				
				//Get Parent Type from Node_active
				 vparentype=GetParentType("0",RNCName);
				 
				transid=Gyear+"_"+ "CHILDPARENT"+"_"+localgetseqNbr(0);
				PreparedStatement stmt2 = con.prepareStatement("insert into NODE_CHILD_PARENT (ID,CREATION_DATE,LAST_MODIFIED_DATE,CHILD_ID_1,CHILD_NAME_1,CHILD_ID_2,CHILD_NAME_2,CHILD_TYPE,CHILD_MODEL,PARENT_ID,PARENT_NAME,PARENT_TYPE,PARENT_MODEL,FILE_NAME,TRANS_SOURCE,FROM_TRANS_ID,TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,DOMAIN,VENDOR) "
				 		+ " values('" + transid +"'  ,sysdate ,sysdate,'" + var3 +"','" + NodeBName + "','0','0','" + NodeBType + "','NodeB','0', '" + RNCName +"','" + vparentype + "','RNC','" + filename +"','0','0','0','0','1','Mobile Access Domain','"+ Gprovider +"') "); 
                 stmt2.executeUpdate();
			     stmt2.close();
			 
		}
		//READ BTS3900 line
		 if (Vname.contains("BTS3900 BTS3900Fdn="))  {
				 n=0;
				 n = Vname.indexOf("BTS3900Type=");
				 String vartyp=Vname.substring((n+13), Vname.length()-4);
				 System.out.println("BTS3900Type=  " + vartyp);
				 Vname=Vname.substring(0, n);
				 n = Vname.indexOf("BTS3900Name=");
				 String varname=Vname.substring((n+13), Vname.length()-2);
				 System.out.println("BTS3900Name=  " + varname);
				 Vname=Vname.substring(0, n);
				 n = Vname.indexOf("NE=");
				 String varne=Vname.substring((n+3), Vname.length()-2);
				 System.out.println("NE=  " + varne);
				 Vname=Vname.substring(0, n);
				 
				//Get Parent Type from Node_active
				// vparentype=GetParentType("0",RNCName);
				 
				transid=Gyear+"_"+ "CHILDPARENT"+"_"+localgetseqNbr(0);
				PreparedStatement stmt2 = con.prepareStatement("insert into NODE_CHILD_PARENT (ID,CREATION_DATE,LAST_MODIFIED_DATE,CHILD_ID_1,CHILD_NAME_1,CHILD_ID_2,CHILD_NAME_2,CHILD_TYPE,CHILD_MODEL,PARENT_ID,PARENT_NAME,PARENT_TYPE,PARENT_MODEL,FILE_NAME,TRANS_SOURCE,FROM_TRANS_ID,TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,DOMAIN,VENDOR) "
				 		+ " values('" + transid +"'  ,sysdate ,sysdate,'" + varne +"','" + varname + "','0','0','" + vartyp + "','BTS3900','0', '0','0','BTS3900','" + filename +"','0','0','0','0','1','Mobile Access Domain','"+ Gprovider +"') "); 
                 stmt2.executeUpdate();
			     stmt2.close();
		 }

		 return null;
	 }

	 
	 private static void copyFiles(File source, File dest) throws IOException {
		 
		 Files.copy(source.toPath(), dest.toPath(),StandardCopyOption.COPY_ATTRIBUTES,StandardCopyOption.REPLACE_EXISTING);
    
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
 
 private static String GetParentType(String nodeid, String Nodename) throws SQLException {
	    String vparenttyp = null ;
	    String query2=null;
		Statement stmttype = null;
		if (StringUtils.equalsIgnoreCase (nodeid,"0")) {
			query2 = "select NODE_TYPE from NODE_ACTIVE where NODE_NAME='"+ Nodename +"' and DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"; 
		} else {
			query2 = "select NODE_TYPE from NODE_ACTIVE where NODE_ID='"+ nodeid +"' and NODE_NAME='"+ Nodename +"' and DOMAIN='Mobile Access Domain' and VENDOR='" + Gprovider +"'"; 
		}
		
		     
	    stmttype = con.createStatement();
	    ResultSet rs2 = stmttype.executeQuery(query2);
	         
	    while (rs2.next()) {
	    	try {
	    		vparenttyp= rs2.getString("NODE_TYPE");  
	    	}
	    	catch(Exception e)  
			{  
				logger.info("error at GetParentType is :"+ e.toString());
				System.out.println("error at GetParentType is :"+ e.toString()); 
			}
	     }
	     rs2.close();
	     stmttype.close();

		return vparenttyp;
	  }
 
 private static String localgetseqNbr(int n1) throws SQLException {

		String min = null ;
		Statement stmttype = null;
	 
	     
	         
	       String SeqName = null;
	    switch(n1)
	    {
	    case 0:
			SeqName = "NODECHILDPARENT_SEQ";
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
