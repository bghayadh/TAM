package com.aliat.alm.Parser;

import java.io.BufferedReader;
import java.io.File;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class PhysicalNodeMovement_MAC {

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
	static String Domain=null,Vendor=null;
	static List<String> OldNodeMAC = new ArrayList<String>();
	static List<String> NewNodeMAC = new ArrayList<String>();
	public static void main(String[] args) {
		List<String> tempOldNodeMAC = new ArrayList<String>();
		List<String> tempNewNodeMAC = new ArrayList<String>();
		List<String> tempOldNodeMAC1 = new ArrayList<String>();
		List<String> tempNewNodeMAC1 = new ArrayList<String>();
		
		
		
		try {
			//objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/"+"almconfig.dat"));
			Resource ConfigResource = new ClassPathResource("almconfig.dat");
			File configfile = ConfigResource.getFile();
			FileReader fr = new FileReader(configfile);
			BufferedReader objReader1 = new BufferedReader(fr);
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
				String lofilename="NodeMovement-"+dtf.format(now)+".log";
			
			
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
					String chckquery = "select DOMAIN,VENDOR,STATUS FROM EXECUTE_DOMAIN_VENDOR_ELEMENTS WHERE ELEMENT='NODE_ACTIVE'"
							+ " ORDER BY CREATION_DATE DESC fetch  first 1 rows only";
			 		ResultSet chckrs = chckstmt.executeQuery(chckquery);
			 		while(chckrs.next()) {
			 			if(chckrs.getString("STATUS").equalsIgnoreCase("IN PROCESS")) {
			 				Domain = chckrs.getString("DOMAIN");
			 				Vendor = chckrs.getString("VENDOR");
			 				
			 				//Get all the serial numbers from the NODE_ANTENNA having active record ='1'  and active record='2'
							logger.info("Get all the serial numbers from the NODE_ACTIVE having active record ='1'  and active record='2'");
							System.out.println("Get all the serial numbers from the NODE_ACTIVE having active record ='1' and active record='2'");
							NewNodeMAC=getNodeMAC(chckrs.getString("DOMAIN"),chckrs.getString("VENDOR"),"1");
							OldNodeMAC=getNodeMAC(chckrs.getString("DOMAIN"),chckrs.getString("VENDOR"),"2");
							tempNewNodeMAC =getNodeMAC(chckrs.getString("DOMAIN"),chckrs.getString("VENDOR"),"1");
							tempOldNodeMAC = getNodeMAC(chckrs.getString("DOMAIN"),chckrs.getString("VENDOR"),"2");
							tempNewNodeMAC1 =getNodeMAC(chckrs.getString("DOMAIN"),chckrs.getString("VENDOR"),"1");
							tempOldNodeMAC1 =getNodeMAC(chckrs.getString("DOMAIN"),chckrs.getString("VENDOR"),"2");
							
							//Checking for New Antennas
						 	logger.info("Checking if there is a new Nodes found after the New Parsing.");
							System.out.println("Checking if there is a new Nodes found after the New Parsing.");
							CheckNewNodes(tempNewNodeMAC,tempOldNodeMAC);
							logger.info("Checking for a new Nodes after the New Parsing Completed.");
							System.out.println("Checking for a new Nodes after the New Parsing Completed.");
							
							//Checking for New Antennas
						 	logger.info("Checking if there is a Disappeared Nodes found after the New Parsing.");
							System.out.println("Checking if there is a Disappeared Nodes found after the New Parsing.");
							CheckDisappearedNodes(tempNewNodeMAC1,tempOldNodeMAC1);
							logger.info("Checking for a Disappeared Nodes after the New Parsing Completed.");
							System.out.println("Checking for a Disappeared Nodes after the New Parsing Completed.");	
							
							logger.info("Checking if there is a Transferred Nodes found after the New Parsing.");
							System.out.println("Checking if there is a Transferred Nodes found after the New Parsing.");
							//Checking for Transferred Antennas
							CheckTransferredNodes(NewNodeMAC,OldNodeMAC);
							logger.info("Checking for a Transferred Nodes after the New Parsing Completed.");
							System.out.println("Checking for a Transferred Nodes after the New Parsing Completed.");	
			 			}
			 		}
					
					
				 	con.close();
		}catch(Exception e){
		      System.err.println(e);
		      e.printStackTrace();
		      logger.info("Error : "+e);
		   }
	}
	
	private static List<String> getNodeMAC(String domain,String vendor,String recordValue) throws SQLException{
		Statement stmt = null;
		String query=null;
		List<String> nodemacs = new ArrayList<String>();
		stmt=con.createStatement();
		query = "select DISTINCT MAC_ADDRESS FROM NODE_ACTIVE WHERE ACTIVE_RECORD='"+recordValue+"' AND DOMAIN='"+domain+"' AND VENDOR='"+vendor+"' ORDER BY MAC_ADDRESS";
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()) {
			if(rs.getString("MAC_ADDRESS") != null && !rs.getString("MAC_ADDRESS").equalsIgnoreCase("null")) {
				nodemacs.add(rs.getString("MAC_ADDRESS"));
			}
		}
		stmt.close();
		rs.close();
		
		return nodemacs;
	}
	
	private static void CheckNewNodes(List<String> newNodes,List<String> oldNodes) throws SQLException {
		Statement stmt = null,stmt1=null;
		String query = null,transID =null,nodeTransID=null;
		int transvalue = 0,nodetransvalue=0;
		PreparedStatement insertstatement = null,updatestatement=null;
		
		newNodes.removeAll(oldNodes);
		if(newNodes.size() > 0) {
			String str = newNodes.stream()
	                .map(value -> "'" + value + "'")
	                .reduce((value1, value2) -> value1 + "," + value2)
	                .orElse("");
			System.out.println(str);
			
			stmt = con.createStatement();
			query = "select TRANS_ID,NODE_TRANSACTION FROM SEQ_TABLE";
			ResultSet rs1 = stmt.executeQuery(query);
			while(rs1.next()) {
				transvalue = rs1.getInt("TRANS_ID");
				nodetransvalue = rs1.getInt("NODE_TRANSACTION");
				
				System.out.println(transvalue);
				System.out.println(nodetransvalue);
			}
			stmt.close();
			rs1.close();
			List<String> nodePkList = new ArrayList<>();
			String prevNode = null;
			System.out.println("New Nodes Found");
			//for(int i=0;i<newNodes.size();i++) {
				stmt1 = con.createStatement();
				query = "select distinct node_pk,unique_node_id,node_name,parsing_date,mac_address,site_id,ware_id,ware_name,node_type,serial_number,circle_id,node_model "
						+ " from NODE_ACTIVE  "
						+ " where active_record='1' and mac_address IN ("+str+") "
								+ "ORDER BY NODE_PK DESC";
				ResultSet rsNode = stmt1.executeQuery(query);
				while(rsNode.next()) {
					prevNode=rsNode.getString("NODE_PK").toString();
					System.out.println("Node_PK = "+prevNode);
					if(nodePkList.size() == 0 || !nodePkList.contains(prevNode)) {
						nodePkList.add(prevNode);
					nodeTransID=Gyear+"_NODE_TRANS_"+nodetransvalue;
					insertstatement = con.prepareStatement(
							"INSERT INTO NODE_TRANSACTIONS (NODE_TRANS_ID,NODE_PK,FROM_NODE_ID,TO_NODE_ID,FROM_NODE_NAME,TO_NODE_NAME,FROM_NODE_TYPE,TO_NODE_TYPE,PARSING_DATE,CREATION_DATE,LAST_MODIFIED_DATE) "
							+ "values ('"+nodeTransID+"','"+rsNode.getString("NODE_PK")+"','0','"+rsNode.getString("unique_node_id")+"','0','"+rsNode.getString("NODE_NAME")+"','0','"+rsNode.getString("NODE_TYPE")+"',TIMESTAMP '"+rsNode.getString("PARSING_DATE")+"',sysdate,sysdate)");
							insertstatement.executeUpdate();
							insertstatement.close();
					nodetransvalue++;
					}
					transID=Gyear+"_TRANS_"+transvalue;
					
					insertstatement = con.prepareStatement(
							"INSERT INTO NETWORK_TRANSACTION (TRANS_ID,ELEMENT_ID,ELEMENT,ALM_TRANS_TYPE,DISCOVERED_TRANS_TYPE,PARSING_DATE,CREATION_DATE,LAST_MODIFIED_DATE,APPROVED_BY,MODIFIED_BY,FROM_SITE,TO_SITE,FROM_CIRCLE,TO_CIRCLE,FROM_WARE_ID,TO_WARE_ID,FROM_WARE_NAME,TO_WARE_NAME,OLD_SERIAL_NUMBER,SERIAL_NUMBER,MODEL,OLD_MAC,MAC_ADDRESS,NODE_TRANS_ID) "
							+ "values ('"+transID+"','"+rsNode.getString("NODE_PK")+"','NODE_ACTIVE','0','NEW NODE',TIMESTAMP '"+rsNode.getString("PARSING_DATE")+"',sysdate,sysdate,'0','0','0','"+rsNode.getString("SITE_ID")+"','0','"+rsNode.getString("CIRCLE_ID")+"','0','"+rsNode.getString("WARE_ID")+"','0','"+rsNode.getString("WARE_NAME")+"','0','"+rsNode.getString("SERIAL_NUMBER")+"','"+rsNode.getString("NODE_MODEL")+"','0','"+rsNode.getString("MAC_ADDRESS")+"','"+nodeTransID+"'))");
							insertstatement.executeUpdate();
							insertstatement.close();
					transvalue++;					
				}
				stmt1.close();
				rsNode.close();
			//}
			updatestatement = con.prepareStatement("Update SEQ_TABLE SET TRANS_ID="+transvalue+", NODE_TRANSACTION="+nodetransvalue);
			updatestatement.executeUpdate();
			updatestatement.close();
			
		}else {
			System.out.println("No New Nodes Found");
		}
	}
	
	private static void CheckDisappearedNodes(List<String> newNodes,List<String> oldNodes) throws SQLException {
		Statement stmt = null,stmt1=null;
		String query = null,transID =null,nodeTransID=null;
		int transvalue = 0,nodetransvalue=0;
		PreparedStatement insertstatement = null,updatestatement=null;
		
		oldNodes.removeAll(newNodes);
		
		if(oldNodes.size()>0) {
			String str = oldNodes.stream()
	                .map(value -> "'" + value + "'")
	                .reduce((value1, value2) -> value1 + "," + value2)
	                .orElse("");
			System.out.println(str);
			System.out.println("Disappeared Nodes.");
			stmt = con.createStatement();
			query = "select TRANS_ID,NODE_TRANSACTION FROM SEQ_TABLE";
			ResultSet rs1 = stmt.executeQuery(query);
			while(rs1.next()) {
				System.out.println("you are here ");
				transvalue = rs1.getInt("TRANS_ID");
				nodetransvalue = rs1.getInt("NODE_TRANSACTION");
				
				System.out.println(transvalue);
				System.out.println(nodetransvalue);
			}
			stmt.close();
			rs1.close();
			List<String> nodePkList = new ArrayList<>();
			String prevNode = null;
			for(int i=0;i<oldNodes.size();i++) {
				stmt1 = con.createStatement();
				query = "select distinct node_pk,unique_node_id,node_name,parsing_date,mac_address,site_id,ware_id,ware_name,node_type,serial_number,circle_id,node_model "
						+ " from NODE_ACTIVE  where active_record='2' and mac_address IN ("+str+") "
						+ " ORDER BY NODE_PK";
				ResultSet rsNode = stmt1.executeQuery(query);
				while(rsNode.next()) {
					
					prevNode=rsNode.getString("NODE_PK").toString();
					System.out.println("Node_PK = "+prevNode);
					if(nodePkList.size() == 0 || !nodePkList.contains(prevNode)) {
						nodePkList.add(prevNode);
						nodeTransID=Gyear+"_NODE_TRANS_"+nodetransvalue;
						insertstatement = con.prepareStatement(
								"INSERT INTO NODE_TRANSACTIONS (NODE_TRANS_ID,NODE_PK,TO_NODE_ID,FROM_NODE_ID,TO_NODE_NAME,FROM_NODE_NAME,TO_NODE_TYPE,FROM_NODE_TYPE,PARSING_DATE,CREATION_DATE,LAST_MODIFIED_DATE) "
								+ "values ('"+nodeTransID+"','"+rsNode.getString("NODE_PK")+"','0','"+rsNode.getString("unique_node_id")+"','0','"+rsNode.getString("NODE_NAME")+"','0','"+rsNode.getString("NODE_TYPE")+"',TIMESTAMP '"+rsNode.getString("PARSING_DATE")+"',sysdate,sysdate)");
								insertstatement.executeUpdate();
								insertstatement.close();
						nodetransvalue++;
					}
					
					transID=Gyear+"_TRANS_"+transvalue;
					
					insertstatement = con.prepareStatement(
							"INSERT INTO NETWORK_TRANSACTION (TRANS_ID,ELEMENT_ID,ELEMENT,ALM_TRANS_TYPE,DISCOVERED_TRANS_TYPE,PARSING_DATE,CREATION_DATE,LAST_MODIFIED_DATE,APPROVED_BY,MODIFIED_BY,TO_SITE,FROM_SITE,TO_CIRCLE,FROM_CIRCLE,TO_WARE_ID,FROM_WARE_ID,TO_WARE_NAME,FROM_WARE_NAME,SERIAL_NUMBER,OLD_SERIAL_NUMBER,MODEL,OLD_MAC,MAC_ADDRESS,NODE_TRANS_ID) "
							+ "values ('"+transID+"','"+rsNode.getString("NODE_PK")+"','NODE_ACTIVE','0','DISAPPEARED NODE',TIMESTAMP '"+rsNode.getString("PARSING_DATE")+"',sysdate,sysdate,'0','0','0','"+rsNode.getString("SITE_ID")+"','0','"+rsNode.getString("CIRCLE_ID")+"','0','"+rsNode.getString("WARE_ID")+"','0','"+rsNode.getString("WARE_NAME")+"','0','"+rsNode.getString("SERIAL_NUMBER")+"','"+rsNode.getString("NODE_MODEL")+"','"+rsNode.getString("MAC_ADDRESS")+"','0','"+nodeTransID+"')");
							insertstatement.executeUpdate();
							insertstatement.close();
					transvalue++;
					
					
					
				}
				stmt1.close();
				rsNode.close();
			}
			updatestatement = con.prepareStatement("Update SEQ_TABLE SET TRANS_ID="+transvalue+", NODE_TRANSACTION="+nodetransvalue);
			updatestatement.executeUpdate();
			updatestatement.close();
			
		}else {
			System.out.println("No Disappeared Nodes.");
		}
	}
	
	private static void CheckTransferredNodes(List<String> newNodes,List<String> oldNodes) throws SQLException{
		Statement stmt = null,stmt1=null,stmt2=null;
		String query = null,transID =null,oldNodeID,oldNodeName,oldSiteID,oldCircleID,oldnodeType,oldwareId,oldWareName,newNodeID,newNodeName,newSiteID,newCircleID,newSN,newnodeType,newwareID,newWareName,newMac,model,varstatus = null;
		String nodeTransID=null;
		int transvalue = 0,nodetransvalue=0;
		PreparedStatement insertstatement = null,updatestatement=null;

		newNodes.retainAll(oldNodes);
		if(newNodes.size() > 0) {
			stmt2 = con.createStatement();
			
			query = "select TRANS_ID,NODE_TRANSACTION FROM SEQ_TABLE";
			ResultSet rs2 = stmt2.executeQuery(query);
			while(rs2.next()) {
				transvalue = rs2.getInt("TRANS_ID");
				nodetransvalue = rs2.getInt("NODE_TRANSACTION");
				
			}
			stmt2.close();
			rs2.close();
			List<String> nodePkList = new ArrayList<>();
			String prevNode = null;
			for(int i=0;i<newNodes.size();i++) {
				
				stmt = con.createStatement();
				query = "select distinct node_pk,unique_node_id,node_name,parsing_date,mac_address,site_id,ware_id,ware_name,node_type,serial_number,circle_id "
						+ " FROM NODE_ACTIVE WHERE ACTIVE_RECORD = '2' and MAC_ADDRESS='"+newNodes.get(i)+"' "
						+ " ORDER BY NODE_PK";
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()) {
					oldNodeID=rs.getString("unique_node_id");
					oldNodeName=rs.getString("NODE_NAME");
					oldSiteID=rs.getString("SITE_ID");
					oldCircleID=rs.getString("CIRCLE_ID");
					oldwareId=rs.getString("WARE_ID");
					oldWareName=rs.getString("WARE_NAME");
					oldnodeType=rs.getString("NODE_TYPE");
					
					stmt1 = con.createStatement();
					query ="select distinct node_pk,unique_node_id,node_name,parsing_date,mac_address,site_id,ware_id,ware_name,node_type,serial_number,circle_id,node_model "
							+ "FROM NODE_ACTIVE WHERE ACTIVE_RECORD = '1' and MAC_ADDRESS='"+newNodes.get(i)+"' "
							+ " ORDER BY NODE_PK";
					ResultSet rs1 = stmt1.executeQuery(query);
					while(rs1.next()) {
						newNodeID=rs1.getString("unique_node_id");
						newNodeName=rs1.getString("NODE_NAME");
						newSiteID=rs1.getString("SITE_ID");
						newCircleID=rs1.getString("CIRCLE_ID");
						newwareID=rs1.getString("WARE_ID");
						newWareName=rs1.getString("WARE_NAME");
						newSN=rs1.getString("SERIAL_NUMBER");
						newnodeType=rs1.getString("NODE_TYPE");
						newMac=rs1.getString("MAC_ADDRESS");
						model=rs1.getString("NODE_MODEL");
					    	// validate site and circle
					    	if (!StringUtils.equalsIgnoreCase (oldSiteID,newSiteID)) {
					    		prevNode=rs1.getString("NODE_PK").toString();
								System.out.println("Node_PK = "+prevNode);
								if(nodePkList.size() == 0 || !nodePkList.contains(prevNode)) {
									nodePkList.add(prevNode);

									nodeTransID=Gyear+"_NODE_TRANS_"+nodetransvalue;
									insertstatement = con.prepareStatement(
											"INSERT INTO NODE_TRANSACTIONS (NODE_TRANS_ID ,NODE_PK,TO_NODE_ID,FROM_NODE_ID,TO_NODE_NAME,FROM_NODE_NAME,TO_NODE_TYPE,FROM_NODE_TYPE,PARSING_DATE,CREATION_DATE,LAST_MODIFIED_DATE) "
											+ "values ('"+nodeTransID+"','"+rs1.getString("NODE_PK")+"','"+newNodeID+"','"+oldNodeID+"','"+newNodeName+"','"+oldNodeName+"','"+newnodeType+"','"+oldnodeType+"',TIMESTAMP '"+rs1.getString("PARSING_DATE")+"',sysdate,sysdate)");
											insertstatement.executeUpdate();
											insertstatement.close();
									nodetransvalue++;
									
								}
					    		System.out.println("Reappear After Site Transfer");
				    			varstatus="Reappear After Site Transfer";
				    			transID=Gyear+"_TRANS_"+transvalue;
				 						
								insertstatement = con.prepareStatement(
				    					"INSERT INTO NETWORK_TRANSACTION (TRANS_ID,ELEMENT_ID,ELEMENT,ALM_TRANS_TYPE,DISCOVERED_TRANS_TYPE,PARSING_DATE,CREATION_DATE,LAST_MODIFIED_DATE,APPROVED_BY,MODIFIED_BY,FROM_SITE,TO_SITE,FROM_CIRCLE,TO_CIRCLE,FROM_WARE_ID,TO_WARE_ID,FROM_WARE_NAME,TO_WARE_NAME,OLD_SERIAL_NUMBER,SERIAL_NUMBER,MODEL,OLD_MAC,MAC_ADDRESS,NODE_TRANS_ID) "
				    					+ "values ('"+transID+"','"+rs1.getString("NODE_PK")+"','NODE_ACTIVE','0','"+varstatus+"',TIMESTAMP '"+rs1.getString("PARSING_DATE")+"',sysdate,sysdate,'0','0','"+oldSiteID+"','"+newSiteID+"','"+oldCircleID+"','"+newCircleID+"','"
				    							+oldwareId+"','"+newwareID+"','"+oldWareName+"','"+newWareName+"','0','"+newSN+"','"+rs1.getString("NODE_MODEL")+"','0','"+newMac+"','"+nodeTransID+"')");
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
			updatestatement = con.prepareStatement("Update SEQ_TABLE SET TRANS_ID="+transvalue+", NODE_TRANSACTION="+nodetransvalue);
			updatestatement.executeUpdate();
			updatestatement.close();
		}
	}
}
