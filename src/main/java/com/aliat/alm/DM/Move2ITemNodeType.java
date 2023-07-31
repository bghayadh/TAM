package com.aliat.alm.DM;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;

public class Move2ITemNodeType {
	
	static Connection con;
	
	public static void main(String[] args) {

        // update Item Manufacturer and Node Type
	   String str = null;
       String pass=null;
       String strpwd = null;
       String struser = null;
	
        try{

        	pass=System.getProperty("user.dir")+"\\target\\classes\\hibernate.cfg.xml";
		    File file = new File(pass);
		  FileInputStream fis = null;
		  BufferedInputStream bis = null;
		  DataInputStream dis = null;
		     
		  fis = new FileInputStream(file);
		
		bis = new BufferedInputStream(fis);
		dis = new DataInputStream(bis);
		String sVerifyText="jdbc:oracle:thin:";
		String spasswrd="hibernate.connection.password";
		String susename="hibernate.connection.username";
		while (dis.available() != 0) {
		//System.out.println(dis.readLine());
		String data = dis.readLine();

              if (data.contains(sVerifyText)) {	
                  int index = data.indexOf('>');
                  str=data.substring(index+1, data.length());
                  index = str.indexOf('<');
                  str=str.substring(0, index);
                  // System.out.println(str);  
              }
              if (data.contains(spasswrd)) {	
                  int index = data.indexOf('>');
                  strpwd=data.substring(index+1, data.length());
                  index = strpwd.indexOf('<');
                  strpwd=strpwd.substring(0, index);
                  //System.out.println(strpwd);  
              }
              if (data.contains(susename)) {	
                  int index = data.indexOf('>');
                  struser=data.substring(index+1, data.length());
                  index = struser.indexOf('<');
                  struser=struser.substring(0, index);
                  //System.out.println(struser);  
              }          

		}
        	
		    	DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
		         //Connection con = DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:alm","alm","alm");
				con = DriverManager.getConnection (str,struser,strpwd);
	         
	         Statement stmt2 = null;
	         Statement stmttype = null;
	         Statement stmtdetail = null;    
	            
	             String query1 = "select * from DM_NODE_ACTIVE";  
	             stmt2 = con.createStatement();
	             ResultSet rs = stmt2.executeQuery(query1);
	             
	             Date date = new Date();
	     		 Calendar calendar = new GregorianCalendar();
	     		 calendar.setTime(date);
	     		 int year = calendar.get(Calendar.YEAR);
      
			while (rs.next()) {
				    /*System.out.println("Move2ITemNodeType  " + rs.getString("ITEM_MODEL") +":"+ rs.getString("ITEM_CATEGORY"));
				    
				    String query3 = "select distinct item_code from item where ITEM_MODEL ='" + rs.getString("ITEM_MODEL") + "' and ITEM_CAT_CODE ='" + rs.getString("ITEM_CATEGORY") + "' ";  
				    stmtdetail = con.createStatement();
		            ResultSet rs3 = stmtdetail.executeQuery(query3);
		            while (rs3.next()) {
		            	PreparedStatement stmt = con.prepareStatement("insert into ITEM_NODE_TYPE values('" + rs3.getString("ITEM_CODE") +"','" + rs.getString("NODE_TYPE") + "','" + rs.getString("ITEM_MODEL") +"','" + rs.getString("ITEM_CATEGORY") +"')");
					    System.out.println("insert into ITEM_NODE_TYPE values('" + rs3.getString("ITEM_CODE") +"','" + rs.getString("NODE_TYPE") + "','" + rs.getString("ITEM_MODEL") +"','" + rs.getString("ITEM_CATEGORY") +"')");
						stmt.executeUpdate();
				        stmt.close();
		            }
		            rs3.close();
		            stmtdetail.close();*/
				//////////////////////////////// Liliane /////////////////////////
				DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        	LocalDateTime now1 = LocalDateTime.now();
	        	String Gyear=dtf1.format(now1).substring(0,4);
	        	
				String codeid= localgetseqNbr(0);
				codeid=Gyear+"_"+ "NODE"+'_'+codeid;
				System.out.println("Begin Node ID: "+codeid);
				
				String query6 = "select SITE_ID, WARE_ID FROM WAREHOUSE where WARE_NAME='"+rs.getString("WARE_NAME")+"'";  
				System.out.println("select SITE_ID, WARE_ID FROM WAREHOUSE where WARE_NAME='"+rs.getString("WARE_NAME")+"'");
		        Statement stmtn = con.createStatement();
		        ResultSet rs_site = stmtn.executeQuery(query6);
		        String WARE_ID = null;
		        while (rs_site.next()) {
		        	
		        	WARE_ID = rs_site.getString("WARE_ID");
		        }
		        stmtn.setMaxRows(1);
		        rs_site.close();
		        stmtn.close();
		        
	        	String supplierID = null;
	        	if(!StringUtils.equalsIgnoreCase(rs.getString("SUPPLIER_NAME"),"null"))
	        	{
		        	String getSupplierDetails = "select SUPPLIER_ID FROM SUPPLIER where SUPPLIER_NAME='"+rs.getString("SUPPLIER_NAME")+"'";  
		        	System.out.println("select SUPPLIER_ID FROM SUPPLIER where SUPPLIER_NAME='"+rs.getString("SUPPLIER_NAME")+"'");
			        Statement stmnt_supplier = con.createStatement();
			        ResultSet getSupplierRes = stmnt_supplier.executeQuery(getSupplierDetails);
			        while (getSupplierRes.next()) {
			        	supplierID = getSupplierRes.getString("SUPPLIER_ID");
			        }
			        stmnt_supplier.setMaxRows(1);
			        getSupplierRes.close();
			        stmnt_supplier.close();
	        	}
			        
			        PreparedStatement stmt = con.prepareStatement("insert into NODE_ACTIVE (NODE_PK,NODE_ID,NODE_NAME,NODE_TYPE,SITE_ID,CREATION_DATE,UPDATE_DATE,WARE_ID,SUPPLIER_ID,WARE_NAME,SUPPLIER_NAME,TRANS_TYPE,ACTIVE_RECORD)"
					 		+ "values('" + codeid +"', '"+ rs.getString("NODE_ID") +"', '" + rs.getString("NODE_NAME") +"' ,'"+rs.getString("NODE_TYPE")+"', '"+rs.getString("SITE_ID")+"', sysdate ,sysdate,'" + WARE_ID +"','" + supplierID +"','" +  rs.getString("WARE_NAME") +"', '"+rs.getString("SUPPLIER_NAME")+"', 'Data Migration','1')");
		        	System.out.println("insert into NODE_ACTIVE (NODE_PK,NODE_ID,NODE_NAME,NODE_TYPE,SITE_ID,CREATION_DATE,UPDATE_DATE,WARE_ID,SUPPLIER_ID,WARE_NAME,SUPPLIER_NAME,TRANS_TYPE,ACTIVE_RECORD) values "
		        			+"('" + codeid +"', '"+ rs.getString("NODE_ID") +"', '" + rs.getString("NODE_NAME") +"' ,'"+rs.getString("NODE_TYPE")+"','"+rs.getString("SITE_ID")+"' , sysdate ,sysdate,'" + WARE_ID +"','" + supplierID 
		        			+"','" +  rs.getString("WARE_NAME") +"', '"+rs.getString("SUPPLIER_NAME")+"', 'Data Migration','1')");
		        	stmt.executeUpdate();
		        	stmt.close();
		        	
		        	String getCabinets = "select * FROM DM_NODE_CABINET where NODEID='"+rs.getString("NODE_ID")+"'";  
		        	System.out.println("select * FROM DM_NODE_CABINET where NODEID='"+rs.getString("NODE_ID")+"'");
			        Statement stmnt_cabinet = con.createStatement();
			        ResultSet getCabinetRes = stmnt_cabinet.executeQuery(getCabinets);
			        while (getCabinetRes.next()) {
			        	
			        	String cabinetID= localgetseqNbr(7);
			        	cabinetID=Gyear+"_"+ "CABINET"+'_'+cabinetID;
						System.out.println("CABINET ID: "+cabinetID);
						
						System.out.println("insert into NODE_CABINET (CABINET_ID,CABINETNO,SERIALNUMBER,NODE_PK,CREATION_DATE,UPDATE_DATE,TRANS_TYPE,ACTIVE_RECORD) values "
			        			+"('"  + cabinetID +"', '"+getCabinetRes.getString("CABINETNO")+"', '"+ getCabinetRes.getString("SERIALNUMBER") +"', '" + codeid +"' , sysdate ,sysdate, 'Data Migration','1')");
			        	PreparedStatement stmt1 = con.prepareStatement("insert into NODE_CABINET (CABINET_ID,CABINETNO,SERIALNUMBER,NODE_PK,CREATION_DATE,UPDATE_DATE,TRANS_TYPE,ACTIVE_RECORD)"
						 		+ "values('" + cabinetID +"', '"+getCabinetRes.getString("CABINETNO")+"', '"+ getCabinetRes.getString("SERIALNUMBER") +"', '" + codeid +"' , sysdate ,sysdate, 'Data Migration','1')");
			        	
			        	stmt1.executeUpdate();
			        	stmt1.close();
			        }
			        getCabinetRes.close();
			        stmnt_cabinet.close();
			        
			        ////////////////// Shelf/Subrack
			        String getSubracks = "select * FROM DM_NODE_SUBRACK where NODEID='"+rs.getString("NODE_ID")+"'";  
		        	System.out.println("select * FROM DM_NODE_SUBRACK where NODEID='"+rs.getString("NODE_ID")+"'");
			        Statement stmnt_Subrack = con.createStatement();
			        ResultSet getSubrackRes = stmnt_Subrack.executeQuery(getSubracks);
			        while (getSubrackRes.next()) {
			        	
			        	String subrackID= localgetseqNbr(11);
			        	subrackID=Gyear+"_"+ "SUBRACK"+'_'+subrackID;
						System.out.println("SUBRACK ID: "+subrackID);
						
						System.out.println("insert into NODE_SUBRACK (SUBRACK_ID,CABINETNO,SUBRACKNO,SERIALNUMBER,NODE_PK,UPDATE_DATE,TRANS_TYPE,ACTIVE_RECORD) values "
			        			+"('"  + subrackID +"', '"+getSubrackRes.getString("CABINETNO")+"', '"+getSubrackRes.getString("SUBRACKNO")+"', '"+ getSubrackRes.getString("SERIALNUMBER") +"', '" + codeid +"' , sysdate, 'Data Migration','1')");
			        	PreparedStatement stmt1 = con.prepareStatement("insert into NODE_SUBRACK (SUBRACK_ID,CABINETNO,SUBRACKNO,SERIALNUMBER,NODE_PK,UPDATE_DATE,TRANS_TYPE,ACTIVE_RECORD)"
						 		+ "values('" + subrackID +"', '"+getSubrackRes.getString("CABINETNO")+"', '"+getSubrackRes.getString("SUBRACKNO")+"', '"+ getSubrackRes.getString("SERIALNUMBER") +"', '" + codeid +"' , sysdate , 'Data Migration','1')");
			        	
			        	stmt1.executeUpdate();
			        	stmt1.close();
			        }
			        getSubrackRes.close();
			        stmnt_Subrack.close();
			        
			        	////////////////// Slot
				        String getSlots = "select * FROM DM_NODE_SLOT where NODEID='"+rs.getString("NODE_ID")+"'";  
			        	System.out.println("select * FROM DM_NODE_SLOT where NODEID='"+rs.getString("NODE_ID")+"'");
				        Statement stmnt_Slots = con.createStatement();
				        ResultSet getSlotsRes = stmnt_Slots.executeQuery(getSlots);
				        while (getSlotsRes.next()) {
				        	
				        	String slotID= localgetseqNbr(4);
				        	slotID=Gyear+"_"+ "SLOT"+'_'+slotID;
							System.out.println("SLOT ID: "+slotID);
							
							System.out.println("insert into NODE_SLOT (SLOT_ID,CABINETNO,SUBRACKNO,SLOTNO,SERIALNUMBER,NODE_PK,UPDATE_DATE,TRANS_TYPE,ACTIVE_RECORD) values "
				        			+"('"  + slotID +"', '"+getSlotsRes.getString("CABINETNO")+"', '"+getSlotsRes.getString("SUBRACKNO")+"', '"+getSlotsRes.getString("SLOTNO")+"', '"+ getSlotsRes.getString("SERIALNUMBER") +"', '" + codeid +"'  ,sysdate, 'Data Migration','1')");
				        	PreparedStatement stmt1 = con.prepareStatement("insert into NODE_SLOT (SLOT_ID,CABINETNO,SUBRACKNO,SLOTNO,SERIALNUMBER,NODE_PK,UPDATE_DATE,TRANS_TYPE,ACTIVE_RECORD)"
							 		+ "values('" + slotID +"', '"+getSlotsRes.getString("CABINETNO")+"', '"+getSlotsRes.getString("SUBRACKNO")+"', '"+getSlotsRes.getString("SLOTNO")+"', '"+ getSlotsRes.getString("SERIALNUMBER") +"', '" + codeid +"'  ,sysdate, 'Data Migration','1')");
				        	
				        	stmt1.executeUpdate();
				        	stmt1.close();
				        }
				        getSlotsRes.close();
				        stmnt_Slots.close();
				        
				        ////////////////// Board
						String getBoards = "select * FROM DM_NODE_BOARD where NODEID='"+rs.getString("NODE_ID")+"'";  
					    System.out.println("select * FROM DM_NODE_BOARD where NODEID='"+rs.getString("NODE_ID")+"'");
						Statement stmnt_Boards = con.createStatement();
						ResultSet getBoardsRes = stmnt_Boards.executeQuery(getBoards);
						while (getBoardsRes.next()) {
						        	
							String boardID= localgetseqNbr(5);
							boardID=Gyear+"_"+ "BOARD"+'_'+boardID;
						    System.out.println("BOARD ID: "+boardID+" NODE ID: "+codeid);
									
							System.out.println("insert into NODE_BOARD (BOARD_ID,CABINETNO,SUBRACKNO,SLOTNO,SERIALNUMBER,NODE_PK,CREATION_DATE,UPDATE_DATE,TRANS_TYPE,ACTIVE_RECORD) values "
						        			+"('"  + boardID +"', '"+getBoardsRes.getString("CABINETNO")+"', '"+getBoardsRes.getString("SUBRACKNO")+"', '"+getBoardsRes.getString("SLOTNO")+"', '"+ getBoardsRes.getString("SERIALNUMBER") +"', '" + codeid +"' , sysdate ,sysdate, 'Data Migration','1')");
						    PreparedStatement stmt1 = con.prepareStatement("insert into NODE_BOARD (BOARD_ID,CABINETNO,SUBRACKNO,SLOTNO,SERIALNUMBER,NODE_PK,CREATION_DATE,UPDATE_DATE,TRANS_TYPE,ACTIVE_RECORD)"
									 		+ "values('" + boardID +"', '"+getBoardsRes.getString("CABINETNO")+"', '"+getBoardsRes.getString("SUBRACKNO")+"', '"+getBoardsRes.getString("SLOTNO")+"', '"+ getBoardsRes.getString("SERIALNUMBER") +"', '" + codeid +"' , sysdate ,sysdate, 'Data Migration','1')");
						        	
						    stmt1.executeUpdate();
						    stmt1.close();
						  }
						getBoardsRes.close();
						stmnt_Boards.close();
			        
		        	
		       
				
						System.out.println("End Node ID: "+codeid);
				

			}
			rs.close();
            stmt2.close();

			
			con.close();
			System.out.println("ITEM_NODE_TYPE COMPLETED");
	        }   
        		
      
	        catch(Exception e){
	  	      System.err.println(e);
	  	      e.printStackTrace();
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
			SeqName = "AUTO_DISCOVERY_LOGS_SEQ";
			break;
			
		case 22:
			SeqName = "AUTO_DISCOVERY_LOGS_DETAILS_SEQ";
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
