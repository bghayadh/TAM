package com.aliat.alm.controller;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Array;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;															  
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.Parser.ManHoleHandHoleImporter;
import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.models.AreaFinance;
import com.aliat.alm.models.DiscoveryNewBoq;
import com.aliat.alm.models.GoodsReceived;
import com.aliat.alm.models.HandHole;
import com.aliat.alm.models.Item;

import com.aliat.alm.models.ItemBarcode;										
import com.aliat.alm.models.ItemCategory;
import com.aliat.alm.models.ManHole;
import com.aliat.alm.models.PurchaseRequest;
import com.aliat.alm.models.UserRole;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.errors.ApiException;

@Controller
public class ImportSettingsController {

	private Session session = null;
	private Transaction tx = null;
	private String queryStatement = null;
	private Query query = null;
	
	
	@Autowired
	ALMSessions almsessions;
	@RequestMapping(value = "/importSettings", method = RequestMethod.GET)
	// @ResponseBody
		public String ImportSettings(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response)
				throws JsonGenerationException, JsonMappingException, IOException {
		
		
		
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		
	
		
		List listTables = session.createSQLQuery("select table_name from user_tables").list();
	    
	    
	    tx.commit();
		session.close();
		
		ObjectMapper mapper = new ObjectMapper();
		
		System.out.println("sadddfs");
		System.out.println("List of Tables *********** " +listTables);
		
		
		model.addAttribute("Liliane_ListTables", mapper.writeValueAsString(listTables));
		
		
		
		return "importSettings";
		
	}
	
	
	
