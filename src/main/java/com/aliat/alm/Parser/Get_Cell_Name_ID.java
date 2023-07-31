package com.aliat.alm.Parser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

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

public class Get_Cell_Name_ID {
	
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
					
		 	objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/"+"almconfig.dat"));
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
			 
			 objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/"+"almcircle.dat"));
			 while ((strCurrentLine1 = objReader1.readLine()) != null){
				 String data = strCurrentLine1;
				 String[] data1 ;
				 data1=data.split(";",-1);
				 circleid=data1[1];
			 }
			 objReader1.close();	 

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
			String lofilename="Get_Cell_Name_ID-"+dtf.format(now)+".log";
			
			//get only year from today date
			DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        	LocalDateTime now1 = LocalDateTime.now();
        	Gyear=dtf1.format(now1).substring(0,4);
        	//System.out.println(Gyear);  

			logger = Logger.getLogger("MyLog"); 
			logger.setUseParentHandlers(false);

						
			// This block configure the logger with handler and formatter  and PATH
			
	        fh = new FileHandler(logpath+"/"+lofilename);
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
				 			  
				 			 System.out.println("In process to Get_GCell_Name_ID for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR") );
				 			logger.info("In process to Get_GCell_Name_ID for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));

				 			Statement stmt0 = null;
				 			stmt0 = con.createStatement(); 
				 		    String sqlStmt0 = "select distinct GBTSFUNCTIONNAME from NODE_GCELL where DOMAIN='" + rsinit2.getString("DOMAIN") +"' and VENDOR='" + rsinit2.getString("VENDOR") +"'";          
				 		    ResultSet rs0 = stmt0.executeQuery(sqlStmt0);
				 		    while (rs0.next()) {
				 		    	if (StringUtils.equalsIgnoreCase (rs0.getString("GBTSFUNCTIONNAME"),"0")) {
				 		    		//do nothing  we cannot update cellanem with 0 data
				 		    	}else {
				 		    		//get cellname and cellid
				 		    		GetCellNameIDfromGCELL(rs0.getString("GBTSFUNCTIONNAME"),rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"));	
				 		    	}
				 		    	
				 		    }
				 		    rs0.close();
				 		    stmt0.close();
				 		    
				 		    System.out.println("Get_GCell_Name_ID COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR") );
				 			logger.info("In process to Get_GCell_Name_ID COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
				 			
				 			
				 			
				 			System.out.println("In process to Get_UCell_Name_ID for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR") );
				 			logger.info("In process to Get_UCell_Name_ID for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
				 		    //// Get from UCELL
				 		    Statement stmt = null;
				 			stmt = con.createStatement(); 
				 		    String sqlStmt = "select distinct NODEBFUNCTIONNAME from NODE_UCELL where DOMAIN='" + rsinit2.getString("DOMAIN") +"' and VENDOR='" + rsinit2.getString("VENDOR") +"'";          
				 		    ResultSet rs = stmt.executeQuery(sqlStmt);
				 		    while (rs.next()) {
				 		    	if (StringUtils.equalsIgnoreCase (rs.getString("NODEBFUNCTIONNAME"),"0")) {
				 		    		//do nothing  we cannot update cellanem with 0 data
				 		    	}else {
				 		    		//get cellname and cellid
				 		    		GetCellNameIDfromUCELL(rs.getString("NODEBFUNCTIONNAME"),rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR"));	
				 		    	}
				 		    	
				 		    }
				 		    rs.close();
				 		    stmt.close();
				 			
				 			System.out.println("Get_UCell_Name_ID COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR") );
				 			logger.info("Get_UCell_Name_ID COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
				 			  
				 		  }
			 	   }
			 	  rsinit2.close();
			 	  stmtinit2.close();
			
			
				
			con.close();
			conalm.close();
		//} // end try
		//	catch(Exception e)  
		//	{  
		//	logger.info("error at main is :"+ e.toString());
		//	System.out.println("error at main is :"+ e.toString()); 
		//	} 
		
	   }
	
 
	 private static void GetCellNameIDfromUCELL(String vcellname,String vdomain,String vvendor) throws InterruptedException, SQLException, ParseException {
		try {
				 Statement stmtp=null;
				 Statement stmtp1=null;
				 Statement stmtp2=null;
				 PreparedStatement stmt0;
				 PreparedStatement stmtinsert1=null;
				 
				 System.out.println(vcellname);
				 //try {
				 String queryp = "select CELLID,CELLNAME,LOCELL from NODE_UCELL where NODEBNAME like '"+ vcellname+"%' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
				 stmtp = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			     ResultSet rsp = stmtp.executeQuery(queryp);
			     rsp.last();
			 	    int totalrec = rsp.getRow(); 
			 	   rsp.beforeFirst();
			 	   if (totalrec >0 ) {
			 		   System.out.println(totalrec);
			 		  while (rsp.next()) {
			 			 String streec = null;
			 			System.out.println(rsp.getString("CELLID") +":"+ rsp.getString("CELLNAME")+":"+ rsp.getString("LOCELL"));
			 			Statement stmtrec = null;
			 			stmtrec = con.createStatement(); 
					    String sqlStmtrec = "select CELLID,CELLNAME from NODE_UCELL where NODEBNAME = '"+ vcellname+"'  and LOCELL='" + rsp.getString("LOCELL") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
					    ResultSet rsrec = stmtrec.executeQuery(sqlStmtrec);
					    while (rsrec.next()) {
					    	System.out.println(rsrec.getString("CELLID") +":"+ rsrec.getString("CELLNAME"));
					    	//set celname and cellid into GCELL where  GBTSFUNCTIONNAME=vcellname
					    	stmtinsert1 = con.prepareStatement("update  NODE_UCELL set CELLID='"+rsp.getString("CELLID")+"' ,CELLNAME='"+rsp.getString("CELLNAME")+"',UPDATE_DATE =sysdate where NODEBFUNCTIONNAME='"+vcellname+"' and LOCELL='"+ rsp.getString("LOCELL") +"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
					    	stmtinsert1.executeUpdate();
				    		stmtinsert1.close();
					    }
					    rsrec.close(); 
					    stmtrec.close();
		
			 		  }
			 	   }
				 rsp.close();
				 stmtp.close();
		}
		 catch(Exception e){
		      System.err.println(e);
		      e.printStackTrace();
		      logger.info("Error GetCellNameIDfromUCELL in Get_Cell_Name_ID : "+e);
		   }
	   
   }

	 
	 private static void GetCellNameIDfromGCELL(String vcellname,String vdomain,String vvendor) throws InterruptedException, SQLException, ParseException {
		 try {
				 Statement stmtp=null;
				 Statement stmtp1=null;
				 Statement stmtp2=null;
				 PreparedStatement stmt0;
				 PreparedStatement stmtinsert1=null;
		         
				 //try {
				 String queryp = "select CELLID,CELLNAME from NODE_GCELL where CELLNAME like '"+ vcellname+"%' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
				 stmtp = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); 
			     ResultSet rsp = stmtp.executeQuery(queryp);
			     rsp.last();
			 	    int totalrec = rsp.getRow(); 
			 	   rsp.beforeFirst();
			 	   if (totalrec >0 ) {
			 		   System.out.println(totalrec);
			 		   int i=1;
			 		   int j=0;
			 		  while (rsp.next()) {
			 			  j=(i-1);
			 			  String streec=vcellname+i;
			 			 System.out.println(streec);
			 			Statement stmtrec = null;
			 			stmtrec = con.createStatement(); 
					    String sqlStmtrec = "select CELLID,CELLNAME from NODE_GCELL where CELLNAME = '"+ streec+"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'";          
					    ResultSet rsrec = stmtrec.executeQuery(sqlStmtrec);
					    while (rsrec.next()) {
					    	System.out.println(rsrec.getString("CELLID") +":"+ rsrec.getString("CELLNAME"));
					    	
					    	//set celname and cellid into GCELL where  GBTSFUNCTIONNAME=vcellname
					    	stmtinsert1 = con.prepareStatement("update  NODE_GCELL set CELLID='"+rsrec.getString("CELLID")+"' ,CELLNAME='"+rsrec.getString("CELLNAME")+"',UPDATE_DATE =sysdate where GBTSFUNCTIONNAME='"+vcellname+"' and GLOCELLID='"+j+"' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'");
					    	stmtinsert1.executeUpdate();
				    		stmtinsert1.close();
					    }
					    rsrec.close(); 
					    stmtrec.close();
					    i=i+1;
			 		  }
			 	   }
				 rsp.close();
				 stmtp.close();
	 }
	 catch(Exception e){
	      System.err.println(e);
	      e.printStackTrace();
	      logger.info("Error GetCellNameIDfromGCELL in Get_Cell_Name_ID : "+e);
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
			case 21:
				SeqName = "NODETRANSACTION_SEQ";
			case 22:
				SeqName = "NODECHILDPARENT_SEQ";
				break;
				
		    }
		      String query2 = "select "+SeqName+".nextval as nbr from dual";      
		          stmttype = con.createStatement();
		          ResultSet rs2 = stmttype.executeQuery(query2);
		         
		          while (rs2.next()) {
		           min= rs2.getString("nbr");    
		          	}
		          rs2.close();

		          stmttype.close();

					 return min;

		  }


}
