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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NewBoardMovement {
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
	private static ObjectMapper mapper = new ObjectMapper();

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
				String lofilename="BoardMovement-"+dtf.format(now)+".log";
			
			
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
					
					
					//Check if there is a new data entered and needed to be manipulated in the node_board
					Statement chckstmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					String chckquery = "select distinct DOMAIN,VENDOR,STATUS FROM EXECUTE_DOMAIN_VENDOR_ELEMENTS WHERE ELEMENT='NODE_BOARD'"
							+ " ORDER BY CREATION_DATE DESC fetch  first 1 rows only";
			 		ResultSet chckrs = chckstmt.executeQuery(chckquery);
			 		while(chckrs.next()) {
			 			if(chckrs.getString("STATUS").equalsIgnoreCase("IN PROCESS")) {
			 				Domain = chckrs.getString("DOMAIN");
			 				Vendor = chckrs.getString("VENDOR");
			 				
			 				//Get all the serial numbers from the node_BOARD having active record ='1'  and active record='2'
							logger.info("Get all the serial numbers from the node_BOARD having active record ='1'  and active record='2'");
							System.out.println("Get all the serial numbers from the node_BOARD having active record ='1' and active record='2'");
							NewSerials=getSerials(chckrs.getString("DOMAIN"),chckrs.getString("VENDOR"),"1");
							OldSerials=getSerials(chckrs.getString("DOMAIN"),chckrs.getString("VENDOR"),"2");
							tempNewSerials =getSerials(chckrs.getString("DOMAIN"),chckrs.getString("VENDOR"),"1");
							tempOldSerials = getSerials(chckrs.getString("DOMAIN"),chckrs.getString("VENDOR"),"2");
							tempNewSerials1 =getSerials(chckrs.getString("DOMAIN"),chckrs.getString("VENDOR"),"1");
							tempOldSerials1 =getSerials(chckrs.getString("DOMAIN"),chckrs.getString("VENDOR"),"2");
							
							//Checking for New BOARDs
						 	logger.info("Checking if there is a new Board found after the New Parsing.");
							System.out.println("Checking if there is a new Board found after the New Parsing.");
							CheckNewBoards(tempNewSerials,tempOldSerials);
							logger.info("Checking for a new Board after the New Parsing Completed.");
							System.out.println("Checking for a new Board after the New Parsing Completed.");
							
							//Checking for New BOARDs
						 	logger.info("Checking if there is a Disappeared Board found after the New Parsing.");
							System.out.println("Checking if there is a Disappeared Board found after the New Parsing.");
							CheckDisappearedBoard(tempNewSerials1,tempOldSerials1);
							logger.info("Checking for a Disappeared Board after the New Parsing Completed.");
							System.out.println("Checking for a Disappeared Board after the New Parsing Completed.");	
							
							logger.info("Checking if there is a Transferred Board found after the New Parsing.");
							System.out.println("Checking if there is a Transferred Board found after the New Parsing.");
							//Checking for Transferred BOARDs
							CheckTransferredBoard(NewSerials,OldSerials);
							logger.info("Checking for a Transferred Board after the New Parsing Completed.");
							System.out.println("Checking for a Transferred Board after the New Parsing Completed.");	
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
		query = "select DISTINCT SERIALNUMBER FROM NODE_BOARD WHERE ACTIVE_RECORD='"+recordValue+"' AND DOMAIN='"+domain+"' AND VENDOR='"+vendor+"' ORDER BY SERIALNUMBER";
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()) {
			serials.add(rs.getString("SERIALNUMBER"));
		}
		stmt.close();
		rs.close();
		
		return serials;
	}
	
	private static void CheckNewBoards(List<String> newSerial,List<String> oldSerial) throws SQLException{
		Statement stmt = null,stmt1=null;
		String query = null,transID =null,nodeTransID=null;
		int transvalue = 0,nodetransvalue=0;
		PreparedStatement insertstatement = null,updatestatement=null;
		
		newSerial.removeAll(oldSerial);

		if(newSerial.size() > 0) {
			//String str = String.join(",", newSerial);
			String str = newSerial.stream()
	                .map(value -> "'" + value + "'")
	                .reduce((value1, value2) -> value1 + "," + value2)
	                .orElse("");
			
			stmt = con.createStatement();
			query = "select TRANS_ID,NODE_TRANSACTION FROM SEQ_TABLE";
			ResultSet rs1 = stmt.executeQuery(query);
			while(rs1.next()) {
				transvalue = rs1.getInt("TRANS_ID");
				nodetransvalue = rs1.getInt("NODE_TRANSACTION");
			}
			stmt.close();
			rs1.close();
			List<String> nodePkList = new ArrayList<>();
			String prevNode = null;
		
			//for(int i=0;i<newSerial.size();i++) {
				stmt1 = con.createStatement();
				
				query ="select distinct a.node_pk,a.unique_node_id,a.node_name,a.parsing_date,a.site_id,a.ware_id,a.ware_name,a.node_type,a.circle_id,b.serialnumber,b.board_id,b.model " + 
						"FROM NODE_ACTIVE a INNER JOIN NODE_BOARD b ON a.node_pk=b.node_pk WHERE b.serialnumber IN ("+str+") and b.ACTIVE_RECORD = '1' " + 
						"ORDER BY a.NODE_PK DESC";
				System.out.println(query);
				ResultSet rsNode = stmt1.executeQuery(query);
				while(rsNode.next()) {
					prevNode=rsNode.getString("NODE_PK").toString();
					System.out.println("Node_PK = "+prevNode);
					if(nodePkList.size() == 0 || !nodePkList.contains(prevNode)) {
						nodePkList.add(prevNode);
						nodeTransID=Gyear+"_NODE_TRANS_"+nodetransvalue;
						insertstatement = con.prepareStatement(
								"INSERT INTO NODE_TRANSACTIONS (NODE_TRANS_ID ,NODE_PK,FROM_NODE_ID,TO_NODE_ID,FROM_NODE_NAME,TO_NODE_NAME,FROM_NODE_TYPE,TO_NODE_TYPE,PARSING_DATE,CREATION_DATE,LAST_MODIFIED_DATE) "
								+ "values ('"+nodeTransID+"','"+rsNode.getString("NODE_PK")+"','0','"+rsNode.getString("unique_node_id")+"','0','"+rsNode.getString("NODE_NAME")+"','0','"+rsNode.getString("NODE_TYPE")+"',TIMESTAMP '"+rsNode.getString("PARSING_DATE")+"',sysdate,sysdate)");
								insertstatement.executeUpdate();
								insertstatement.close();
						nodetransvalue++;	
					}
					
					transID=Gyear+"_TRANS_"+transvalue;
					insertstatement = con.prepareStatement(
							"INSERT INTO NETWORK_TRANSACTION (TRANS_ID,ELEMENT_ID,ELEMENT,ALM_TRANS_TYPE,DISCOVERED_TRANS_TYPE,PARSING_DATE,CREATION_DATE,LAST_MODIFIED_DATE,APPROVED_BY,MODIFIED_BY,FROM_SITE,TO_SITE,FROM_CIRCLE,TO_CIRCLE,FROM_WARE_ID,TO_WARE_ID,FROM_WARE_NAME,TO_WARE_NAME,OLD_SERIAL_NUMBER,SERIAL_NUMBER,MODEL,NODE_TRANS_ID) "
							+ "values ('"+transID+"','"+rsNode.getString("BOARD_ID")+"','NODE_BOARD','0','NEW BOARD',TIMESTAMP '"+rsNode.getString("PARSING_DATE")+"',sysdate,sysdate,'0','0','0','"+rsNode.getString("SITE_ID")+"','0','"+rsNode.getString("CIRCLE_ID")+"','0','"+rsNode.getString("WARE_ID")+"','0','"+rsNode.getString("WARE_NAME")+"','0','"+rsNode.getString("SERIALNUMBER")+"','"+rsNode.getString("MODEL")+"','"+nodeTransID+"')");
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
			System.out.println("No New Serials Found, No new Boards");
		}
	}
	
	private static void CheckDisappearedBoard(List<String> newSerial1,List<String> oldSerial1) throws SQLException {
		Statement stmt = null,stmt1=null;
		String query = null,transID =null,nodeTransID=null;
		int transvalue = 0,nodetransvalue=0;
		PreparedStatement insertstatement = null,updatestatement=null;
		
		oldSerial1.removeAll(newSerial1);
		
		if(oldSerial1.size()>0) {
			String str = oldSerial1.stream()
	                .map(value -> "'" + value + "'")
	                .reduce((value1, value2) -> value1 + "," + value2)
	                .orElse("");
			
			System.out.println("Disappeared Serials, Disappeared BOARDs.");
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
				stmt1 = con.createStatement();
				query="select distinct a.node_pk,a.unique_node_id,a.node_name,a.parsing_date,a.site_id,a.ware_id,a.ware_name,a.node_type,b.serialnumber,a.circle_id,b.board_id,b.model"
						+ " FROM NODE_ACTIVE a INNER JOIN NODE_BOARD b on a.node_pk=b.node_pk "
						+ " WHERE b.serialnumber IN ("+str+") and b.ACTIVE_RECORD = '2' "
						+ " ORDER BY a.NODE_PK DESC";
				ResultSet rsNode = stmt1.executeQuery(query);
				while(rsNode.next()) {
					transID=Gyear+"_TRANS_"+transvalue;					
					
					prevNode=rsNode.getString("NODE_PK").toString();
					System.out.println("Node_PK = "+prevNode);
					if(nodePkList.size() == 0 || !nodePkList.contains(prevNode)) {
						nodePkList.add(prevNode);
						nodeTransID=Gyear+"_NODE_TRANS_"+nodetransvalue;
						insertstatement = con.prepareStatement(
								"INSERT INTO NODE_TRANSACTIONS (NODE_TRANS_ID ,NODE_PK,TO_NODE_ID,FROM_NODE_ID,TO_NODE_NAME,FROM_NODE_NAME,TO_NODE_TYPE,FROM_NODE_TYPE,PARSING_DATE,CREATION_DATE,LAST_MODIFIED_DATE) "
								+ "values ('"+nodeTransID+"','"+rsNode.getString("NODE_PK")+"','0','"+rsNode.getString("unique_node_id")+"','0','"+rsNode.getString("NODE_NAME")+"','0','"+rsNode.getString("NODE_TYPE")+"',TIMESTAMP '"+rsNode.getString("PARSING_DATE")+"',sysdate,sysdate)");
								insertstatement.executeUpdate();
								insertstatement.close();
						nodetransvalue++;	
					}
					
					insertstatement = con.prepareStatement(
							"INSERT INTO NETWORK_TRANSACTION (TRANS_ID,ELEMENT_ID,ELEMENT,ALM_TRANS_TYPE,DISCOVERED_TRANS_TYPE,PARSING_DATE,CREATION_DATE,LAST_MODIFIED_DATE,APPROVED_BY,MODIFIED_BY,TO_SITE,FROM_SITE,TO_CIRCLE,FROM_CIRCLE,TO_WARE_ID,FROM_WARE_ID,TO_WARE_NAME,FROM_WARE_NAME,SERIAL_NUMBER,OLD_SERIAL_NUMBER,MODEL,NODE_TRANS_ID) "
							+ "values ('"+transID+"','"+rsNode.getString("BOARD_ID")+"','NODE_BOARD','0','DISAPPEARED BOARD',TIMESTAMP '"+rsNode.getString("PARSING_DATE")+"',sysdate,sysdate,'0','0','0','"+rsNode.getString("SITE_ID")+"','0','"+rsNode.getString("CIRCLE_ID")+"','0','"+rsNode.getString("WARE_ID")+"','0','"+rsNode.getString("WARE_NAME")+"','"+rsNode.getString("SERIALNUMBER")+"','0','"+rsNode.getString("MODEL")+"','"+nodeTransID+"')");
							insertstatement.executeUpdate();
							insertstatement.close();
					transvalue++;
					
					
					
				}
				stmt1.close();
				rsNode.close();
			
			updatestatement = con.prepareStatement("Update SEQ_TABLE SET TRANS_ID="+transvalue+", NODE_TRANSACTION="+nodetransvalue);
			updatestatement.executeUpdate();
			updatestatement.close();
			
		}else {
			System.out.println("No Disappeared Serials, No Disappeared BOARDs.");
		}
	}
	
	private static void CheckTransferredBoard(List<String> newSerial1,List<String> oldSerial1) throws SQLException, JsonProcessingException{
		Statement stmt = null,stmt1=null,stmt2=null;
		String query = null,transID =null,oldNodeID,oldNodeName,oldSiteID,oldCircleID,oldnodeType,oldwareId,oldWareName,newNodeID,newNodeName,newSiteID,newCircleID,newSN,newnodeType,newwareID,newWareName,varstatus = null;
		String nodeTransID=null;
		int transvalue = 0,nodetransvalue=0;
		PreparedStatement insertstatement = null,updatestatement=null;

		newSerial1.retainAll(oldSerial1);
		
		if(newSerial1.size() > 0) {

			List<String> nodePkList = new ArrayList<>();
			List<String> boardIDList = new ArrayList<>();
			String prevNode = null,prevBoard=null;
			stmt2 = con.createStatement();
			query = "select TRANS_ID,NODE_TRANSACTION FROM SEQ_TABLE";
			ResultSet rs2 = stmt2.executeQuery(query);
			while(rs2.next()) {
				transvalue = rs2.getInt("TRANS_ID");
				nodetransvalue = rs2.getInt("NODE_TRANSACTION");
				
			}
			stmt2.close();
			rs2.close();
			for(int i=0;i<newSerial1.size();i++) {
				
				stmt = con.createStatement();
				query = "select distinct a.node_pk,a.unique_node_id,a.node_name,a.parsing_date,a.site_id,a.ware_id,a.ware_name,a.node_type,b.serialnumber,a.circle_id,b.board_id"
						+ " FROM NODE_ACTIVE a,NODE_BOARD b WHERE b.serialnumber = '"+newSerial1.get(i)+"' and b.ACTIVE_RECORD = '2' and a.node_pk=b.node_pk "
						+ " ORDER BY a.NODE_PK DESC";
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
					query = "select distinct a.node_pk,a.unique_node_id,a.node_name,a.parsing_date,a.site_id,a.ware_id,a.ware_name,a.node_type,b.serialnumber,a.circle_id,b.board_id,b.model"
							+ " FROM NODE_ACTIVE a,NODE_BOARD b WHERE b.serialnumber = '"+newSerial1.get(i)+"' and b.ACTIVE_RECORD = '1' and a.node_pk=b.node_pk "
							+ " ORDER BY a.NODE_PK DESC";

					ResultSet rs1 = stmt1.executeQuery(query);
					while(rs1.next()) {
						newNodeID=rs1.getString("unique_node_id");
						newNodeName=rs1.getString("NODE_NAME");
						newSiteID=rs1.getString("SITE_ID");
						newCircleID=rs1.getString("CIRCLE_ID");
						newwareID=rs1.getString("WARE_ID");
						newWareName=rs1.getString("WARE_NAME");
						newSN=rs1.getString("SERIALNUMBER");
						newnodeType=rs1.getString("NODE_TYPE");
						
						
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
					    		System.out.println("Reappear SN  After Site Transfer");
				    			varstatus="Reappear SN  After Site Transfer";
				    			prevBoard = rs1.getString("board_id");
								if(boardIDList.size() == 0 || !boardIDList.contains(prevBoard)) {
									boardIDList.add(prevBoard);
				    			transID=Gyear+"_TRANS_"+transvalue;
								insertstatement = con.prepareStatement(
				    					"INSERT INTO NETWORK_TRANSACTION (TRANS_ID,ELEMENT_ID,ELEMENT,ALM_TRANS_TYPE,DISCOVERED_TRANS_TYPE,PARSING_DATE,CREATION_DATE,LAST_MODIFIED_DATE,APPROVED_BY,MODIFIED_BY,FROM_SITE,TO_SITE,FROM_CIRCLE,TO_CIRCLE,FROM_WARE_ID,TO_WARE_ID,FROM_WARE_NAME,TO_WARE_NAME,OLD_SERIAL_NUMBER,SERIAL_NUMBER,MODEL,NODE_TRANS_ID) "
				    					+ "values ('"+transID+"','"+rs1.getString("BOARD_ID")+"','NODE_BOARD','0','"+varstatus+"',TIMESTAMP '"+rs1.getString("PARSING_DATE")+"',sysdate,sysdate,'0','0','"+oldSiteID+"','"+newSiteID+"','"+oldCircleID+"','"+newCircleID+"','"
				    							+oldwareId+"','"+newwareID+"','"+oldWareName+"','"+newWareName+"','0','"+newSN+"','"+rs1.getString("MODEL")+"','"+nodeTransID+"')");
				    			insertstatement.executeUpdate();
				    			insertstatement.close();
										
								transvalue++;
								}
								
							
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
