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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class LoadFileDWDMHuawei {
	static String readfileAIMfrom;
	static String copyfileAIMto;
	static int NodeSeq, BoardSeq, SubrackSeq;
	static BufferedReader objReader1 = null;
	static String strCurrentLine1;
	static String projpath = null;
	static String logpath;
	static String db1path;
	static String username1;
	static String password1;
	static String db2path;
	static String username2;
	static String password2, siteID;
	static String vfolderfrom, fileType, fileName;
	static String Gprovider, Domain, subDomain, subDomainType, gateway, gatewayType, gatewayIP, patchVersion,
			partNumber, others;
	static String boardId, boardName, boardType, subracknb, slotnb, serialnb, bomCode, hardwareVersion, biosVersion,
			issueNb, model, status;
	static String subrackId, subrackName, subrackType;
	static String vfolderto;
	static String circleid = "10";
	static String Gyear, wareID, wareName, longi, lat, creationDate, IPaddress, MACaddress, commStatus = "0",
			adminStatus = "0", softwareVersion, LCStatus = "0";
	static Logger logger;
	static FileHandler fh;
	static Connection conalm;
	static Connection con;
	static String tech2 = "0";
	static String tech3 = "0";
	static String tech4 = "0";
	static String tech5 = "0";
	static String vcodeid = "0";
	static PreparedStatement stmtp;
	static Statement stmtp1;
	static ArrayList<String> FileName = new ArrayList<String>();

	static String nodeId = null;
	static String nodeType = null;
	static String nodeModel = null;
	static String nodeName = null;
	static String unique_Node_ID = null;

	public static void main(String[] args, String vendor, String domain, String sub_domain, String sub_domainType)
			throws Exception {

		FileName = new ArrayList<String>();
		Resource ConfigResource = new ClassPathResource("almconfig.dat");
		File configfile = ConfigResource.getFile();
		FileReader fr = new FileReader(configfile);
		BufferedReader objReader1 = new BufferedReader(fr);

		while ((strCurrentLine1 = objReader1.readLine()) != null) {
			String data = strCurrentLine1;
			String[] data1;

			if (data.contains("projectpath")) {
				data1 = data.split(";", -1);
				projpath = data1[1];
			}
			if (data.contains("logpath")) {
				data1 = data.split(";", -1);
				logpath = data1[1];
			}
			if (data.contains("db1path")) {
				data1 = data.split(";", -1);
				db1path = data1[1];
				username1 = data1[2];
				password1 = data1[3];
			}
			if (data.contains("db2path")) {
				data1 = data.split(";", -1);
				db2path = data1[1];
				username2 = data1[2];
				password2 = data1[3];
			}
			if (data.contains("readexcelTransOptDWDMHWfrom")) {
				data1 = data.split(";", -1);
				readfileAIMfrom = data1[1];
				vfolderfrom = readfileAIMfrom;
				Gprovider = vendor;
				Domain = domain;
				subDomain = sub_domain;
				subDomainType = sub_domainType;
			}
			if (data.contains("copyexcelTransOptDWDMHWto")) {
				data1 = data.split(";", -1);
				copyfileAIMto = data1[1];

			}

		}
		objReader1.close();

		Resource CircleRsource = new ClassPathResource("almcircle.dat");
		File circlefile = CircleRsource.getFile();
		fr = new FileReader(circlefile);
		objReader1 = new BufferedReader(fr);

		while ((strCurrentLine1 = objReader1.readLine()) != null) {
			String data = strCurrentLine1;
			String[] data1;
			data1 = data.split(";", -1);
			circleid = data1[1];
		}
		objReader1.close();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		String lofilename = "ParserLogDWDMHW-" + dtf.format(now) + ".log";

		File folder = new File(vfolderfrom);
		File[] listOfFiles = folder.listFiles();
		String fileName1 = null;

		logger = Logger.getLogger("MyLog");
		logger.setUseParentHandlers(false);

		// This block configure the logger with handler and formatter and PATH

		fh = new FileHandler(logpath + "/" + lofilename);
		logger.addHandler(fh);
		SimpleFormatter formatter = new SimpleFormatter();
		fh.setFormatter(formatter);

		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		conalm = DriverManager.getConnection(db1path, username1, password1);

		try {
			// con= DriverManager.getConnection(dbURL,username,password);
			con = DriverManager.getConnection(db2path, username2, password2);
		} catch (SQLException e) {
			System.out.println("Opss, error");
			e.printStackTrace();
		}

		// validate if the same process is running now if yes we cannot run it twice
		// until finish
		Statement stmtinit21 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		String sqlStmtinit21 = "select * from EXECUTE_DOAMIN_VENDOR_FILES where DOMAIN='" + Domain + "' and VENDOR='"
				+ Gprovider + "' and STATUS='IN PROCESS' and SUB_DOMAIN='" + subDomain + "' and TYPE='" + subDomainType
				+ "'";
		ResultSet rsinit21 = stmtinit21.executeQuery(sqlStmtinit21);
		rsinit21.last();
		int totalrecinit2 = rsinit21.getRow();
		rsinit21.beforeFirst();
		System.out.println(totalrecinit2);
		if (totalrecinit2 == 0) {
			PreparedStatement stmtinit = con.prepareStatement(
					"insert into EXECUTE_DOAMIN_VENDOR_FILES (DOMAIN,VENDOR,CREATION_DATE,STATUS,SUB_DOMAIN,TYPE) values ('"
							+ Domain + "', '" + Gprovider + "',sysdate,'IN PROCESS','" + subDomain + "','"
							+ subDomainType + "')");
			stmtinit.executeUpdate();
			stmtinit.close();
			logger.info("Load DWDM HW Files inprocess...");
			for (File file : listOfFiles) {

				if (file.isFile()) {
					fileName1 = file.getName().toString();
					// System.out.println("filename1 is : "+fileName1); //BOARD
					if (fileName1.contains("Network")) {
						FileName.add(0, fileName1);
					} else {
						FileName.add(fileName1);
					}
					String[] data1 = fileName1.replace(".", "_").split("_");
					fileName = data1[0];
					fileType = data1[1];
				}

			}

			for (int i = 0; i < FileName.size(); i++) {
				System.out.println("FileName.get(i)........" + FileName.get(i));
				readfile(FileName.get(i));
				File source = new File(readfileAIMfrom + "/" + FileName.get(i));
				File dest = new File(copyfileAIMto + "/" + FileName.get(i) + ".bkp");
				copyFiles(source, dest);
				deleteFiles(readfileAIMfrom + "/" + FileName.get(i));
			}

			logger.info("Load DWDM HW Files completed");
			System.out.println("Load DWDM HW Files completed");
			// update file status to completed
			stmtp = con.prepareStatement("update EXECUTE_DOAMIN_VENDOR_FILES set STATUS ='COMPLETED' where DOMAIN='"
					+ Domain + "' and VENDOR='" + Gprovider + "' and STATUS='IN PROCESS' and SUB_DOMAIN='" + subDomain
					+ "' and TYPE='" + subDomainType + "'");
			stmtp.executeUpdate();
			stmtp.close();

		} else {
			System.out.println("A process already is running , please wait until process ending");
			logger.info("A process already is running , please wait until process ending");
		}

		GetduplicateFilename(Domain, Gprovider, subDomain, subDomainType);

		conalm.close();
		con.close();
	}

	private static void readfile(String fileName) throws FileNotFoundException, IOException, SQLException {

		Calendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);

		// csvparser used to read csv file in order to fill the data in a list of type
		// CSVRecord row by row
		CSVParser csvParser = new CSVParser(new FileReader(vfolderfrom + "/" + fileName), CSVFormat.DEFAULT);
		List<CSVRecord> records = new ArrayList<>();
		for (CSVRecord record : csvParser) {
			records.add(record);
		}

		if (fileName.contains("Network")) {
			System.out.println("entered network record ");
			// select the node active sequence from the seq table in alm.
			String sqlStmtinit2 = "select NODE_ACTIVE from SEQ_TABLE";
			stmtp1 = conalm.createStatement();
			ResultSet rsinit2 = stmtp1.executeQuery(sqlStmtinit2);
			while (rsinit2.next()) {
				// store the returned result in a variable to be increased each loop instead of
				// accessing the database all the time
				// which lead to exceed the maximum number of open cursors.
				NodeSeq = rsinit2.getInt("NODE_ACTIVE");
				// update the seq of the node active based on the size of the list filled from
				// the csv file
				stmtp = conalm
						.prepareStatement("UPDATE SEQ_TABLE SET NODE_ACTIVE = NODE_ACTIVE +" + (records.size() - 4));// records.size()-4)
																														// is
																														// used
																														// to
																														// remove
																														// the
																														// unnecessary
																														// header
																														// rows
																														// in
																														// the
																														// csv
																														// file
				stmtp.executeUpdate();
				stmtp.close();
			}
			for (int i = 4; i < records.size(); i++) {
				wareID = "";
				wareName = "";
				longi = "";
				lat = "";
				siteID = "";

				vcodeid = year + "_NODE_" + Gprovider + "_" + Domain + "_" + NodeSeq;
				rsinit2.close();
				stmtp1.close();
				if (records.get(i).get(0).contains("_")) {// if the cell of the csv file contains _ then it may contain
															// site ID
					siteID = records.get(i).get(0).split("_")[0];
					char charArray[] = siteID.toCharArray();
					if (Character.isDigit(charArray[0]) && !Character.isDigit(charArray[siteID.length() - 1])) { // if
																													// the
																													// first
																													// character
																													// of
																													// the
																													// possible
																													// site
																													// id
																													// is
																													// number
																													// then
																													// it
																													// is
																													// a
																													// site
																													// ID.
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

				} else {
					wareID = "";
					wareName = "";
					longi = "";
					lat = "";
					siteID = "";
				}
				nodeName = records.get(i).get(0);
				nodeType = "DWDM";
				nodeModel = records.get(i).get(1);
				IPaddress = records.get(i).get(2);
				nodeId = records.get(i).get(2);
				softwareVersion = records.get(i).get(3);
				MACaddress = records.get(i).get(4);
				partNumber = records.get(i).get(10);
				commStatus = records.get(i).get(11);
				adminStatus = records.get(i).get(12);
				if (!records.get(i).get(23).toString().trim().equalsIgnoreCase("--")) {
					LCStatus = records.get(i).get(23);
				}
				gateway = records.get(i).get(20);
				gatewayType = records.get(i).get(19);
				gatewayIP = records.get(i).get(21);
				patchVersion = records.get(i).get(17);
				unique_Node_ID = nodeId + "_HW";
				// others = "{\"NE
				// ID\":"+"\""+networkRecords.get(i).get(5)+"\","+"\"Fibers/Cables\":"+"\""+networkRecords.get(i).get(6)+"\","+"\"Subnet\":"+"\""+networkRecords.get(i).get(7)+"\","+"\"Subnet
				// Path\":"+"\""+networkRecords.get(i).get(8)+"\","+"\"Subrack
				// Type\":"+"\""+networkRecords.get(i).get(9)+"\","+"\"Physical
				// Location\":"+"\""+networkRecords.get(i).get(13)+"\","+"\"Created
				// On\":"+"\""+networkRecords.get(i).get(14)+"\","+"\"NE
				// Alias\":"+"\""+networkRecords.get(i).get(15)+"\","+"\"Remarks\":"+"\""+networkRecords.get(i).get(16)+"\","+"\"LSR
				// ID\":"+"\""+networkRecords.get(i).get(18)+"\","+"\"Optical
				// NE\":"+"\""+networkRecords.get(i).get(22)+"\""+"}";
				others = "{\"NE ID\":" + "\"" + records.get(i).get(5) + "\"," + "\"Fibers/Cables\":" + "\""
						+ records.get(i).get(6) + "\"," + "\"Subnet\":" + "\"" + records.get(i).get(7) + "\","
						+ "\"Subnet Path\":" + "\"" + records.get(i).get(8) + "\"," + "\"Subrack Type\":" + "\""
						+ records.get(i).get(9) + "\"," + "\"Physical Location\":" + "\"" + records.get(i).get(13)
						+ "\"," + "\"Optical NE\":" + "\"" + records.get(i).get(22) + "\"" + "\"Created On\":" + "\""
						+ records.get(i).get(14) + "}";

				stmtp = con.prepareStatement(
						"insert into NODE_ACTIVE (NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_MODEL,TECH_2G,TECH_3G,TECH_4G,TECH_5G,SITE_ID,CIRCLE_ID,CREATION_DATE,UPDATE_DATE,FILE_TYPE,FILENAME,STATUS,WARE_ID,VENDOR,WARE_NAME,IP_ADDRESS,MAC_ADDRESS,SUB_DOMAIN,SOFTWARE_VERSION,STATUS_1,GATEWAY,GATEWAY_TYPE,GATEWAY_IP,STATUS_2,LONGITUDE,LATITUDE,PATCH_VERSION,PART_NUMBER,SUB_DOMAIN_TYPE,OTHERS,ACTIVE_RECORD)"
								+ "values('" + vcodeid + "','" + unique_Node_ID + "','" + nodeId + "','" + nodeName
								+ "','" + nodeType + "','" + Domain + "','" + nodeModel + "','" + tech2 + "','" + tech3
								+ "','" + tech4 + "','" + tech5 + "','" + siteID + "','" + circleid
								+ "',sysdate,sysdate,'" + fileType + "','" + fileName + "','" + commStatus + "','"
								+ wareID + "','" + Gprovider + "','" + wareName + "','" + IPaddress + "','" + MACaddress
								+ "','" + subDomain + "','" + softwareVersion + "','" + adminStatus + "','" + gateway
								+ "','" + gatewayType + "','" + gatewayIP + "','" + LCStatus + "','" + longi + "','"
								+ lat + "','" + patchVersion + "','" + partNumber + "','" + subDomainType + "','"
								+ others + "','1')");
				stmtp.executeUpdate();
				stmtp.close();

				NodeSeq++;

			}

			// end parsing of node network
		} else {

			String sqlStmtinit2 = "";
			ResultSet rsinit2;
			System.out.println("Entered ELSE");
			// start board parsing
			if (fileName.contains("Boards")) {
				System.out.println("Boards entered");
				// select the board sequence from the seq table in alm.
				sqlStmtinit2 = "select NODE_BOARD from SEQ_TABLE";
				stmtp1 = conalm.createStatement();
				rsinit2 = stmtp1.executeQuery(sqlStmtinit2);
				while (rsinit2.next()) {
					// store the returned result in a variable to be increased each loop instead of
					// accessing the database all the time
					// which lead to exceed the maximum number of open cursors.
					BoardSeq = rsinit2.getInt("NODE_BOARD");
					// update the seq of the board based on the size of the list filled from the csv
					// file
					// records.size()-4) is used to remove the unnecessary header rows in the csv
					// file
					stmtp = conalm
							.prepareStatement("UPDATE SEQ_TABLE SET NODE_BOARD = NODE_BOARD +" + (records.size() - 4));
					stmtp.executeUpdate();
					stmtp.close();
				}
				for (int i = 4; i < records.size(); i++) {
					boardId = year + "_NODE_" + Gprovider + "_" + Domain + "_BRD_" + BoardSeq;
					rsinit2.close();
					stmtp1.close();

					nodeName = records.get(i).get(0);
					nodeId = records.get(i).get(5);

					boardName = records.get(i).get(1);
					boardType = records.get(i).get(3);
					subracknb = records.get(i).get(8);
					slotnb = records.get(i).get(9);
					hardwareVersion = records.get(i).get(10);
					softwareVersion = records.get(i).get(11);
					serialnb = records.get(i).get(12);
					bomCode = records.get(i).get(22);
					biosVersion = records.get(i).get(18);
					issueNb = records.get(i).get(16);
					model = records.get(i).get(3);
					status = records.get(i).get(19);
					others = "{\"Subrack Type\":" + "\"" + records.get(i).get(7) + "\"," + "\"FPGA Version\":" + "\""
							+ records.get(i).get(17) + "\"," + "\"," + "\"Administrative Status\":" + "\""
							+ records.get(i).get(23) + "\"}";

					stmtp = con.prepareStatement(
							"insert into NODE_BOARD (BOARD_ID,SUBRACKNO,SLOTNO,BOARDNAME,BOARDTYPE,SERIALNUMBER,HARDWAREVERSION,SOFTVER,BIOSVER,ISSUENUMBER,BOMCODE,MODEL,NODE_PK,UPDATE_DATE,FILENAME,STATUS,CREATION_DATE,DOMAIN,VENDOR,OTHERS,ACTIVE_RECORD)"
									+ "values('" + boardId + "','" + subracknb + "','" + slotnb + "','" + boardName
									+ "','" + boardType + "','" + serialnb + "','" + hardwareVersion + "','"
									+ softwareVersion + "','" + biosVersion + "','" + issueNb + "','" + bomCode + "','"
									+ model + "',(select NODE_PK from NODE_ACTIVE where NODE_ID='" + nodeId
									+ "' and domain='" + Domain + "' and vendor='" + Gprovider
									+ "' order by creation_date desc fetch first 1 row only),sysdate,'" + fileName
									+ "','" + status + "',sysdate,'" + Domain + "','" + Gprovider + "','" + others
									+ "','1')");
					stmtp.executeUpdate();
					stmtp.close();

					BoardSeq++;
				}
			}
			// end of board

			// start of subrack
			if (fileName.contains("subrack")) {
				System.out.println("subrack Entered ");
				// select the subrack sequence from the seq table in alm.
				sqlStmtinit2 = "select NODE_SUBRACK from SEQ_TABLE";
				stmtp1 = conalm.createStatement();
				rsinit2 = stmtp1.executeQuery(sqlStmtinit2);
				while (rsinit2.next()) {
					// store the returned result in a variable to be increased each loop instead of
					// accessing the database all the time
					// which lead to exceed the maximum number of open cursors.
					SubrackSeq = rsinit2.getInt("NODE_SUBRACK");
					// update the seq of the subrack based on the size of the list filled from the
					// csv file
					stmtp = conalm.prepareStatement(
							"UPDATE SEQ_TABLE SET NODE_SUBRACK = NODE_SUBRACK +" + (records.size() - 4));
					stmtp.executeUpdate();
					stmtp.close();
				}
				for (int i = 4; i < records.size(); i++) {
					subrackId = year + "_NODE_" + Gprovider + "_" + Domain + "_SUBRACK_" + SubrackSeq;
					rsinit2.close();
					stmtp1.close();

					nodeName = records.get(i).get(0);
					nodeId = records.get(i).get(4);
					subrackName = records.get(i).get(1);
					subrackType = records.get(i).get(2);
					subracknb = records.get(i).get(9);
					softwareVersion = records.get(i).get(4);
					serialnb = records.get(i).get(5);
					bomCode = records.get(i).get(12);
					status = records.get(i).get(10);
					others = "{\"Manufactured On\":" + "\"" + records.get(i).get(14) + "\","
							+ "\"Equipment Room Name\":" + "\"" + records.get(i).get(15) + "\"," + "\"Rack Name\":"
							+ "\"" + records.get(i).get(16) + "\"," + "\"Subrack Number\":" + "\""
							+ records.get(i).get(17) + "\"," + "\"Remarks\":" + "\"" + records.get(i).get(18) + "\""
							+ "}";

					String sql = null;
					String manifacturedDate = records.get(i).get(14);
					if (manifacturedDate != null && !manifacturedDate.isEmpty() && !manifacturedDate.contains("--")) {
						if (!manifacturedDate.contains("-")) {
							manifacturedDate = "";
						}
						sql = "insert into NODE_SUBRACK (SUBRACK_ID,SUBRACKNO,SERIALNUMBER,BOMCODE,NODE_PK,UPDATE_DATE,FILENAME,STATUS,DOMAIN,VENDOR,OTHERS,ACTIVE_RECORD,RACKTYPE,MODEL,DATEOFMANUFACTURE)"
								+ "values('" + subrackId + "','" + subracknb + "','" + serialnb + "','" + bomCode
								+ "',(select NODE_PK from NODE_ACTIVE where NODE_ID='" + nodeId + "' and domain='"
								+ Domain + "' and vendor='" + Gprovider
								+ "' order by creation_date desc fetch first 1 row only),sysdate,'" + fileName + "','"
								+ status + "','" + Domain + "','" + Gprovider + "','" + others + "','1','" + subrackType
								+ "','" + subrackType + "',TO_DATE('" + manifacturedDate + "','YYYY-MM-DD'))";

					} else {
						String manfdate = "";
						sql = "insert into NODE_SUBRACK (SUBRACK_ID,SUBRACKNO,SERIALNUMBER,BOMCODE,NODE_PK,UPDATE_DATE,FILENAME,STATUS,DOMAIN,VENDOR,OTHERS,ACTIVE_RECORD,RACKTYPE,MODEL,DATEOFMANUFACTURE)"
								+ "values('" + subrackId + "','" + subracknb + "','" + serialnb + "','" + bomCode
								+ "',(select NODE_PK from NODE_ACTIVE where NODE_ID='" + nodeId + "' and domain='"
								+ Domain + "' and vendor='" + Gprovider
								+ "' order by creation_date desc fetch first 1 row only),sysdate,'" + fileName + "','"
								+ status + "','" + Domain + "','" + Gprovider + "','" + others + "','1','" + subrackType
								+ "','" + subrackType + "',TO_DATE('" + manfdate + "','YYYY-MM-DD'))";
					}
					stmtp = con.prepareStatement(sql);
					stmtp.executeUpdate();
					stmtp.close();

					SubrackSeq++;
				}
			}
		}
	}

	private static void GetduplicateFilename(String vdomain, String vvendor, String subDomain, String type)
			throws SQLException {
		Statement stmt1 = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		int vcount = 0;
		int i = 0;

		// select all file having duplicata entry and same filename >1
		String query1 = "select NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR,SUB_DOMAIN,SUB_DOMAIN_TYPE,count(*) counter from NODE_ACTIVE  group by  NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR,SUB_DOMAIN,SUB_DOMAIN_TYPE having count(*) >1 and DOMAIN='"
				+ vdomain + "' and VENDOR='" + vvendor + "' and SUB_DOMAIN='" + subDomain + "' and SUB_DOMAIN_TYPE ='"
				+ type + "'";
		stmt1 = con.createStatement();
		ResultSet rs1 = stmt1.executeQuery(query1);
		while (rs1.next()) {
			// try {
			// select all rows related to a file identified by rs1 having count >=1
			String query2 = "select NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR,SUB_DOMAIN,SUB_DOMAIN_TYPE,count(*) counter from (select NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR,SUB_DOMAIN,SUB_DOMAIN_TYPE from NODE_ACTIVE where (SITE_ID ='"
					+ rs1.getString("SITE_ID") + "' OR SITE_ID IS NULL) and CIRCLE_ID ='" + rs1.getString("CIRCLE_ID")
					+ "' and NODE_ID ='" + rs1.getString("NODE_ID") + "' and NODE_NAME ='" + rs1.getString("NODE_NAME")
					+ "' and DOMAIN='" + vdomain + "' and VENDOR='" + vvendor + "' and SUB_DOMAIN='" + subDomain
					+ "' and SUB_DOMAIN_TYPE ='" + type
					+ "' ) group by  NODE_ID,NODE_NAME,SITE_ID,CIRCLE_ID,DOMAIN,VENDOR,SUB_DOMAIN,SUB_DOMAIN_TYPE having count(*) >=1 and DOMAIN='"
					+ vdomain + "' and VENDOR='" + vvendor + "' and SUB_DOMAIN='" + subDomain
					+ "' and SUB_DOMAIN_TYPE ='" + type + "'";
			stmt2 = con.createStatement();
			ResultSet rs2 = stmt2.executeQuery(query2);
			// get all node_pk found duplicated
			while (rs2.next()) {
				vcount = 0;
				vcount = (int) Long.parseLong(rs2.getString("counter"));
				i = 0;
				// Get old creation date of the same file and update rows with old creation date
				String query3 = "select node_pk,creation_date from NODE_ACTIVE where NODE_ID='"
						+ rs2.getString("NODE_ID") + "' and (SITE_ID='" + rs2.getString("SITE_ID")
						+ "' OR SITE_ID IS NULL) and NODE_NAME ='" + rs2.getString("NODE_NAME") + "' and CIRCLE_ID ='"
						+ rs2.getString("CIRCLE_ID") + "' and DOMAIN='" + vdomain + "' and VENDOR='" + vvendor
						+ "' and SUB_DOMAIN='" + subDomain + "' and SUB_DOMAIN_TYPE ='" + type
						+ "' order by creation_date asc";
				stmt3 = con.createStatement();
				// stmt3.setMaxRows(1);
				ResultSet rs3 = stmt3.executeQuery(query3);
				while (rs3.next()) {
					if (i == 0) { // if i =0 to get old creation date

						String vdate = rs3.getString("creation_date");

						// convert date to yyyy-mm-dd
						String vyear;
						String vmonth;
						String vday;
						int n = vdate.indexOf("-");
						vyear = vdate.substring(0, n).trim();
						vdate = vdate.substring(n + 1, vdate.length()).trim();
						n = vdate.indexOf("-");
						vmonth = vdate.substring(0, n).trim();
						if (vmonth.length() < 2) {
							vmonth = "0" + vmonth;
						}
						vday = vdate.substring(n + 1, n + 3).trim();
						if (vday.length() < 2) {
							vday = "0" + vday;
						}
						vdate = vyear + "-" + vmonth + "-" + vday;
						/// end convert date

						System.out.println("after formatting : " + vdate);
						// update creation date with old creation date
						// where filename ='"+ rs2.getString("filename") +"'");
						PreparedStatement updtmt8 = con
								.prepareStatement("update NODE_ACTIVE set creation_date = TIMESTAMP '"
										+ rs3.getString("creation_date") + "',update_date=sysdate where NODE_NAME ='"
										+ rs2.getString("NODE_NAME") + "' and CIRCLE_ID ='" + rs2.getString("CIRCLE_ID")
										+ "' and DOMAIN='" + vdomain + "' and VENDOR='" + vvendor + "' and SUB_DOMAIN='"
										+ subDomain + "' and SUB_DOMAIN_TYPE ='" + type + "'");
						updtmt8.executeUpdate();
						updtmt8.close();

						// Function to delete the filename from all tables argument field name and field
						// value
						deleterowsinALLTABLES("NODE_PK", rs3.getString("node_pk"), vdomain, vvendor, subDomain, type);
					}
					if ((i > 0) && (i < (vcount - 1))) { // if i< maxcount just to delete duplicate node_pk from all
															// table

						// Function to delete the filename from all tables argument field name and field
						// value
						deleterowsinALLTABLES("NODE_PK", rs3.getString("node_pk"), vdomain, vvendor, subDomain, type);
					}

					i = i + 1;
				}
				rs3.close(); // end of read rows and delete duplicata except the last one
				stmt3.close();

			}
			rs2.close(); // end of dupliacted node_pk
			stmt2.close();
		}
	}

	private static void deleterowsinALLTABLES(String fieldname, String fieldValue, String vdomain, String vvendor,
			String subDomain, String type) throws SQLException {
		try {
			// delete all rows related to node_pk from all nodes tables
			PreparedStatement stmt = con.prepareStatement("delete from NODE_ACTIVE where " + fieldname + " = '"
					+ fieldValue + "' and DOMAIN='" + vdomain + "' and VENDOR='" + vvendor + "' and SUB_DOMAIN='"
					+ subDomain + "' and SUB_DOMAIN_TYPE ='" + type + "'");
			stmt.executeUpdate();
			stmt.close();

			stmt = con.prepareStatement("delete from  NODE_BOARD where " + fieldname + " = '" + fieldValue
					+ "' and DOMAIN='" + vdomain + "' and VENDOR='" + vvendor + "'");
			stmt.executeUpdate();
			stmt.close();

			stmt = con.prepareStatement("delete from  NODE_SUBRACK where " + fieldname + " = '" + fieldValue
					+ "' and DOMAIN='" + vdomain + "' and VENDOR='" + vvendor + "'");
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
		} catch (Exception e) {
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

		}

	}

	private static void copyFiles(File source, File dest) throws IOException {
		try {
			Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.COPY_ATTRIBUTES,
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void deleteFiles(String source) throws InterruptedException {
		System.gc();// Added this part
		Thread.sleep(150);
		File telecomfile = new File(source);
		try {
			FileUtils.forceDelete(telecomfile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
