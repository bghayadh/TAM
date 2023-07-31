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
  

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UpdateUniqueNodeID_WIN {
	
	private static final CopyOption REPLACE_EXISTING = null;
	static Connection con;
	static Connection conalm;
    static String Gyear ;
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
	
	public static void main(String[] args) throws SecurityException, IOException, InterruptedException, SQLException, ParseException {
		//try {
		    // Read all required paths ex(DB1,DB2,Log file, Path to read file AIM ,path to copy processed)
					
		 	objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\"+"almconfig.dat"));
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
				 if (data.contains("db2path")) {
					 data1=data.split(";",-1);
					 db2path=data1[1];
					 username2=data1[2];
					 password2=data1[3];
					 //System.out.println("db2path found :" + db2path);
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
			String lofilename="UpdateUniqueNodeID-"+dtf.format(now)+".log";
			
			//get only year from today date
			DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        	LocalDateTime now1 = LocalDateTime.now();
        	Gyear=dtf1.format(now1).substring(0,4);
        	//System.out.println(Gyear);  

			logger = Logger.getLogger("MyLog"); 
			logger.setUseParentHandlers(false);

						
			// This block configure the logger with handler and formatter  and PATH
			
	        fh = new FileHandler(logpath+"\\"+lofilename);
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);

				DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
				//conalm = DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:alm","alm","alm");
				conalm = DriverManager.getConnection (db1path,username1,password1);

				try {
				    //con= DriverManager.getConnection(dbURL,username,password);
				    con= DriverManager.getConnection(db2path,username2,password2);
				System.out.println("Connected to oracle DB");
				} catch (SQLException e) {
				       System.out.println("Opss, error");
				       e.printStackTrace();
				   }
				
				/// select different domain and vendor from temp node active table
				Statement stmtinit2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  
		    	 String sqlStmtinit2 = "select distinct DOMAIN, VENDOR from NODE_ACTIVE";          
				    ResultSet rsinit2 = stmtinit2.executeQuery(sqlStmtinit2);
				    rsinit2.last();
			 	    int totalrecinit2 = rsinit2.getRow(); 
			 	   rsinit2.beforeFirst();
			 	   if (totalrecinit2 > 0 ) {
				 		  while (rsinit2.next()) {
				 			 System.out.println("In process to Update UNIQUE_NODE_ID FIELD for " + rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
						        // Update UNIQUE_NODE_ID in Node_active table
								UpdateUniqueNodeIddata(rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR")); 
						System.out.println("Update UNIQUE_NODE_ID FIELD COMPLETED ! for " + rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR") );
						logger.info("Update UNIQUE_NODE_ID FIELD COMPLETED ! for " + rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
				 		  }
			 	   }
				
			

				
			con.close();
			conalm.close();
		//} // end try
			//catch(Exception e)  
			//{  
			//logger.info("error at main is :"+ e.toString());
			//System.out.println("error at main is :"+ e.toString()); 
			//} 
		
	   }

	 private static void UpdateUniqueNodeIddata(String vdomain,String vvendor) throws InterruptedException, SQLException, ParseException {
		try {
				 Statement stmtp=null;
				 PreparedStatement stmt0;
				 String varcontroller="0";
				 int nbr=1;
				 
				 String queryp = "select NODE_ID,NODE_NAME from NODE_ACTIVE where STATUS <>'1' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
				 stmtp = con.createStatement();
			     ResultSet rsp = stmtp.executeQuery(queryp);
				 while (rsp.next()) {
					 varcontroller="";
					 varcontroller=GetControllerID(rsp.getString("NODE_ID"),rsp.getString("NODE_NAME"),vdomain,vvendor);
					 if (StringUtils.equalsIgnoreCase (varcontroller,"")) {
						    // do nothing controller not found
					 } else {
						 stmt0 = con.prepareStatement("update node_active set UNIQUE_NODE_ID = UNIQUE_NODE_ID ||'_' || '"+ varcontroller +"',STATUS='1' where NODE_ID='"+ rsp.getString("NODE_ID") +"' and NODE_NAME='"+ rsp.getString("NODE_NAME") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
			 		     stmt0.executeUpdate();
					     stmt0.close(); 
					 }
				     //System.out.println("line is :"+ nbr +"   " + rsp.getString("NODE_ID")); 
				     nbr=nbr+1;
				 }
				 rsp.close();
				 stmtp.close();
		}
		catch(Exception e){
		      System.err.println("Error in UpdateUniqueNodeIddata  UpdateUniqueNodeID: "+e);
		      e.printStackTrace();
		      logger.info("Error in UpdateUniqueNodeIddata  UpdateUniqueNodeID: "+e);
		   }
	 	}

	 private static String GetControllerID(String nodeid, String nodename,String vdomain,String vvendor) throws SQLException {
		    String vcontroller = "" ;

			Statement stmttype = null;
			String query2 = "select PARENT_MODEL from NODE_CHILD_PARENT  where CHILD_ID_1= '"+ nodeid +"' and CHILD_NAME_1='"+ nodename +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";      
		    stmttype = con.createStatement();
		    ResultSet rs2 = stmttype.executeQuery(query2);
		         
		    while (rs2.next()) {
		    	try {
		    	//System.out.println("vcontroller is :"+ vcontroller); 
		    		vcontroller= vcontroller.trim() +rs2.getString("PARENT_MODEL");  
		    	}
		    	catch(Exception e)  
				{  
					logger.info("error at GetControllerID is :"+ e.toString());
					System.out.println("error at GetControllerID is :"+ e.toString()); 
				}
		     }
		     rs2.close();
		     stmttype.close();

			return vcontroller;
		  }

}
