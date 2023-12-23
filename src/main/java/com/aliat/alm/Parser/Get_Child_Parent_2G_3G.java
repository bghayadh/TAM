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

public class Get_Child_Parent_2G_3G {

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
			String lofilename="GET_CHILD_PARENT_2G_3G-"+dtf.format(now)+".log";
			
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
				 			 System.out.println("In process to Get Child Parent for 2G & 3G for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR") );

				 			GetChildParentfromUCELL(rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR")); 
				 			GetChildParentfromGCELL(rsinit2.getString("DOMAIN"),rsinit2.getString("VENDOR")); 
				 			System.out.println("Get Child Parent for 2G & 3G COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR") );
				 			logger.info("Get Child Parent for 2G & 3G COMPLETED for "+ rsinit2.getString("DOMAIN") +","+ rsinit2.getString("VENDOR"));
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
	
 
	 private static void GetChildParentfromUCELL(String vdomain,String vvendor) throws InterruptedException, SQLException, ParseException {
		 try {
				 Statement stmtp=null;
				 Statement stmtp1=null;
				 Statement stmtp2=null;
				 PreparedStatement stmt0;
		         String vchildid="0";
		         String vchildname="0";
		         String vchildtype="0";
		         String vchildmodel="0";
		         String vparentid="0";
		         String vparentname="0";
		         String vparenttype="0";
		         String vparentmodel="0";
		         String vfilename="0";
		         
				 
				 //Get distinct NODEBNAME, NODE_PK from NODE_UCELL it is  the parent of all data in UCELL having NODEBFUNCTIONNAME =NODEBNAME whcih theya are child
				 
				 
		         String queryp = "select distinct a.NODEBNAME ,a.node_pk from NODE_4G a , NODE_ACTIVE b where a.node_pk=b.node_pk and  b.NODE_MODEL in ('BSC','RNC') and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'"; 
				 stmtp = con.createStatement();
			     ResultSet rsp = stmtp.executeQuery(queryp);
				 while (rsp.next()) {
					  vchildid="0";
			          vchildname="0";
			          vchildtype="0";
			          vchildmodel="0";
			          vparentid="0";
			          vparentname="0";
			          vparenttype="0";
			          vparentmodel="0";
			          vfilename="0";
			          
					 String queryp1 = "select NODE_ID,NODE_NAME,NODE_TYPE,NODE_MODEL from NODE_ACTIVE where NODE_PK='"+ rsp.getString("NODE_PK") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
					 stmtp1 = con.createStatement();
				     ResultSet rsp1 = stmtp1.executeQuery(queryp1);
				     while (rsp1.next()) {
				    	 vparentid=rsp1.getString("NODE_ID");
				    	 vparentname=rsp1.getString("NODE_NAME");
				    	 vparenttype=rsp1.getString("NODE_TYPE");
				    	 vparentmodel=rsp1.getString("NODE_MODEL");
				     }
				     rsp1.close();
					 stmtp1.close();
					 
					 
					 String queryp2 = "select distinct a.NODE_PK,a.FILENAME,b.NODE_ID,b.NODE_NAME,b.NODE_TYPE,b.NODE_MODEL from NODE_4G a,NODE_ACTIVE b where a.NODEBFUNCTIONNAME ='"+ rsp.getString("NODEBNAME") + "' and a.NODE_PK=b.NODE_PK and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'"; 
					 stmtp2 = con.createStatement();
				     ResultSet rsp2 = stmtp2.executeQuery(queryp2);
				     while (rsp2.next()) {
				    	 vchildid=rsp2.getString("NODE_ID");
				    	 vchildname=rsp2.getString("NODE_NAME");
				    	 vchildtype=rsp2.getString("NODE_TYPE");
				    	 vchildmodel=rsp2.getString("NODE_MODEL");
				    	 vfilename=rsp2.getString("FILENAME");
				    	 
				    	
				     }
				     rsp2.close();
					 stmtp2.close();
					 
					 
					 
					 //insert into CHILD_PARENT TABLE
					 if (StringUtils.equalsIgnoreCase (vchildid,"0")) {
						   // do nothing
					 } else {
						 String childid=Gyear+"_"+ "CHILDPARENT"+"_"+localgetseqNbr(22);
						 stmt0 = con.prepareStatement("insert into  NODE_CHILD_PARENT(ID,CREATION_DATE,LAST_MODIFIED_DATE,CHILD_ID_1,CHILD_NAME_1,CHILD_ID_2,CHILD_NAME_2,CHILD_TYPE,CHILD_MODEL,PARENT_ID,PARENT_NAME,PARENT_TYPE,PARENT_MODEL,FILE_NAME,TRANS_SOURCE,FROM_TRANS_ID,TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,DOMAIN,VENDOR) "
				 			 		+  "values ('"+ childid  +"',sysdate, sysdate,'" + vchildid + "','"+ vchildname +"','0','"+ rsp.getString("NODEBNAME") +"','"+ vchildtype +"','"+ vchildmodel +"','"+ vparentid +"','"+ vparentname +"','"+ vparenttype +"','"+ vparentmodel +"','"+ vfilename +"','0','0','0','0','1','" + vdomain +"','" + vvendor +"')  ");
						 stmt0.executeUpdate();
						 stmt0.close();
					 }
			    	 
					 
			     }
				 rsp.close();
				 stmtp.close();
		 }  
		 catch(Exception e){
		      System.err.println("Error in GetChildParentfromUCELL: "+e);
		      e.printStackTrace();
		      logger.info("Error in GetChildParentfromUCELL: "+e);
		   }	
		 
	   
}

	 
	 private static void GetChildParentfromGCELL(String vdomain,String vvendor) throws InterruptedException, SQLException, ParseException {
		 try {
				 Statement stmtp=null;
				 Statement stmtp1=null;
				 Statement stmtp2=null;
				 PreparedStatement stmt0;
		         String vchildid="0";
		         String vchildname="0";
		         String vchildtype="0";
		         String vchildmodel="0";
		         String vparentid="0";
		         String vparentname="0";
		         String vparenttype="0";
		         String vparentmodel="0";
		         String vfilename="0";
		         
				 //try {
				 //Get distinct NODEBNAME, NODE_PK from NODE_UCELL it is  the parent of all data in UCELL having NODEBFUNCTIONNAME =NODEBNAME whcih theya are child
		
		         String queryp = "select distinct  SUBSTR(a.CELLNAME, 1, LENGTH(a.CELLNAME)-1) as CELLNAME ,a.node_pk from NODE_2G a , NODE_ACTIVE b where a.node_pk=b.node_pk and  b.NODE_MODEL in ('BSC','RNC') and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'"; 
				 stmtp = con.createStatement();
			     ResultSet rsp = stmtp.executeQuery(queryp);
				 while (rsp.next()) {
					  vchildid="0";
			          vchildname="0";
			          vchildtype="0";
			          vchildmodel="0";
			          vparentid="0";
			          vparentname="0";
			          vparenttype="0";
			          vparentmodel="0";
			          vfilename="0";
			          
					 String queryp1 = "select NODE_ID,NODE_NAME,NODE_TYPE,NODE_MODEL from NODE_ACTIVE where NODE_PK='"+ rsp.getString("NODE_PK") + "' and DOMAIN='" + vdomain +"' and VENDOR='" + vvendor +"'"; 
					 stmtp1 = con.createStatement();
				     ResultSet rsp1 = stmtp1.executeQuery(queryp1);
				     while (rsp1.next()) {
				    	 vparentid=rsp1.getString("NODE_ID");
				    	 vparentname=rsp1.getString("NODE_NAME");
				    	 vparenttype=rsp1.getString("NODE_TYPE");
				    	 vparentmodel=rsp1.getString("NODE_MODEL");
				     }
				     rsp1.close();
					 stmtp1.close();
					 
					 String varfile=rsp.getString("CELLNAME");
					 System.out.println("vlue of varfile is:  " + varfile);
					 if (StringUtils.equalsIgnoreCase (varfile,null)) {
						    //donothing
					 } else {
					 			 
						 String queryp2 = "select distinct a.NODE_PK,a.FILENAME,b.NODE_ID,b.NODE_NAME,b.NODE_TYPE,b.NODE_MODEL from NODE_2G a,NODE_ACTIVE b where a.GBTSFUNCTIONNAME ='"+ varfile + "' and a.NODE_PK=b.NODE_PK and b.DOMAIN='" + vdomain +"' and b.VENDOR='" + vvendor +"'"; 
						 //System.out.println(queryp2);
						 stmtp2 = con.createStatement();
					     ResultSet rsp2 = stmtp2.executeQuery(queryp2);
					     while (rsp2.next()) {
					    	 vchildid=rsp2.getString("NODE_ID");
					    	 vchildname=rsp2.getString("NODE_NAME");
					    	 vchildtype=rsp2.getString("NODE_TYPE");
					    	 vchildmodel=rsp2.getString("NODE_MODEL");
					    	 vfilename=rsp2.getString("FILENAME");
					    	 
					    	
					     }
					     rsp2.close();
						 stmtp2.close();
					 
					 
					 
						 //insert into CHILD_PARENT TABLE
						 if (StringUtils.equalsIgnoreCase (vchildid,"0")) {
							   // do nothing
						 } else {
							 String childid=Gyear+"_"+ "CHILDPARENT"+"_"+localgetseqNbr(22);
							 stmt0 = con.prepareStatement("insert into  NODE_CHILD_PARENT(ID,CREATION_DATE,LAST_MODIFIED_DATE,CHILD_ID_1,CHILD_NAME_1,CHILD_ID_2,CHILD_NAME_2,CHILD_TYPE,CHILD_MODEL,PARENT_ID,PARENT_NAME,PARENT_TYPE,PARENT_MODEL,FILE_NAME,TRANS_SOURCE,FROM_TRANS_ID,TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,DOMAIN,VENDOR) "
					 			 		+  "values ('"+ childid  +"',sysdate, sysdate,'" + vchildid + "','"+ vchildname +"','0','" + rsp.getString("CELLNAME") + "','"+ vchildtype +"','"+ vchildmodel +"','"+ vparentid +"','"+ vparentname +"','"+ vparenttype +"','"+ vparentmodel +"','"+ vfilename +"','0','0','0','0','1','" + vdomain +"','" + vvendor +"')  ");
							 stmt0.executeUpdate();
							 stmt0.close();
						 }
				    } // end if varfile is not null
					 
			     }
				 rsp.close();
				 stmtp.close();
	 }  
	 catch(Exception e){
	      System.err.println("Error in GetChildParentfromGCELL: "+e);
	      e.printStackTrace();
	      logger.info("Error in GetChildParentfromGCELL: "+e);
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
