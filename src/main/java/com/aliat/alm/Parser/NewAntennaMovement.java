package com.aliat.alm.Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.commons.lang3.StringUtils;

public class NewAntennaMovement {

	static Connection con ;
	static Logger logger;
	static FileHandler fh;
	static BufferedReader objReader1 = null;
	static String strCurrentLine1;
	static String logpath;
	static String db1path;
	static String username1;
	static String password1;
	static String logsid="0";
	static String Gyear;
	static int nbOfErrors = 0;
	static String TransType = null;
	static List<String> OldSerials = new ArrayList<String>();
	static List<String> NewSerials = new ArrayList<String>();
	public static void main(String[] args) {
		List<String> tempOldSerials = new ArrayList<String>();
		List<String> tempNewSerials = new ArrayList<String>();
		List<String> tempOldSerials1 = new ArrayList<String>();
		List<String> tempNewSerials1 = new ArrayList<String>();
		String Domain=null,Vendor=null;
		
		
		try {
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
				 
			}
			 objReader1.close();
			 
			 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDateTime now = LocalDateTime.now();
				Gyear=dtf.format(now).substring(0,4);
				String lofilename="AntennaMovement-"+dtf.format(now)+".log";
			
			
				logger = Logger.getLogger("MyLog"); 
				logger.setUseParentHandlers(false);
				
				// This block configure the logger with handler and formatter  and PATH
		        fh = new FileHandler(logpath+"/"+lofilename);
		        logger.addHandler(fh);
		        SimpleFormatter formatter = new SimpleFormatter();  
		        fh.setFormatter(formatter);
		        
		        
			    	// Connect to alm DB 
					String dbURL =db1path;
					String username =username1;
					String password =password1;
					try {
					    con= DriverManager.getConnection(dbURL,username,password);
					System.out.println("Connected to oracle DB");
					} catch (SQLException e) {
					       System.out.println("Opss, error");
					       e.printStackTrace();
					       logger.info("Error : "+e);
					}
					
					
					//Check if there is a new data entered and needed to be manipulated in the NODE_ANTENNA
					Statement chckstmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					String chckquery = "SELECT DOMAIN,VENDOR,STATUS FROM EXECUTE_DOMAIN_VENDOR_ELEMENTS WHERE ELEMENT='NODE_ANTENNA'"
							+ " ORDER BY CREATION_DATE DESC fetch  first 1 rows only";
			 		ResultSet chckrs = chckstmt.executeQuery(chckquery);
			 		while(chckrs.next()) {
			 			if(chckrs.getString("STATUS").equalsIgnoreCase("IN PROCESS")) {
			 				Domain = chckrs.getString("DOMAIN");
			 				Vendor = chckrs.getString("VENDOR");
			 				
			 				//Get all the serial numbers from the NODE_ANTENNA having active record ='1'  and active record='2'
							logger.info("Get all the serial numbers from the NODE_ANTENNA having active record ='1'  and active record='2'");
							System.out.println("Get all the serial numbers from the NODE_ANTENNA having active record ='1' and active record='2'");
							NewSerials=getSerials(chckrs.getString("DOMAIN"),chckrs.getString("VENDOR"),"1");
							OldSerials=getSerials(chckrs.getString("DOMAIN"),chckrs.getString("VENDOR"),"2");
							tempNewSerials =getSerials(chckrs.getString("DOMAIN"),chckrs.getString("VENDOR"),"1");
							tempOldSerials = getSerials(chckrs.getString("DOMAIN"),chckrs.getString("VENDOR"),"2");
							tempNewSerials1 =getSerials(chckrs.getString("DOMAIN"),chckrs.getString("VENDOR"),"1");
							tempOldSerials1 =getSerials(chckrs.getString("DOMAIN"),chckrs.getString("VENDOR"),"2");
							
							//Checking for New Antennas
						 	logger.info("Checking if there is a new Antenna found after the New Parsing.");
							System.out.println("Checking if there is a new Antenna found after the New Parsing.");
							CheckNewAntennas(tempNewSerials,tempOldSerials);
							logger.info("Checking for a new Antenna after the New Parsing Completed.");
							System.out.println("Checking for a new Antenna after the New Parsing Completed.");
							
							//Checking for New Antennas
						 	logger.info("Checking if there is a Disappeared Antenna found after the New Parsing.");
							System.out.println("Checking if there is a Disappeared Antenna found after the New Parsing.");
							CheckDisappearedAntenna(tempNewSerials1,tempOldSerials1);
							logger.info("Checking for a Disappeared Antenna after the New Parsing Completed.");
							System.out.println("Checking for a Disappeared Antenna after the New Parsing Completed.");	
							
							logger.info("Checking if there is a Transferred Antenna found after the New Parsing.");
							System.out.println("Checking if there is a Transferred Antenna found after the New Parsing.");
							//Checking for Transferred Antennas
							CheckTransferredAntenna(NewSerials,OldSerials);
							logger.info("Checking for a Transferred Antenna after the New Parsing Completed.");
							System.out.println("Checking for a Transferred Antenna after the New Parsing Completed.");	
			 			}
			 		}
					
					
				 	con.close();
			}catch(Exception e){
		      System.err.println(e);
		      e.printStackTrace();
		      logger.info("Error : "+e);
		   }
					
	}
	
	private static List<String> getSerials(String domain,String vendor,String recordValue) throws SQLException{
		Statement stmt = null;
		String query=null;
		List<String> serials = new ArrayList<String>();
		stmt=con.createStatement();
		query = "SELECT SERIALNUMBER FROM NODE_ANTENNA WHERE ACTIVE_RECORD='"+recordValue+"' AND DOMAIN='"+domain+"' AND VENDOR='"+vendor+"'";
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()) {
			serials.add(rs.getString("SERIALNUMBER"));
		}
		stmt.close();
		rs.close();
		
		return serials;
	}
	
	private static void CheckNewAntennas(List<String> newSerial,List<String> oldSerial) throws SQLException {
		Statement stmt = null,stmt1=null,stmt2=null;
		String query = null,transID =null;
		int transvalue = 0;
		PreparedStatement insertstatement = null,updatestatement=null;
		
		newSerial.removeAll(oldSerial);
		if(newSerial.size() > 0) {
			stmt = con.createStatement();
			query = "SELECT TRANS_ID FROM SEQ_TABLE";
			ResultSet rs1 = stmt.executeQuery(query);
			while(rs1.next()) {
				transvalue = rs1.getInt("TRANS_ID");
			}
			stmt.close();
			rs1.close();
			System.out.println("New Serials Found, New Antenna");
			for(int i=0;i<newSerial.size();i++) {
				stmt1 = con.createStatement();
				query = "select a.Antenna_id,b.node_id,b.node_name,b.parsing_date from NODE_ANTENNA a,node_active b where  a.serialnumber = '"+newSerial.get(i)+"' and a.active_record='1' and a.NODE_PK=b.NODE_PK";
				ResultSet rs11 = stmt1.executeQuery(query);
				while(rs11.next()) {
					transID=Gyear+"_TRANS_"+transvalue;
					
					insertstatement = con.prepareStatement(
					"INSERT INTO NETWORK_TRANSACTION (TRANS_ID,ELEMENT_ID,ELEMENT,ALM_TRANS_TYPE,DISCOVERED_TRANS_TYPE,PARSING_DATE,CREATION_DATE,LAST_MODIFIED_DATE,APPROVED_BY,MODIFIED_BY,FROM_SITE,TO_SITE,FROM_NODE,TO_NODE,FROM_CIRCLE,TO_CIRCLE) "
					+ "values ('"+transID+"','"+rs11.getString("Antenna_ID")+"','NODE_ANTENNA','0','NEW Antenna',TIMESTAMP '"+rs11.getString("PARSING_DATE")+"',sysdate,sysdate,'0','0','0','0','0','0','0','0')");
					insertstatement.executeUpdate();
					insertstatement.close();
					transvalue++;
					
				}
				stmt1.close();
				rs11.close();
			}
			updatestatement = con.prepareStatement("Update SEQ_TABLE SET TRANS_ID="+transvalue);
			updatestatement.executeUpdate();
			updatestatement.close();
			
		}else {
			System.out.println("No New Serials Found, No new Antennas");
		}
	}
	
	private static void CheckDisappearedAntenna(List<String> newSerial1,List<String> oldSerial1) throws SQLException {
		Statement stmt = null,stmt1=null,stmt2=null;
		String query = null,transID =null;
		int transvalue = 0;
		PreparedStatement insertstatement = null,updatestatement=null;
		
		oldSerial1.removeAll(newSerial1);
		
		if(oldSerial1.size()>0) {
			System.out.println("Disappeared Serials, Disappeared Antennas.");
			stmt = con.createStatement();
			query = "SELECT TRANS_ID FROM SEQ_TABLE";
			ResultSet rs1 = stmt.executeQuery(query);
			while(rs1.next()) {
				transvalue = rs1.getInt("TRANS_ID");
				System.out.println(transvalue);
			}
			stmt.close();
			rs1.close();
			for(int i=0;i<oldSerial1.size();i++) {
				System.out.println(oldSerial1.get(i));
				stmt1 = con.createStatement();
				query = "select a.Antenna_id,b.node_id,b.node_name,b.parsing_date from NODE_ANTENNA a,node_active b where  a.serialnumber = '"+oldSerial1.get(i)+"' and a.active_record='2' and a.NODE_PK=b.NODE_PK";
				ResultSet rs11 = stmt1.executeQuery(query);
				while(rs11.next()) {
					transID=Gyear+"_TRANS_"+transvalue;
					
					insertstatement = con.prepareStatement(
					"INSERT INTO NETWORK_TRANSACTION (TRANS_ID,ELEMENT_ID,ELEMENT,ALM_TRANS_TYPE,DISCOVERED_TRANS_TYPE,PARSING_DATE,CREATION_DATE,LAST_MODIFIED_DATE,APPROVED_BY,MODIFIED_BY,FROM_SITE,TO_SITE,FROM_NODE,TO_NODE,FROM_CIRCLE,TO_CIRCLE) "
					+ "values ('"+transID+"','"+rs11.getString("Antenna_ID")+"','NODE_ANTENNA','0','Antenna DISAPPEARED',TIMESTAMP '"+rs11.getString("PARSING_DATE")+"',sysdate,sysdate,'0','0','0','0','0','0','0','0')");
					insertstatement.executeUpdate();
					insertstatement.close();
					transvalue++;
					
				}
				stmt1.close();
				rs11.close();
			}
			updatestatement = con.prepareStatement("Update SEQ_TABLE SET TRANS_ID="+transvalue);
			updatestatement.executeUpdate();
			updatestatement.close();
			
		}else {
			System.out.println("No Disappeared Serials, No Disappeared Antennas.");
		}
	}
	
	private static void CheckTransferredAntenna(List<String> newSerial1,List<String> oldSerial1) throws SQLException{
		Statement stmt = null,stmt1=null,stmt2=null,stmt3=null;
		String query = null,transID =null,oldNodeID,oldNodeName,oldSiteID,oldCircleID,newNodeID,newNodeName,newSiteID,newCircleID,varstatus = null;
		int transvalue=0;
		PreparedStatement insertstatement = null,updatestatement=null;

		newSerial1.retainAll(oldSerial1);
		if(newSerial1.size() > 0) {
			stmt2 = con.createStatement();
			query = "SELECT TRANS_ID FROM SEQ_TABLE";
			ResultSet rs2 = stmt2.executeQuery(query);
			while(rs2.next()) {
				transvalue = rs2.getInt("TRANS_ID");
				System.out.println(transvalue);
			}
			stmt2.close();
			rs2.close();
			for(int i=0;i<newSerial1.size();i++) {
				
				stmt = con.createStatement();
				query = "SELECT a.NODE_ID,a.NODE_NAME,a.SITE_ID,a.CIRCLE_ID,a.PARSING_DATE,b.SERIALNUMBER FROM NODE_ACTIVE a,NODE_ANTENNA b " + 
						"WHERE  b.ACTIVE_RECORD = '2' and serialnumber = '"+newSerial1.get(i)+"' and a.node_pk=b.node_pk ";
				System.out.println("2 : "+query);
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()) {
					oldNodeID=rs.getString("NODE_ID");
					oldNodeName=rs.getString("NODE_NAME");
					oldSiteID=rs.getString("SITE_ID");
					oldCircleID=rs.getString("CIRCLE_ID");
					
					System.out.println();
					stmt1 = con.createStatement();
					query ="SELECT a.NODE_ID,a.NODE_NAME,a.SITE_ID,a.CIRCLE_ID,a.PARSING_DATE,b.SERIALNUMBER,b.Antenna_ID FROM NODE_ACTIVE a,NODE_ANTENNA b " + 
							"WHERE  b.ACTIVE_RECORD = '1' and serialnumber = '"+newSerial1.get(i)+"' and a.node_pk=b.node_pk ";
					System.out.println("1 : "+query);
					ResultSet rs1 = stmt1.executeQuery(query);
					while(rs1.next()) {
						newNodeID=rs1.getString("NODE_ID");
						newNodeName=rs1.getString("NODE_NAME");
						newSiteID=rs1.getString("SITE_ID");
						newCircleID=rs1.getString("CIRCLE_ID");
						
						
					    	// validate site and circle
					    	if (!StringUtils.equalsIgnoreCase (oldSiteID,newSiteID)) {
				    			System.out.println("Reappear SN  After Site Transfer");
				    			varstatus="Reappear SN  After Site Transfer";	
						    		transID=Gyear+"_TRANS_"+transvalue;
									insertstatement = con.prepareStatement(
											"INSERT INTO NETWORK_TRANSACTION (TRANS_ID,ELEMENT_ID,ELEMENT,ALM_TRANS_TYPE,DISCOVERED_TRANS_TYPE,PARSING_DATE,CREATION_DATE,LAST_MODIFIED_DATE,APPROVED_BY,MODIFIED_BY,FROM_SITE,TO_SITE,FROM_NODE,TO_NODE,FROM_CIRCLE,TO_CIRCLE) "
											+ "values ('"+transID+"','"+rs1.getString("Antenna_ID")+"','NODE_ANTENNA','0','"+varstatus+"',TIMESTAMP '"+rs1.getString("PARSING_DATE")+"',sysdate,sysdate,'0','0','"+oldSiteID+"','"+newSiteID+"','"+oldNodeID+"','"+newNodeID+"','"+oldCircleID+"','"+newCircleID+"')");
											insertstatement.executeUpdate();
											insertstatement.close();
											
									transvalue++;
					    	}
						
					}
					stmt1.close();
					rs1.close();
					
				}
				stmt.close();
				rs.close();
				
			}
			updatestatement = con.prepareStatement("Update SEQ_TABLE SET TRANS_ID="+transvalue);
			updatestatement.executeUpdate();
			updatestatement.close();
		}else {
			
		}
	}
}
