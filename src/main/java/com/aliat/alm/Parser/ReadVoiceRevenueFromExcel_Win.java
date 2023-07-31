package com.aliat.alm.Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

public class ReadVoiceRevenueFromExcel_Win {

	static Connection con;
	static String Gyear;
	static Logger logger;
	static FileHandler fh;
	static BufferedReader objReader1 = null;
	static String strCurrentLine1;
	static String logpath;
	static String db1path;
	static String username1;
	static String password1;
	static String vreadexcel;
	static String siteID;
	static String revenueDate;
	static double dataRevenue;
	static double voiceRevenue;

	private static Transaction txRPT = null;
	private static Session sessionRPT = null;
	private static Query query = null;

	public static void main(String[] args) {

		try {

			objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + "almconfig.dat"));
			while ((strCurrentLine1 = objReader1.readLine()) != null) {
				String data = strCurrentLine1;
				String[] data1;
				if (data.contains("logpath")) {
					data1 = data.split(";", -1);
					logpath = data1[1];
					// System.out.println("logpath found :" + logpath);
				}
				if (data.contains("db3path")) {
					data1 = data.split(";", -1);
					db1path = data1[1];
					username1 = data1[2];
					password1 = data1[3];
					// System.out.println("db1path found :" + db1path);
					// System.out.println("username1 found :" + username1);
					// System.out.println("password1 found :" + password1);
				}
				if (data.contains("readexcel")) {
					data1 = data.split(";", -1);
					vreadexcel = data1[1];
					// System.out.println("read excel from :" + vreadexcel);
				}

			}
			objReader1.close();

			/*
			 * // Connect to almrpt DB String dbURL = db1path; String username = username1;
			 * String password = password1; // System.out.println("db1path found :" +
			 * dbURL); // System.out.println("username1 found :" + username); //
			 * System.out.println("password1 found :" + password);
			 */

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
			Gyear = dtf.format(now).substring(0, 4);

			

