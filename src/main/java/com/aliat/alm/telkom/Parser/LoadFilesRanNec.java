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

public class LoadFilesRanNec {

	static String readFileNecFrom;
	static String copyFileNecTo;
	static int NodeSeq,CellSeq;
	static int NodeBoardSeq, NodeBoardSeqTemp;

	static BufferedReader objReader = null;
	static String strCurrentLine;
	static String projectPath = null;
	static String logPath;
	static String almDbPath;
	static String almDbUsername;
	static String almDbPassword;
	static String parserDbPath;
	static String parserDbUsername;
	static String parserDbPassword, siteID;
	static String vfolderfrom, fileType, fileName;
	static String provider, Domain, subDomain, subDomainType, gateway, gatewayType, gatewayIP, patchVersion, partNumber;
	static String circleId = "10";
	static String wareID = null, wareName = null, longi = null, lat = null, creationDate, IPaddress, MACaddress,
			commStatus = "0", adminStatus = "0", softwareVersion, LCStatus = "0";
	static Logger logger;
	static FileHandler fh;
	static Connection conalm;
	static Connection con;
	static String tech2 = "0";
	static String tech3 = "0";
	static String tech4 = "0";
	static String tech5 = "0";
	static String vcodeid = "0";
	static String boardID = "0";
	static PreparedStatement stmtp, stmtp2,cellStm;
	static Statement stmtp1;

	static String nodeId = null;
	static String nodeType = null;
	static String nodeModel = null;
	static String nodeName = null;
	static String unique_Node_ID = null;
	static String siteIndex = null, cabinetNO = null, subrackNO = null, rackNO = null, frameNO = null, slotNO = null,
			slotPos = null, subslotNO = null, moduleNO = null;
	static String boardName = null, boardType = null, vendorFamType = null, vendorUnitTypeNO = null, vendorName;
	static String serialNumber = null, boardHardwareVer = null, dateOfManufact = null, dateOflastService = null,
			unitPosition = null, manufacturerData = null, boardSoftwareVersion = null, boardLogicVersion = null,
			boardBiosVersion = null;
	static String boardMbusVer = null, boardLanVer = null, isSueNumber = null, boardBomCode = null, model = null,
			userLabel = null;
	static String MISSING = null, ApdevInfo = null, workMode = null, boardStatus = null, fromTransSrc = null,
			fromTransID = null, toTransID = null, transType = null;
	static String activeRecord = "1", line = null, almPosition = null, toTransType = null;
	static String cellID,cellName,CellPK;