	@RequestMapping(value = "/insertData", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> InsertData(HttpServletRequest request,Model model,@ModelAttribute ItemParameters itemParameters, HttpServletResponse response) throws JsonProcessingException {
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		
		String selectedValue=request.getParameter("selectedValue");
		
		System.out.println("choooosen tableeeeeeee" +selectedValue);
		
		if (itemParameters.getDictParameter() != null) {
			
		
		Session session = almsessions.getSession();
		
		Transaction tx = session.beginTransaction();
		
		String items=request.getParameter("items");
		
		List<String> Columns = Arrays.asList(items.split(","));
		
		System.out.println("sizeeeeeeeeeeee of rowsss" +itemParameters.getDictParameter().size());
		
		System.out.println("sizeeeeeeeeeeee of columns to be sett" +Columns.size());
		//System.out.println("columnnnnnnnnnnnnnnnnnnnn" +items);
		//for (int i = 0; i < itemParameters.getDictParameter().size(); i++) {
		
		
		
	
		String finalColumn=Columns.get(Columns.size()-1)+")";
		
		
		String finalValue=itemParameters.getDictParameter().get(itemParameters.getDictParameter().size()-1)+")";
		
		
		
				for (int k = 0; k < itemParameters.getDictParameter().size(); k++) {
					
					String queryInsert="INSERT INTO "+selectedValue+"(";
					
					for (int i = 0; i < Columns.size(); i++) {
						
						
						//String column=itemParameters.getDictParameterbarcode().get(i).get("value");
						//System.out.println("columnnnnn"+i+"" +Columns.get(i));
						
						if(i==Columns.size()-1) {
							
							queryInsert=queryInsert+finalColumn+" VALUES (";
						}
						else {
							queryInsert=queryInsert+Columns.get(i)+",";
						}
						
					}
					
					
					
					String line=itemParameters.getDictParameter().get(k).toString();
					//System.out.println("*********************888888888   " +line);
					
					//String Row=itemParameters.getDictParameter().get(k).get("value");
					System.out.println("Lineeeeeeeeeeeeeeeeee number"+k+"" +line);
				
					line=line.replaceAll("[{ = }]","");
				
					System.out.println("RRRRRRRRRRRR Lineeeee number"+k+"" +line);
				
					List<String> rowColumns = Arrays.asList(line.split(","));
					//Map<String, String> value=itemParameters.getDictParameter().get(k);
					
					for (int p = 0; p < rowColumns.size(); p++) {
					
					System.out.println("columnnnn"+p+" of row"+k+": " +rowColumns.get(p));
					
					String finalColumnInEachRow=rowColumns.get(rowColumns.size()-1)+"')";
					
					if(p==rowColumns.size()-1) {
						
						queryInsert=queryInsert+"'"+finalColumnInEachRow;
					}
					else {
						queryInsert=queryInsert+"'"+rowColumns.get(p)+"',";
					}
						
					
					
					}
					
					System.out.println("myyyy Query final==>>>"+queryInsert);
				//	}
					//String finalVal=line.get(line.size()-1)+")";
					//queryInsert=queryInsert+finalVal;
					
					Query importData = session.createSQLQuery(queryInsert);
					importData.executeUpdate();
					
				}
				tx.commit();
				session.close();
				
				//queryInsert=queryInsert+finalValue;
			
				
				//INSERT INTO table_name (column1, column2, column3, ...)
			
				//VALUES (value1, value2, value3,
				//		session.createSQLQuery("select table_name from user_tables")
						
				//Query importData = session.createSQLQuery("insert into "+selectedValue+"(USER_ROLE_ID,ROLE_NAME,USER_NAME) values (USER_ROLE_ID.nextval,'"+role.trim()+"','"+un+"')");
//				//			userRole.setRolename(role.trim());
//					//		userRole.setUsername(un);
//			
				
				//		session.saveOrUpdate(userRole);
					//		insertRole.executeUpdate();
							
						
						
						
						
			
		//}
		
	
		}
		
		
		
		return rtn;
	
		
	}
	
	@RequestMapping(value = "/getcsvData", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetCsvData(HttpServletRequest request,Model model,@ModelAttribute ItemParameters itemParameters,HttpServletResponse response) 	throws JsonGenerationException, JsonMappingException, IOException {
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		
		String selectedValue=request.getParameter("selectedValue");
		
		
		//System.out.println("choooosen tableeeeeeee" +selectedValue);
		
		//rtn.put("apply","apply");
		//rtn.put("selectedDb",selectedValue);
		
	
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		
		List fieldsNames = session.createSQLQuery("SELECT COLUMN_NAME FROM ALL_COL_COMMENTS WHERE TABLE_NAME='"+selectedValue+"'").list();
		System.out.println("choooosen fffffiiiiiiieeeeeelllllldddddssss" +fieldsNames);
		
	
	
		tx.commit();
		session.close();
		rtn.put("fieldsNames",fieldsNames);
		
		return rtn;
	
		
}
	@RequestMapping(value = "/getExcelMHandHHData", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getExcelMHandHHData(HttpServletRequest request,Model model,@ModelAttribute ItemParameters itemParameters,HttpServletResponse response) {
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		
		String selectedValue=request.getParameter("selectedValue");
		//System.out.println("selectedValue issss "+selectedValue);
		session = almsessions.getSession();
		tx = session.beginTransaction();
		
		ManHoleHandHoleImporter MHandHH = new ManHoleHandHoleImporter();
		int manholesLastSeqNum=0, jctLastSeqNum=0, handholeLastSeqNum=0;		
		int JctSeq=0;
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		
		try {
			//Get the max jct ID 
			String maxSeq =session.createSQLQuery("SELECT COALESCE(MAX(NUMV),0) FROM (SELECT TO_NUMBER(COALESCE((regexp_replace (JUNCTION_ID, '[^0-9]','')),'0')) AS NUMV FROM JUNCTION ORDER BY TO_NUMBER(COALESCE((regexp_replace (JUNCTION_ID, '[^0-9]','')),'0')) DESC) ").uniqueResult().toString();
		   	if(StringUtils.equalsIgnoreCase(maxSeq,"0")) {
		   		JctSeq=0;
		   	}
		   	else {
				String[] MaxSeq = maxSeq.split("2022"); 
				JctSeq=Integer.parseInt(MaxSeq[1]);
		   	}
		   	
			if(StringUtils.equalsIgnoreCase(selectedValue,"Manhole")) {

				List<ManHole> listManH = null;
				listManH = MHandHH.excelImport();
				System.out.println("listManH is: "+mapper.writeValueAsString(listManH));
				
				for(ManHole manH : listManH) {					

						queryStatement = "insert into MANHOLE(MANHOLE_ID, MANHOLE_NAME, MANHOLE_MODEL, LONGITUDE, LATITUDE, CITY, PROJECT_ID,DM_NAME) VALUES('"+manH.getID().toString()+"','"+manH.getMHName().toString()+"','"+manH.getMHModel()+"','"+manH.getLongitude()+"','"+manH.getLatitude()+"','"+manH.getCity().toString()+"','"+manH.getProjectID().toString()+"','"+manH.getDmName().toString()+"')";
						query = session.createSQLQuery(queryStatement);
						query.executeUpdate();
						
						//Get the sequence from Manhole ID
						String manID = manH.getID();
						String[] manSeq = manID.split("_"); 
						manholesLastSeqNum=Integer.parseInt(manSeq[2]);
								
						
						if(manH.getDmName().contains("J") == true){
								
								JctSeq++;
								String jctID = "JCT_"+year +"_" +JctSeq;
								
								String nameJct = manH.getDmName();

								String[] arrJctName=null ;
								String jctName = null ;
					
							if(manH.getDmName().contains("(") == true){
								 arrJctName = nameJct.split("\\("); 
								 if(manH.getDmName().charAt(0) =='J' ){
									 jctName=nameJct;
								 }
								 else {
								 jctName = arrJctName[1].replaceAll("[)]", "");
								}
							}
							else if(manH.getDmName().contains("-") == true){
								 arrJctName = nameJct.split("\\-"); 
								 jctName = arrJctName[1];
							}
							else {
								jctName=nameJct;
							}

								queryStatement = "insert into JUNCTION(JUNCTION_ID, JUNCTION_NAME,PHYSICAL_LAYER_ID,PHYSICAL_LAYER_NAME, LONGITUDE, LATITUDE,CAPACITY,JUNCTION_NUMBER, CITY, PROJECT_ID) VALUES('"+jctID+"','"+jctName+"','"+manH.getID()+"','"+manH.getMHName().toString()+"','"+manH.getLongitude()+"','"+manH.getLatitude()+"','24','24','"+manH.getCity().toString()+"','"+manH.getProjectID().toString()+"')";
								query = session.createSQLQuery(queryStatement);
								query.executeUpdate();
								
							
							}
						
						}
				
				//Update the start value of manhole seq after import
				query=session.createSQLQuery("alter sequence MANHOLE_SEQ restart start with "+manholesLastSeqNum+" ");
			   	query.executeUpdate();
			   
			   	//Update the start value of junction seq after import
			   	query=session.createSQLQuery("alter sequence JUNCTION_SEQ restart start with "+JctSeq+" ");
			   	query.executeUpdate();
			

			}
			else {

				List<HandHole> listManH = null;
				listManH = MHandHH.excelHImport();
					for(HandHole manH : listManH) {

						queryStatement = "insert into HANDHOLE(HANDHOLE_ID, HANDHOLE_NAME,HANDHOLE_MODEL, LONGITUDE, LATITUDE, CITY, PROJECT_ID,DM_NAME) VALUES('"+manH.getID().toString()+"','"+manH.getHName().toString()+"','"+manH.getHModel()+"','"+manH.getLongitude()+"','"+manH.getLatitude()+"','"+manH.getCity().toString()+"','"+manH.getProjectID().toString()+"','"+manH.getDmName().toString()+"')";
						query = session.createSQLQuery(queryStatement);
						query.executeUpdate();
						
						//Get the sequence from Handhole ID
						String handID = manH.getID();
						String[] handSeq = handID.split("_"); 
						handholeLastSeqNum=Integer.parseInt(handSeq[2]);
						
						
						if(manH.getDmName().contains("J") == true){
							
							JctSeq++;
							String jctID = "JCT_"+year +"_" +JctSeq;
							
								String nameJct = manH.getDmName();

								String[] arrJctName=null ;
								String jctName = null ;
								
								if(manH.getDmName().charAt(0) =='H' ){
									if(manH.getDmName().contains("(") == true){
										 arrJctName = nameJct.split("\\("); 
										 jctName = arrJctName[1].replaceAll("[)]", "");
										}
									
								}
								else if(manH.getDmName().charAt(0) =='J' ){
									if(manH.getDmName().contains("(") == true){
										 arrJctName = nameJct.split("\\("); 
										 jctName = arrJctName[0].replaceAll("[)]", "");
										}
									
									
								}
								

								queryStatement = "insert into JUNCTION(JUNCTION_ID, JUNCTION_NAME,PHYSICAL_LAYER_ID,PHYSICAL_LAYER_NAME,LONGITUDE,LATITUDE,CAPACITY,JUNCTION_NUMBER,CITY, PROJECT_ID) VALUES('"+jctID+"','"+jctName+"','"+manH.getID()+"','"+manH.getHName().toString()+"','"+manH.getLongitude()+"','"+manH.getLatitude()+"','24','24','"+manH.getCity().toString()+"','"+manH.getProjectID().toString()+"')";
								query = session.createSQLQuery(queryStatement);
								query.executeUpdate();
								
							}
						
						}
					
					//Update the start value of manhole seq after import
					query=session.createSQLQuery("alter sequence HANDHOLE_SEQ restart start with "+handholeLastSeqNum+" ");
				   	query.executeUpdate();
				   
					//Update the start value of junction seq after import
					  query=session.createSQLQuery("alter sequence JUNCTION_SEQ restart start with "+JctSeq+" ");
					  query.executeUpdate();
				
			}
			

				//// not needed in this function but already implemented in jsp because of an earlier job .
				@SuppressWarnings("rawtypes")
				List fieldsNames = session.createSQLQuery("SELECT COLUMN_NAME FROM ALL_COL_COMMENTS WHERE TABLE_NAME='"+selectedValue+"'").list();

				rtn.put("fieldsNames",fieldsNames);

		} catch (ApiException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		tx.commit();
		session.close();
		return rtn;
	
		
	}
	@RequestMapping(value = "/insertAuxFiberData", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> insertAuxFiberData(HttpServletRequest request,Model model,@ModelAttribute ItemParameters itemParameters,HttpServletResponse response) 	throws JsonGenerationException, JsonMappingException, IOException {
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		ObjectMapper mapper = new ObjectMapper();		
		session = almsessions.getSession();
		tx = session.beginTransaction();
		
		try {

		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		
		query = session.createSQLQuery(
				"CREATE TABLE FIBER_AUXILIARY_POINTS_TEMP (\"AUXILIARY_ID\" VARCHAR2(100 BYTE) NOT NULL ENABLE," + 
				"	\"FIBER_CABLE_ID\" VARCHAR2(100 BYTE), " + 
				"	\"LONGITUDE\" VARCHAR2(200 BYTE), " + 
				"	\"LATITUDE\" VARCHAR2(200 BYTE)," + 
				"	\"DISTANCE_FROM_SOURCE\" FLOAT(126)," + 
				"	\"AUXILIARY_NAME\" VARCHAR2(200 BYTE)," + 
				"	\"CREATION_DATE\" TIMESTAMP (6)," + 
				"	\"LAST_MODIFIED_DATE\" TIMESTAMP (6)," + 
				"	\"SEQ_SORTING\" NUMBER," + 
				"	\"DRIVING_DISTANCE\" FLOAT, " + 
				"    \"GEO_DISTANCE\" FLOAT, " + 
				"	 PRIMARY KEY (\"AUXILIARY_ID\"))");
		query.executeUpdate();
		
		query = session.createSQLQuery("INSERT INTO FIBER_AUXILIARY_POINTS_TEMP (SELECT * FROM FIBER_AUXILIARY_POINTS)");
		query.executeUpdate();
		
	
		query = session.createSQLQuery("DROP TABLE FIBER_AUXILIARY_POINTS");
		query.executeUpdate();
		
		query = session.createSQLQuery("CREATE TABLE FIBER_AUXILIARY_POINTS " + 
				"(\"AUXILIARY_ID\" VARCHAR2(100 BYTE) NOT NULL ENABLE, " + 
				"\"FIBER_CABLE_ID\" VARCHAR2(100 BYTE), " + 
				"\"LONGITUDE\" VARCHAR2(200 BYTE), " + 
				"\"LATITUDE\" VARCHAR2(200 BYTE), " + 
				"\"DISTANCE_FROM_SOURCE\" FLOAT(126), " + 
				"\"WARE_ID\" VARCHAR2(200 BYTE), " + 
				"\"AUXILIARY_POINT_ID\" VARCHAR2(200 BYTE), " + 
				"\"AUXILIARY_POINT_NAME\" VARCHAR2(200 BYTE), " + 
				"\"CREATION_DATE\" TIMESTAMP (6), " + 
				"\"LAST_MODIFIED_DATE\" TIMESTAMP (6), " + 
				"\"SEQ_SORTING\" NUMBER, " + 
				"\"DRIVING_DISTANCE\" FLOAT, " + 
				"\"GEO_DISTANCE\" FLOAT, " + 
				" PRIMARY KEY (\"AUXILIARY_ID\")) ");
		query.executeUpdate();
		
		List<Object[]> fiberAuxList = session.createSQLQuery(
				"SELECT * FROM FIBER_AUXILIARY_POINTS_TEMP ").list();
		
		
		String wareID="";String AuxPointID="";String AuxPointName="";
		if (fiberAuxList.size() >0) {

			for (Object[] obj : fiberAuxList) {
				
				System.out.println("Enter ");
				if(((String) obj[5]).contains("WARE") == true ) {
					 wareID = ((String) obj[5]).split(":")[0];
					 AuxPointID = ((String) obj[5]).split(":")[2];
					 AuxPointName = ((String) obj[5]).split(":")[1];
				}
				else if( ((String) obj[5]).contains("MH") == true || ((String) obj[5]).contains("HH") == true || ((String) obj[5]).contains("DB") == true) {
					 wareID = "null";
					 AuxPointID = ((String) obj[5]).split(":")[0];
					 AuxPointName = ((String) obj[5]).split(":")[1];
				}
				else  {
					 wareID = "null";
					 AuxPointID = "null";
					 AuxPointName = ((String) obj[5]);
				}
				

				query = session.createSQLQuery("INSERT INTO FIBER_AUXILIARY_POINTS (AUXILIARY_ID, FIBER_CABLE_ID, LONGITUDE,LATITUDE,DISTANCE_FROM_SOURCE,WARE_ID,AUXILIARY_POINT_ID,AUXILIARY_POINT_NAME,CREATION_DATE, "
						+ " LAST_MODIFIED_DATE,SEQ_SORTING,DRIVING_DISTANCE,GEO_DISTANCE) VALUES ("
						+ " '"+obj[0]+"', '"+obj[1]+"','"+obj[2]+"','"+obj[3]+"','"+obj[4]+"','"+wareID+"','"+AuxPointID+"','"+AuxPointName+"',TO_TIMESTAMP('"+obj[6]+"', 'YYYY-MM-DD HH24:MI:SS.FF'),TO_TIMESTAMP('"+obj[7]+"', 'YYYY-MM-DD HH24:MI:SS.FF'),'"+obj[8]+"','"+obj[9]+"','"+obj[10]+"' )");
				query.executeUpdate();

			}
		}
		query = session.createSQLQuery("DROP TABLE FIBER_AUXILIARY_POINTS_TEMP");
		query.executeUpdate();
		
		tx.commit();
		
		System.out.println("Script done" );
	
		}   catch (Exception e) {
        	//logger.info("Error in creating session with the DataBase " +e.getMessage());
        } 
		
		
		session.close();
		return rtn;
	}

	@RequestMapping(value = "/insertFiberCableData", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> insertFiberCableData(HttpServletRequest request,Model model,@ModelAttribute ItemParameters itemParameters,HttpServletResponse response) 	throws JsonGenerationException, JsonMappingException, IOException {
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		ObjectMapper mapper = new ObjectMapper();		
		session = almsessions.getSession();
		tx = session.beginTransaction();
		
		try {

		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();
		
		query = session.createSQLQuery(
				"CREATE TABLE FIBER_CABLES_TEMP (\"FIBER_CABLE_ID\" VARCHAR2(100 BYTE) NOT NULL ENABLE," + 
				"	\"SOURCE\" VARCHAR2(200 BYTE), " + 
				"	\"DESTINATION\" VARCHAR2(200 BYTE), " + 
				"	\"ITEM_CODE\" VARCHAR2(200 BYTE)," + 
				"	\"NUMBER_OF_STRANDS\" NUMBER ," + 
				"	\"NUMBER_OF_TUBES\" NUMBER ," + 
				"	\"LENGTH\" NUMBER," + 
				"	\"CONDUIT_ID\" VARCHAR2(100 BYTE) ," + 
				"	\"CONDUIT_NAME\" VARCHAR2(200 BYTE)," + 
				"	\"SOURCE_LNG\" NUMBER, " + 
				"    \"SOURCE_LAT\" NUMBER, " + 
				"    \"DESTINATION_LNG\" NUMBER, " + 
				"    \"DESTINATION_LAT\" NUMBER, " + 
				"    \"CABLE_MODE\"  VARCHAR2(100 BYTE), " + 
				"    \"FIBER_CABLE_NAME\"  VARCHAR2(200 BYTE), " + 
				"    \"SOURCE_CITY\"  VARCHAR2(50 BYTE), " + 
				"    \"DESTINATION_CITY\"  VARCHAR2(50 BYTE), " + 
				"    \"PROJECT_ID\"  VARCHAR2(100 BYTE) NOT NULL ENABLE, " + 
				"    \"FIBER_TYPE\"  VARCHAR2(100 BYTE), " + 
				"    \"FIBER_DEPLOYMENT\"  VARCHAR2(100 BYTE), " + 
				"    \"FIBER_NETWORK_LEVEL\"  VARCHAR2(100 BYTE), " + 
				"    \"FIBER_OWNER\"  VARCHAR2(100 BYTE), " + 
				"    \"CREATION_DATE\" TIMESTAMP (6), " + 
				"    \"LAST_MODIFIED_DATE\" TIMESTAMP (6), " + 
				"    \"CREATED_BY\" VARCHAR2(100 BYTE), " + 
				"    \"LAST_MODIFIED_BY\" VARCHAR2(100 BYTE), " + 
				"    \"TOTAL_DRIVING_DISTANCE\" NUMBER, " + 
				"    \"DRAWING_TYPE\" VARCHAR2(100 BYTE), " + 
				"    \"TOTAL_GEO_DISTANCE\" NUMBER, " + 				
				"	 PRIMARY KEY (\"FIBER_CABLE_ID\") )");
		query.executeUpdate();
		
		
		query = session.createSQLQuery("INSERT INTO FIBER_CABLES_TEMP (SELECT * FROM FIBER_CABLES)");
		query.executeUpdate();
		
	
		query = session.createSQLQuery("DROP TABLE FIBER_CABLES ");
		query.executeUpdate();
		

		query = session.createSQLQuery(
				"CREATE TABLE FIBER_CABLES ( \"FIBER_CABLE_ID\" VARCHAR2(100 BYTE) NOT NULL ENABLE," + 
				"	\"SOURCE_WARE_ID\" VARCHAR2(200 BYTE), " + 
				"	\"SOURCE_ID\" VARCHAR2(200 BYTE), " + 
				"	\"SOURCE_NAME\" VARCHAR2(200 BYTE), " + 
				"	\"DESTINATION_WARE_ID\" VARCHAR2(200 BYTE), " + 
				"	\"DESTINATION_ID\" VARCHAR2(200 BYTE), " + 
				"	\"DESTINATION_NAME\" VARCHAR2(200 BYTE), " + 
				"	\"ITEM_CODE\" VARCHAR2(200 BYTE)," + 
				"	\"NUMBER_OF_STRANDS\" NUMBER ," + 
				"	\"NUMBER_OF_TUBES\" NUMBER ," + 
				"	\"LENGTH\" NUMBER," + 
				"	\"CONDUIT_ID\" VARCHAR2(100 BYTE) ," + 
				"	\"CONDUIT_NAME\" VARCHAR2(200 BYTE)," + 
				"	\"SOURCE_LNG\" NUMBER, " + 
				"    \"SOURCE_LAT\" NUMBER, " + 
				"    \"DESTINATION_LNG\" NUMBER, " + 
				"    \"DESTINATION_LAT\" NUMBER, " + 
				"    \"CABLE_MODE\"  VARCHAR2(100 BYTE), " + 
				"    \"FIBER_CABLE_NAME\"  VARCHAR2(200 BYTE), " + 
				"    \"SOURCE_CITY\"  VARCHAR2(50 BYTE), " + 
				"    \"DESTINATION_CITY\"  VARCHAR2(50 BYTE), " + 
				"    \"PROJECT_ID\"  VARCHAR2(100 BYTE) NOT NULL ENABLE, " + 
				"    \"FIBER_TYPE\"  VARCHAR2(100 BYTE), " + 
				"    \"FIBER_DEPLOYMENT\"  VARCHAR2(100 BYTE), " + 
				"    \"FIBER_NETWORK_LEVEL\"  VARCHAR2(100 BYTE), " + 
				"    \"FIBER_OWNER\"  VARCHAR2(100 BYTE), " + 
				"    \"CREATION_DATE\" TIMESTAMP (6), " + 
				"    \"LAST_MODIFIED_DATE\" TIMESTAMP (6), " + 
				"    \"CREATED_BY\" VARCHAR2(100 BYTE), " + 
				"    \"LAST_MODIFIED_BY\" VARCHAR2(100 BYTE), " + 
				"    \"TOTAL_DRIVING_DISTANCE\" NUMBER, " + 
				"    \"DRAWING_TYPE\" VARCHAR2(100 BYTE), " + 
				"    \"TOTAL_GEO_DISTANCE\" NUMBER, " + 				
				"	 PRIMARY KEY (\"FIBER_CABLE_ID\"))");
		query.executeUpdate();
		

		List<Object[]> fiberCableList = session.createSQLQuery(
				"SELECT * FROM FIBER_CABLES_TEMP ").list();
		
		String srcWareID="";String dstWareID="";String srcID="";String dstID="";String srcName="";String dstName="";
		if (fiberCableList.size() >0) {

			for (Object[] obj : fiberCableList) {
				

				if(((String) obj[1]).contains("WARE") == true ) {
					srcWareID = ((String) obj[1]).split(":")[0];
					srcID = ((String) obj[1]).split(":")[2];
					srcName = ((String) obj[1]).split(":")[1];
				}
				else if (((String) obj[1]).contains("CLT") == true) {
					srcWareID = "null";
					srcID = ((String) obj[1]).split(":")[0];
					srcName = ((String) obj[1]).split(":")[1]+":"+((String) obj[1]).split(":")[2];
					
				}
				else if( ((String) obj[1]).contains("MH") == true || ((String) obj[1]).contains("HH") == true || ((String) obj[1]).contains("DB") == true || ((String) obj[1]).contains("CLT") == true) {
					srcWareID = "null";
					srcID = ((String) obj[1]).split(":")[0];
					srcName = ((String) obj[1]).split(":")[1];
				}
				else  {
					srcWareID = "null";
					srcID = "null";
					srcName = ((String) obj[1]);
				}
				
				if(((String) obj[2]).contains("WARE") == true ) {
					dstWareID = ((String) obj[2]).split(":")[0];
					dstID = ((String) obj[2]).split(":")[2];
					dstName = ((String) obj[2]).split(":")[1];
				}
				else if (((String) obj[2]).contains("CLT") == true) {
					dstWareID = "null";
					dstID = ((String) obj[2]).split(":")[0];
					dstName = ((String) obj[2]).split(":")[1]+":"+((String) obj[2]).split(":")[2];
					
				}
				else if( ((String) obj[2]).contains("MH") == true || ((String) obj[2]).contains("HH") == true || ((String) obj[2]).contains("DB") == true ) {
					dstWareID = "null";
					dstID = ((String) obj[2]).split(":")[0];
					dstName = ((String) obj[2]).split(":")[1];
				}
				else  {
					dstWareID = "null";
					dstID = "null";
					dstName = ((String) obj[2]);
				}
				
				
				
				query = session.createSQLQuery("INSERT INTO FIBER_CABLES (FIBER_CABLE_ID, SOURCE_WARE_ID, SOURCE_ID,SOURCE_NAME,DESTINATION_WARE_ID,DESTINATION_ID,DESTINATION_NAME,ITEM_CODE,NUMBER_OF_STRANDS,NUMBER_OF_TUBES,LENGTH,CONDUIT_ID,CONDUIT_NAME,SOURCE_LNG,SOURCE_LAT, "
						+ " DESTINATION_LNG,DESTINATION_LAT,CABLE_MODE,FIBER_CABLE_NAME,SOURCE_CITY,DESTINATION_CITY,PROJECT_ID,FIBER_TYPE,FIBER_DEPLOYMENT,FIBER_NETWORK_LEVEL,FIBER_OWNER,CREATION_DATE,LAST_MODIFIED_DATE,CREATED_BY,LAST_MODIFIED_BY,TOTAL_DRIVING_DISTANCE,DRAWING_TYPE,TOTAL_GEO_DISTANCE ) VALUES ( "
						+ " '"+obj[0]+"', '"+srcWareID+"','"+srcID+"','"+srcName+"','"+dstWareID+"','"+dstID+"','"+dstName+"','"+obj[3]+"', '"+obj[4]+"', '"+obj[5]+"','"+obj[6]+"','"+obj[7]+"','"+obj[8]+"', '"+obj[9]+"', '"+obj[10]+"', '"+obj[11]+"', '"+obj[12]+"', '"+obj[13]+"', '"+obj[14]+"', '"+obj[15]+"', '"+obj[16]+"', '"+obj[17]+"', '"+obj[18]+"', '"+obj[19]+"', '"+obj[20]+"', '"+obj[21]+"', "
								+ " TO_TIMESTAMP('"+obj[22]+"', 'YYYY-MM-DD HH24:MI:SS.FF'),TO_TIMESTAMP('"+obj[23]+"', 'YYYY-MM-DD HH24:MI:SS.FF'),'"+obj[24]+"','"+obj[25]+"','"+obj[26]+"' , '"+obj[27]+"' , '"+obj[28]+"' )");
			
				query.executeUpdate();

			}
		}
		query = session.createSQLQuery("DROP TABLE FIBER_CABLES_TEMP");
		query.executeUpdate();
		
		tx.commit();
		
		System.out.println("Script done" );
	
		}   catch (Exception e) {
        	//logger.info("Error in creating session with the DataBase " +e.getMessage());
        } 
		
		
		session.close();
		return rtn;
	}
/*
	@RequestMapping(value = "/getExcelMHandHHData", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getExcelMHandHHData(HttpServletRequest request,Model model,@ModelAttribute ItemParameters itemParameters,HttpServletResponse response) {
		
		Map<String, Object> rtn = new LinkedHashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		
		String selectedValue=request.getParameter("selectedValue");
		session = almsessions.getALMSession();
		tx = session.beginTransaction();
		
		ManHoleHandHoleImporter MHandHH = new ManHoleHandHoleImporter();
		
		//List<ManHole> listManH = null;
		List<HandHole> listManH = null;
		try {
			listManH = MHandHH.excelImport();
			System.out.println("listManH is: "+mapper.writeValueAsString(listManH));
			//for(ManHole manH : listManH) {
			for(HandHole manH : listManH) {
				//queryStatement = "insert into MANHOLE(MANHOLE_ID, MANHOLE_NAME, MANHOLE_MODEL, LONGITUDE, LATITUDE, CITY, PROJECT_ID,DM_NAME) VALUES('"+manH.getID().toString()+"','"+manH.getMHName().toString()+"','"+manH.getMHModel()+"','"+manH.getLongitude()+"','"+manH.getLatitude()+"','"+manH.getCity().toString()+"','"+manH.getProjectID().toString()+"','"+manH.getDmName().toString()+"')";
				queryStatement = "insert into HANDHOLE(HANDHOLE_ID, HANDHOLE_NAME,HANDHOLE_MODEL, LONGITUDE, LATITUDE, CITY, PROJECT_ID,DM_NAME) VALUES('"+manH.getID().toString()+"','"+manH.getHName().toString()+"','"+manH.getHModel()+"','"+manH.getLongitude()+"','"+manH.getLatitude()+"','"+manH.getCity().toString()+"','"+manH.getProjectID().toString()+"','"+manH.getDmName().toString()+"')";
				query = session.createSQLQuery(queryStatement);
				query.executeUpdate();
				
					if(manH.getDmName().contains("J") == true)
					{
						String seq = manH.getID();
						String[] arr = seq.split("_",2); //arr[1]
						
						String jctID = "JCT_"+arr[1];
						String jctName = "JCT_"+manH.getCity()+"_"+arr[1];
						
						//queryStatement = "insert into JUNCTION(JUNCTION_ID, JUNCTION_NAME,PHYSICAL_LAYER_ID,PHYSICAL_LAYER_NAME, LONGITUDE, LATITUDE,NUM_ROWS,NUM_COLUMNS, CITY, PROJECT_ID) VALUES('"+jctID+"','"+jctName+"','"+manH.getID()+"','"+manH.getMHName().toString()+"','"+manH.getLongitude()+"','"+manH.getLatitude()+"','1','3','"+manH.getCity().toString()+"','"+manH.getProjectID().toString()+"')";
						queryStatement = "insert into JUNCTION(JUNCTION_ID, JUNCTION_NAME,PHYSICAL_LAYER_ID,PHYSICAL_LAYER_NAME, LONGITUDE, LATITUDE,NUM_ROWS,NUM_COLUMNS, CITY, PROJECT_ID) VALUES('"+jctID+"','"+jctName+"','"+manH.getID()+"','"+manH.getHName().toString()+"','"+manH.getLongitude()+"','"+manH.getLatitude()+"','1','3','"+manH.getCity().toString()+"','"+manH.getProjectID().toString()+"')";
						query = session.createSQLQuery(queryStatement);
						query.executeUpdate();
						
						for(int i = 1; i<4; i++)
						{
							String type = "";
							if(i == 1)
								type = "Uplink";
							else
								type = "Access";
							
							String jctMapID = "JCT_MAP_"+arr[1];
							queryStatement = "insert into JUNCTION_MAPPING(JCT_ID, ROW_NUMBER,COLUMN_NUMBER,PORT, TYPE, JCT_MAPPING_ID, PHYSICAL_LAYER_ID) VALUES('"+jctID+"','1','"+i+"','P"+i+"','"+type+"','"+jctMapID+"','"+manH.getID()+"')";
							query = session.createSQLQuery(queryStatement);
							query.executeUpdate();
						
						}
					}
				}
				//// not needed in this function but already implemented in jsp because of an earlier job .
				@SuppressWarnings("rawtypes")
				List fieldsNames = session.createSQLQuery("SELECT COLUMN_NAME FROM ALL_COL_COMMENTS WHERE TABLE_NAME='"+selectedValue+"'").list();

				rtn.put("fieldsNames",fieldsNames);

		} catch (ApiException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		tx.commit();
		session.close();
		return rtn;
	
		
	}
	
*/	
	
}