			File f = new File("C:\\ALM2\\ALM\\src\\main\\resources\\almrpt.cfg.xml");
			Configuration cfg = new Configuration().configure(f);
			// Configuration cfg = new
			// Configuration().configure("C:/ALM2/ALM/src/main/resources/almrpt.cfg.xml");
			StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
					.applySettings(cfg.getProperties());
			SessionFactory sf = cfg.buildSessionFactory(builder.build());
			sessionRPT = sf.openSession();
			
		
			if(sessionRPT != null && sessionRPT.isOpen()) {
				txRPT=sessionRPT.beginTransaction();
				Read_Insert_VoiceDataRevenue(sessionRPT);
				
			}
			
			
			
				
			
			
			// System.out.println("Get Start and End Date from excel sheet 4");

			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			logger.info("Error : " + e.getMessage());
		}

	}

	private static void Read_Insert_VoiceDataRevenue(Session session) {
		try {

			File file = new File(vreadexcel + "\\" + "224_Traffic_Revenue.xlsx"); // creating a new file instance
			FileInputStream fis = new FileInputStream(file); // obtaining bytes from the file
			// creating Workbook instance that refers to .xlsx file
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(3); // creating a Sheet object to retrieve object
			XSSFSheet sheet1 = wb.getSheetAt(1); // creating a Sheet object to retrieve object

			int rowStart = sheet.getFirstRowNum();
			int rowEnd = sheet.getLastRowNum() + 1;

			int rowStart1 = sheet1.getFirstRowNum();
			int rowEnd1 = sheet1.getLastRowNum() + 1;
			Cell c = null;
			ArrayList<Object> SiteId = new ArrayList<>();
			ArrayList<Object> RevenueDate = new ArrayList<>();
			ArrayList<Object> VoiceRevenue = new ArrayList<>();
			ArrayList<Object> DataRevenue = new ArrayList<>();

			// Read Excel Sheet of Voice Revenue
			for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
				Row r = sheet.getRow(rowNum);

				int lastColumn = (r.getLastCellNum());

				for (int cn = 0; cn < lastColumn; cn++) {
					c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
					if (c == null) {
						// The spreadsheet is empty in this cell
					} else {
						// first column
						if (cn == 0 && rowNum > 0) {
							SiteId.add(c.getStringCellValue().toString());
						}

						// first row
						if (cn > 0 && rowNum == 0) {
							RevenueDate.add(String.valueOf(c.getDateCellValue()));
						}

						// data in between the first row and first column
						if (cn > 0 && rowNum > 0) {
							VoiceRevenue.add(c.getNumericCellValue());
						}
					}
				}

			}

			/// Read Excel Sheet of Data Revenue
			for (int rowNum1 = rowStart1; rowNum1 < rowEnd1; rowNum1++) {
				Row r = sheet.getRow(rowNum1);

				int lastColumn1 = (r.getLastCellNum());

				for (int cn = 0; cn < lastColumn1; cn++) {
					c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
					if (c == null) {
						// The spreadsheet is empty in this cell
					} else {
						// data in between the first row and first column
						if (cn > 0 && rowNum1 > 0) {
							DataRevenue.add(c.getNumericCellValue());
						}
					}
				}

			}


			///// Fill in the Database
			String RDate=null,SiteID = null, globalID = null;//, SiteName, Latitude = null, Longitude = null;
			//char tech_2g = '0', tech_3g = '0', tech_4g = '0', tech_5g = '0';
			double VRevenue = 0, DRevenue = 0;
			int n = 0, m = 0;
			
		
				for (int i = 0; i < SiteId.size(); i++) {
					SiteID = (String) SiteId.get(i);
					m = n;
					n = n + RevenueDate.size();

				/*	ArrayList<String[]> WarehouseData = (ArrayList<String[]>) session.createSQLQuery(
							"SELECT WARE_NAME as SiteName,LATITUDE as Latitude,LONGITUDE as Longitude,TECH_2g as tech_2g,TECH_3G as tech_3g,TECH_4G as tech_4g,TECH_5g as tech_5g from alm_warehouse where SITE_ID='"
									+ SiteID + "'")
							.list();
					ObjectMapper mapper = new ObjectMapper();
					System.out.println(mapper.writeValueAsString(WarehouseData.get(0)));

					SiteName = WarehouseData.get(0).toString();
					Latitude = WarehouseData.get(1).toString();
					Longitude = WarehouseData.get(2).toString();
					tech_2g = WarehouseData.get(3).toString().charAt(0);
					tech_3g = WarehouseData.get(4).toString().charAt(0);
					tech_4g = WarehouseData.get(5).toString().charAt(0);
					tech_5g = WarehouseData.get(6).toString().charAt(0);*/

					for (int j = 0, k = m; k < n && j < RevenueDate.size(); k++, j++) {

						RDate = (String) RevenueDate.get(j);
						VRevenue = (double) VoiceRevenue.get(k);
						DRevenue = (double) DataRevenue.get(k);

						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM d H:mm:ss zzz yyyy",
								Locale.ENGLISH);
						ZonedDateTime parsedDate = ZonedDateTime.parse(RDate, formatter);

						String sequence = session
								.createSQLQuery("select PREPAID_PAYG_REVENUE_SEQ.nextval as nbr from dual")
								.uniqueResult().toString();

						globalID = Gyear + "_SITE_REV_" + sequence;

						query = session.createSQLQuery(
								"insert into  PREPAID_PAYG_REVENUE(ID,REVENUE_DATE,SITE_ID,VOICE_REVENUE,DATA_REVENUE) "
								+ "values ('" + globalID + "',TO_DATE('"
										+ parsedDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + "','YYYY-MM-DD'),'"
										+ SiteID + "'," + VRevenue + "," + DRevenue + ")");
						query.executeUpdate();

					}

				}
			
		

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("error at ReadDates is :" + e.getCause());
			System.out.println("error at ReadDates is :" + e.getCause());
		}
		
		 finally {
				if (session != null && session.isOpen()) {
					txRPT.commit();
					session.close();
				}
	}

}
}