	public static void main(String[] args, String vendor, String domain) throws Exception {

		// objReader = new BufferedReader(new
		// FileReader(System.getProperty("user.dir")+"\\"+"almconfig.dat"));
		Resource ConfigResource = new ClassPathResource("almconfig.dat");
		File configfile = ConfigResource.getFile();
		FileReader fr = new FileReader(configfile);
		BufferedReader objReader = new BufferedReader(fr);

		while ((strCurrentLine = objReader.readLine()) != null) {
			String data = strCurrentLine;
			String[] splittedStr;

			if (data.contains("projectpath")) {
				splittedStr = data.split(";", -1);
				projectPath = splittedStr[1];
			}
			if (data.contains("logpath")) {
				splittedStr = data.split(";", -1);
				logPath = splittedStr[1];
			}
			if (data.contains("db1path")) {
				splittedStr = data.split(";", -1);
				almDbPath = splittedStr[1];
				almDbUsername = splittedStr[2];
				almDbPassword = splittedStr[3];
			}
			if (data.contains("db2path")) {
				splittedStr = data.split(";", -1);
				parserDbPath = splittedStr[1];
				parserDbUsername = splittedStr[2];
				parserDbPassword = splittedStr[3];
			}
			if (data.contains("readFileRanNecFrom")) {
				splittedStr = data.split(";", -1);
				readFileNecFrom = splittedStr[1];
				vfolderfrom = readFileNecFrom;
				provider = vendor;
				Domain = domain;
			}
			if (data.contains("copyFileRanNecTo")) {
				splittedStr = data.split(";", -1);
				copyFileNecTo = splittedStr[1];

			}

		}
		objReader.close();

		// objReader = new BufferedReader(new
		// FileReader(System.getProperty("user.dir")+"\\"+"almcircle.dat"));
		Resource CircleRsource = new ClassPathResource("almcircle.dat");
		File circlefile = CircleRsource.getFile();
		fr = new FileReader(circlefile);
		objReader = new BufferedReader(fr);

		while ((strCurrentLine = objReader.readLine()) != null) {
			String data = strCurrentLine;
			String[] circleID;
			circleID = data.split(";", -1);
			circleId = circleID[1];
		}
		objReader.close();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		String lofilename = "ParserLogRanNec-" + dtf.format(now) + ".log";

		File folder = new File(vfolderfrom);
		File[] listOfFiles = folder.listFiles();
		String fileName = null;

		logger = Logger.getLogger("MyLog");
		logger.setUseParentHandlers(false);

		// This block configure the logger with handler and formatter and PATH

		fh = new FileHandler(logPath + "/" + lofilename);
		logger.addHandler(fh);
		SimpleFormatter formatter = new SimpleFormatter();
		fh.setFormatter(formatter);

		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		conalm = DriverManager.getConnection(almDbPath, almDbUsername, almDbPassword);

		try {
			con = DriverManager.getConnection(parserDbPath, parserDbUsername, parserDbPassword);
		} catch (SQLException e) {
			System.out.println("Opss, error");
			e.printStackTrace();
		}

		// validate if the same process is running now if yes we cannot run it twice
		// until finish
		Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		String sqlQuery = "select * from EXECUTE_DOAMIN_VENDOR_FILES where DOMAIN='" + Domain + "' and VENDOR='"
				+ provider + "' and STATUS='IN PROCESS' ";
		ResultSet resultSet = statement.executeQuery(sqlQuery);
		resultSet.last();
		int totalRowsNum = resultSet.getRow();
		resultSet.beforeFirst();
		System.out.println("totalRowsNum" + totalRowsNum);

		if (totalRowsNum == 0) {
			PreparedStatement insertStmt = con.prepareStatement(
					"insert into EXECUTE_DOAMIN_VENDOR_FILES (DOMAIN,VENDOR,CREATION_DATE,STATUS) values ('" + Domain
							+ "', '" + provider + "',sysdate,'IN PROCESS')");
			insertStmt.executeUpdate();
			insertStmt.close();
			logger.info("Load RAN NEC Files in process...");

			// looping over all files found in the directory.
			for (File file : listOfFiles) {
				if (file.isFile()) {
					fileName = file.getName().toString();
					System.out.println("file name is : " + fileName);

					// splitting to get the file name and extension
					fileType = "csv";

					readfile(fileName);
					File source = new File(readFileNecFrom + "/" + file.getName());
					File dest = new File(copyFileNecTo + "/" + file.getName() + ".bkp");

					copyFiles(source, dest);
					deleteFiles(readFileNecFrom + "/" + file.getName());
				}

			}

		} else {

		}

		// GetduplicateFilename(Domain,provider,subDomain,subDomainType);
		logger.info("Load RAN NEC Files completed");
		// update file status to completed
		stmtp = con.prepareStatement("update EXECUTE_DOAMIN_VENDOR_FILES set STATUS ='COMPLETED' where DOMAIN='"
				+ Domain + "' and VENDOR='" + provider + "' and STATUS='IN PROCESS' ");
		stmtp.executeUpdate();
		stmtp.close();

		conalm.close();
		con.close();
	}

	private static void readfile(String fileName) throws FileNotFoundException, IOException, SQLException {

		CSVParser csvParser = new CSVParser(new FileReader(vfolderfrom + "/" + fileName), CSVFormat.DEFAULT);
		List<CSVRecord> records = new ArrayList<>();
		for (CSVRecord record : csvParser) {
			records.add(record);
		}

		Calendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);

		String sqlStmtinit = "select NODE_ACTIVE,NODE_LCELL from SEQ_TABLE";
		stmtp1 = conalm.createStatement();
		ResultSet rsinit = stmtp1.executeQuery(sqlStmtinit);
		while (rsinit.next()) {
			NodeSeq = rsinit.getInt("NODE_ACTIVE");
			CellSeq=rsinit.getInt("NODE_LCELL");
		}
		stmtp1.close();
		rsinit.close();

		String nodeBoardSeq = "select NODE_BOARD from SEQ_TABLE";
		stmtp1 = conalm.createStatement();
		ResultSet resultSet = stmtp1.executeQuery(nodeBoardSeq);
		while (resultSet.next()) {
			NodeBoardSeq = resultSet.getInt("NODE_BOARD");
		}

		stmtp1.close();
		resultSet.close();

		for (int i = 1; i < records.size(); i++) {
			wareID = "";
			wareName = "";
			longi = "";
			lat = "";
			siteID = "";
			nodeModel = records.get(i).get(2);
			if (nodeModel.startsWith("HAR")) {

				siteID = records.get(i).get(0).substring(0, 5);
				char charArray[] = siteID.toCharArray();
				if (Character.isDigit(charArray[0]) && !Character.isDigit(charArray[siteID.length() - 1])) {
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
				}
				nodeName = records.get(i).get(0).replace("(eNodeB)", " ").trim();
				nodeId = nodeName.split("_", -1)[0];
				unique_Node_ID = nodeId+"_NEC";
				nodeType = "eNodeB";

				IPaddress = null;
				MACaddress = records.get(i).get(16);
				partNumber = null;
				commStatus = "0";
				adminStatus = "0";
				LCStatus = "0";
				serialNumber = records.get(i).get(6);
				vcodeid = year + "_NODE_NEC_"+ Domain + "_" + NodeSeq;

				stmtp = con.prepareStatement(
						"insert into NODE_ACTIVE (NODE_PK,UNIQUE_NODE_ID,NODE_ID,NODE_NAME,NODE_TYPE,DOMAIN,NODE_MODEL,TECH_2G,TECH_3G,TECH_4G,TECH_5G,SITE_ID,CIRCLE_ID,CREATION_DATE,UPDATE_DATE,FILE_TYPE,FILENAME,STATUS,ACTIVE_RECORD,WARE_ID,VENDOR,WARE_NAME,IP_ADDRESS,MAC_ADDRESS,SUB_DOMAIN,LONGITUDE,LATITUDE,PART_NUMBER,SUB_DOMAIN_TYPE,SERIAL_NUMBER)"
								+ "values('" + vcodeid + "','" + unique_Node_ID + "','" + nodeId + "','" + nodeName
								+ "','" + nodeType + "','" + Domain + "','" + nodeModel + "','" + tech2 + "','" + tech3
								+ "','" + tech4 + "','" + tech5 + "','" + siteID + "','" + circleId
								+ "',sysdate,sysdate,'" + fileType + "','" + fileName + "','" + commStatus + "','1','"
								+ wareID + "','" + provider + "','" + wareName + "','" + IPaddress + "','" + MACaddress
								+ "','" + subDomain + "','" + longi + "','"
								+ lat + "','" + partNumber + "','" + subDomainType + "','" + serialNumber + "')");
				stmtp.executeUpdate();
				stmtp.close();
				
				NodeSeq++;
				//added
				
				CellPK = year + "_NEC_RAN_CELL" + "_" + CellSeq;
				cellID=nodeId;
				cellName=nodeName;
				cellStm = con.prepareStatement("insert into NODE_LCELL (LCELL_ID,LOCALCELLID,CELLID,CELLNAME,CELLRADIUS,FREQBAND,ULEARFCNCFGIND,ULEARFCN,DLEARFCN,ULBANDWIDTH,DLBANDWIDTH,PHYCELLID,FDDTDDIND,ENODEBFUNCTIONNAME,NBCELLFLAG,NODE_PK,NODE_ATTR_PK,CREATION_DATE,UPDATE_DATE,FILENAME,STATUS,FROM_TRANS_SOURCE,TO_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,DOMAIN,VENDOR)"
						+ "values('"+CellPK+"','','"+cellID+"','"+cellName+"','','','','','','','','','','','','"+vcodeid+"','',sysdate,sysdate,"
								+ "'"+fileName+"','"+commStatus+"','0','0','0','0','0','1','0','"+Domain+"','"+provider+"')");
				cellStm.executeUpdate();
				cellStm.close();
				
				CellSeq++;
				

				

			}

		}

		for (int i = 1; i < records.size(); i++) {
			model = records.get(i).get(2);
			if (!model.startsWith("HAR") && !model.equalsIgnoreCase(".")) {
				boardID = year + "_Node_NEC_RAN_BRD" + NodeBoardSeq;
				moduleNO = records.get(i).get(4);
				vendorName = "NEC";
				serialNumber = records.get(i).get(6);
				boardHardwareVer = records.get(i).get(5);
				manufacturerData = records.get(i).get(17);
				boardSoftwareVersion = records.get(i).get(10);

				stmtp2 = con.prepareStatement(
						"insert into NODE_BOARD (BOARD_ID,SITEINDEX,CABINETNO,SUBRACKNO,RACKNO,FRAMENO,SLOTNO,SLOTPOS,SUBSLOTNO,MODULENO,BOARDNAME,BOARDTYPE,VENDORUNITFAMILYTYPE,VENDORUNITTYPENUMBER,VENDORNAME,SERIALNUMBER,HARDWAREVERSION,UNITPOSITION,MANUFACTURERDATA,SOFTVER,LOGICVER,BIOSVER,LANVER,MBUSVER,ISSUENUMBER,BOMCODE,MODEL,USERLABEL,NODE_PK,NODE_ATTR_PK,UPDATE_DATE,FILENAME,EXTINFO,APDEVINFO,WORKMODE,STATUS,FROM_TRANS_SOURCE,FROM_TRANS_ID,TO_TRANS_ID,TRANS_TYPE,ACTIVE_RECORD,LINE,ALM_POSITION,CREATION_DATE,DOMAIN,VENDOR,TO_TRANS_SOURCE)"
								+ "values('" + boardID + "','" + siteIndex + "','" + cabinetNO + "','" + subrackNO + "','"
								+ rackNO + "','" + frameNO + "','" + slotNO + "','" + slotPos + "','" + subslotNO + "','"
								+ moduleNO + "','" + boardName + "','" + boardType + "','" + vendorFamType + "','"
								+ vendorUnitTypeNO + "','" + vendorName + "','" + serialNumber + "','" + boardHardwareVer
								+ "','" + unitPosition + "','" + manufacturerData + "','" + boardSoftwareVersion + "','"
								+ boardLogicVersion + "','" + boardBiosVersion + "','" + boardLanVer + "','" + boardMbusVer
								+ "','" + isSueNumber + "','" + boardBomCode + "','" + model + "','" + userLabel + "',"
								+ "(select NODE_PK from NODE_ACTIVE where NODE_NAME='" +  records.get(i).get(0)+ "' order by creation_date desc fetch first 1 row only),"
								+ "'" + MISSING + "',sysdate," + " '" + fileName + "','" + fileType + "','"
								+ ApdevInfo + "' , '" + workMode + "' , '" + boardStatus + "' , '" + fromTransSrc + "' , '"
								+ fromTransID + "' , '" + toTransID + "' , '" + transType + "' , '" + activeRecord + "' ,'"
								+ line + "' , '" + almPosition + "', sysdate , '" + Domain + "' , '" + provider + "', '"
								+ toTransType + "')");
				stmtp2.executeUpdate();
				stmtp2.close();

				NodeBoardSeq++;
			}
			

		}
		

		stmtp = conalm.prepareStatement(
				"UPDATE SEQ_TABLE SET NODE_ACTIVE = '" + NodeSeq + "' , NODE_BOARD = '" + NodeBoardSeq + "', NODE_LCELL = '" + CellSeq + "' ");
		stmtp.executeUpdate();
		stmtp.close();

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
