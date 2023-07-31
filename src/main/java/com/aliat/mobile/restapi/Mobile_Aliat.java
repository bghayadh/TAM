package com.aliat.mobile.restapi;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.aliat.alm.models.Mobile_TT_DCP;
import com.aliat.alm.models.Mobile_TT_LINK_FAILURE;
import com.aliat.alm.models.Mobile_TroubleTickets_Info;
import com.aliat.alm.models.Mobile_Trouble_Tickets_Actions;
import com.aliat.alm.models.Mobile_Trouble_Tickets_Assign;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.models.DisplayShopsAPI;
import com.aliat.alm.models.FindedSitesAPI;
import com.aliat.alm.models.ScanDataAPI;
import com.aliat.alm.models.ScanListViewAPI;
import com.aliat.alm.models.ShopImageAPI;
import com.aliat.alm.models.ShopsAPI;
import com.aliat.alm.models.ShopsListViewAPI;
import com.aliat.alm.models.SitesListViewAPI;
import com.aliat.alm.models.SpeedCoverageTestAPI;
import com.aliat.alm.models.SpeedCoverageTestListViewAPI;
import com.aliat.alm.models.WarehouseAPI;
import com.aliat.alm.models.WarehouseImageAPI;

@RestController

public class Mobile_Aliat {
	
	private static final Logger logger = LoggerFactory.getLogger(Mobile_Aliat.class);
    private static Session session = null;
    private static Transaction tx = null;
    private static Query query=null;
    private static String data = null;
	 
    @Autowired
    ALMSessions almsessions;
	 
	 @RequestMapping(value="/getTestSpeedCoverageCounter", method = RequestMethod.POST)
     @ResponseBody
     public String getTestSpeedCoverageCounter(){
     	
                    
                     String counter=null; 
                 	 session = almsessions.getSession();
                 	 
                 	 if (session != null && session.isOpen()) {
                       	tx = session.beginTransaction();
                       	
                          try {
                              counter = session.createSQLQuery("SELECT count(*) from SPEEDCOVERAGETEST where TO_CHAR(SPEEDCOVERAGE_DATE, 'YYYY-MM-DD')=TO_CHAR(SYSDATE, 'YYYY-MM-DD') ").uniqueResult().toString();
                        
                         }


                      catch (Exception e) {
                       System.out.println("Error in creating session with the getTestSpeedCounter method" +e.getMessage());
                       logger.info("Error in creating session with the DataBase getTestSpeedCoverageCounter: " +e);
                       
                     } 
                          finally
               	 		{
               	 			if (session != null && session.isOpen()) {
               	 				 tx.commit();
               	 				 session.close();
               	 				} 			
               	 		}             
                      } else {
                      	 System.out.println("could not connect to DB in getTestSpeedCounter method" );
                      	 logger.info("could not connect to DB getTestSpeedCoverageCounter: " );
                      }
                      
                     return counter;
                     
                 	 }
	 
	 
	 
	 
	 
	 
	 @RequestMapping(value ="/savingSpeedCoverageTest_Aliat",method = RequestMethod.POST)
     @ResponseBody
     public String savingSpeedCoverageTest(@RequestBody SpeedCoverageTestAPI testingspeedcoverage) {
                     
                
         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {
                     
                     Date date = new Date();
                     Calendar calendar = new GregorianCalendar();
                     calendar.setTime(date);
                     int year = calendar.get(Calendar.YEAR);
                     
                     //String speedCoverageId= "TEST_"+year+"_" + appConfig.getSequenceNbr(42);
                     
                     
                     Timestamp speedCoverageDate =  new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
                                     
                     SpeedCoverageTestAPI SpeedCoverage = new SpeedCoverageTestAPI();
                                     
                     //SpeedCoverage.setSpeedCoverageId(speedCoverageId);
                     SpeedCoverage.setSpeedCoverageDate(speedCoverageDate);
                     SpeedCoverage.setSpeedCoverageLat(testingspeedcoverage.getSpeedCoverageLat().toString());
                     SpeedCoverage.setSpeedCoverageLong(testingspeedcoverage.getSpeedCoverageLong().toString());
                     SpeedCoverage.setCoverageSignal(testingspeedcoverage.getCoverageSignal().toString());
                     SpeedCoverage.setSpeedDown(testingspeedcoverage.getSpeedDown().toString());
                     SpeedCoverage.setSpeedUp(testingspeedcoverage.getSpeedUp().toString());

                     session.saveOrUpdate(SpeedCoverage);
                     
                     
                     data = "Saved";
                     System.out.println(data);
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase savingSpeedCoverageTest" +e.getMessage());
              logger.info("Error in creating session with the DataBase savingSpeedCoverageTest: " +e);
              data="not saved";
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in  savingSpeedCoverageTest" );
             	logger.info("could not connect to DB savingSpeedCoverageTest: " );
             	data="not update";
             }
               return data;       
                     
                     
     }
	 
	 
	 
	 
	 
	 
	 @RequestMapping(value ="/getTestData",method = RequestMethod.POST)
     @ResponseBody
     public List<SpeedCoverageTestListViewAPI> getTestData(@RequestBody SpeedCoverageTestListViewAPI testlist) {
		 
		 List<SpeedCoverageTestListViewAPI> data = new ArrayList<SpeedCoverageTestListViewAPI>();
     	        String saveddata =null;

     	       String date = testlist.getDate(); 
               int vfrom = testlist.getVfrom(); 
               int vto = testlist.getVto();
                     
         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("SELECT * FROM (select ROW_NUMBER() OVER (ORDER BY SPEEDCOVERAGE_DATE DESC) row_num,"
                	+ " SPEEDCOVERAGE_LAT,SPEEDCOVERAGE_LNG,COVERAGE_SIGNAL,SPEED_DOWNLOAD, SPEED_UPLOAD"
                	+ " from SPEEDCOVERAGETEST where TO_DATE(TO_CHAR(SPEEDCOVERAGE_DATE,'DD-MM-YYYY'),'DD-MM-YYYY') =TO_DATE('" + date+ "','DD-MM-YYYY')) T"
                	+ " WHERE row_num >= '" + vfrom + "' AND row_num <='" + vto + "' ").list(); 
  
                	 saveddata = "Saved";
                     System.out.println(saveddata);
                     
                     ObjectMapper mapper = new ObjectMapper();
                     System.out.println("List "+mapper.writeValueAsString(data));
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase getTestData" +e.getMessage());
              logger.info("Error in creating session with the DataBase getTestData: " +e);
              saveddata="not update";
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in getTestData" );
             	logger.info("could not connect to DB getTestData: " );
             	saveddata="not update";
             }
               return data;       
                     
                     
     }
	 
	 
	 
	 
	 
	 
	 @RequestMapping(value ="/getTestListPagination",method = RequestMethod.POST)
     @ResponseBody
     public String getTestListPagination(@RequestBody SpeedCoverageTestListViewAPI testlist){


		 String date = testlist.getDate();
                     String counter=null;
                 	 session = almsessions.getSession();
                 	 
                 	 if (session != null && session.isOpen()) {
                       	tx = session.beginTransaction();
                          try {
                              counter = session.createSQLQuery("SELECT COUNT(*) FROM SPEEDCOVERAGETEST where TO_DATE(TO_CHAR(SPEEDCOVERAGE_DATE,'DD-MM-YYYY'),'DD-MM-YYYY') =TO_DATE('" + date + "','DD-MM-YYYY') ").uniqueResult().toString();
                         }


                      catch (Exception e) {
                       System.out.println("Error in creating session with the getTestListPagination method" +e.getMessage());
                       logger.info("Error in creating session with the DataBase getTestListPagination: " +e);
                       
                     } 
                          finally
               	 		{
               	 			if (session != null && session.isOpen()) {
               	 				 tx.commit();
               	 				 session.close();
               	 				} 			
               	 		}             
                      } else {
                      	 System.out.println("could not connect to DB in getTestListPagination method" );
                      	 logger.info("could not connect to DB getTestListPagination: " );
                      }


                     return counter;
                 	 }
	 
	 
	 
	 @RequestMapping(value ="/getSitesData",method = RequestMethod.POST)
     @ResponseBody
     public List<SitesListViewAPI> getSitesData(@RequestBody SitesListViewAPI sitelist) {
		 
		 List<SitesListViewAPI> data = new ArrayList<SitesListViewAPI>();
     	        String saveddata =null;

               int vfrom = sitelist.getVfrom(); 
               int vto = sitelist.getVto();
                     
         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("SELECT * FROM (select ROW_NUMBER() OVER (ORDER BY WARE_ID) row_num,WARE_ID,SITE_ID, "
            		+ " WARE_NAME,ADDRESS from WAREHOUSE where SITE='1' order by SITE_ID) T WHERE "
            		+ " row_num >= '" + vfrom + "' AND row_num <='" + vto + "' ").list(); 
  
                	 saveddata = "Saved";
                     System.out.println(saveddata);
                     
                     ObjectMapper mapper = new ObjectMapper();
                     System.out.println("List "+mapper.writeValueAsString(data));
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase getSitesData method" +e.getMessage());
              logger.info("Error in creating session with the DataBase getSitesData method: " +e);
              saveddata="not update";
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in  getSitesData method" );
             	logger.info("could not connect to DB getSitesData method: " );
             	saveddata="not update";
             }
               return data;       
                     
                     
     }
	 
	 
	 @RequestMapping(value ="/getSitesListPagination",method = RequestMethod.POST)
     @ResponseBody
     public String getSitesListPagination(@RequestBody SitesListViewAPI sitelist){


                     String counter=null;
                 	 session = almsessions.getSession();
                 	 
                 	 if (session != null && session.isOpen()) {
                       	tx = session.beginTransaction();
                          try {
                              counter = session.createSQLQuery("SELECT COUNT(*) FROM WAREHOUSE where SITE='1' ").uniqueResult().toString();
                         }


                      catch (Exception e) {
                       System.out.println("Error in creating session with the getSitesListPagination method" +e.getMessage());
                       logger.info("Error in creating session with the DataBase getSitesListPagination: " +e);
                       
                     } 
                          finally
               	 		{
               	 			if (session != null && session.isOpen()) {
               	 				 tx.commit();
               	 				 session.close();
               	 				} 			
               	 		}             
                      } else {
                      	 System.out.println("could not connect to DB in getSitesListPagination method" );
                      	 logger.info("could not connect to DB getSitesListPagination: " );
                      }


                     return counter;
                 	 }
	 
	 
	 @RequestMapping(value ="/AutoCompleteSite",method = RequestMethod.POST)
     @ResponseBody
     public List<SitesListViewAPI> AutoCompleteSite(@RequestBody SitesListViewAPI sitelist) {
		 
		 List<SitesListViewAPI> data = new ArrayList<SitesListViewAPI>();
     	        String saveddata =null;

                     
         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("select SITE_ID,WARE_NAME from WAREHOUSE").list(); 
  
                	 saveddata = "Saved";
                     System.out.println(saveddata);
                     
                     ObjectMapper mapper = new ObjectMapper();
                     System.out.println("List "+mapper.writeValueAsString(data));
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase AutoCompleteSite method" +e.getMessage());
              logger.info("Error in creating session with the DataBase AutoCompleteSite method: " +e);
              saveddata="not update";
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in AutoCompleteSite method" );
             	logger.info("could not connect to DB AutoCompleteSite method: " );
             	saveddata="not update";
             }
               return data;       
                     
                     
     }
	 
	 
	 @RequestMapping(value ="/FindSitesData",method = RequestMethod.POST)
     @ResponseBody
     public List<FindedSitesAPI> FindSitesData(@RequestBody FindedSitesAPI find) {
		 
		 List<FindedSitesAPI> data = new ArrayList<FindedSitesAPI>();
     	        String saveddata =null;
                     
     	       String siteid = find.getSiteid().toString();
     	       
     	        
         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("SELECT WARE_ID,SITE_ID,WARE_NAME,ADDRESS from WAREHOUSE where SITE='1' and SITE_ID ='" +siteid+ "' order by SITE_ID ").list(); 
  
                	 saveddata = "Saved";
                     System.out.println(saveddata);

                     ObjectMapper mapper = new ObjectMapper();
                     System.out.println("List "+mapper.writeValueAsString(data));
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase FindSitesData method" +e.getMessage());
              logger.info("Error in creating session with the DataBase FindSitesData method: " +e);
              saveddata="not update";
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in FindSitesData method" );
             	logger.info("could not connect to DB FindSitesData method: " );
             	saveddata="not update";
             }
             
               return data;       
                     
                     
     }
	 
	 
	 @RequestMapping(value ="/DisplayWarehouse",method = RequestMethod.POST)
     @ResponseBody
     public List<FindedSitesAPI> DisplayWarehouse(@RequestBody FindedSitesAPI display) {
		 
		 List<FindedSitesAPI> data = new ArrayList<FindedSitesAPI>();
     	        String saveddata =null;
                     
     	      String wareid = display.getWareid().toString();
     	        
         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("SELECT  * from WAREHOUSE where WARE_ID ='" + wareid +"'  and SITE='1' ").list(); 
  
                	 saveddata = "Saved";
                     System.out.println(saveddata);

                     ObjectMapper mapper = new ObjectMapper();
                     System.out.println("List "+mapper.writeValueAsString(data));
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase DisplayWarehouse method" +e.getMessage());
              logger.info("Error in creating session with the DataBase DisplayWarehouse method: " +e);
              saveddata="not update";
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in DisplayWarehouse method" );
             	logger.info("could not connect to DB DisplayWarehouse method: " );
             	saveddata="not update";
             }
             
               return data;       
                     
                     
     }
	 

	 
	    

     @RequestMapping(value = "/SavingorUpdatingWarehouse", method = RequestMethod.POST)
     @ResponseBody
     public String savingWarehouse(@RequestBody WarehouseAPI warehouse) {

     	
     	String wareid= warehouse.getWareid().toString();
     	
                     data = "not Saved";

                     session = almsessions.getSession();

                     if (session != null && session.isOpen()) {
                                     tx = session.beginTransaction();
                                     try {
                                                     Date date = new Date();
                                                     Calendar calendar = new GregorianCalendar();
                                                     calendar.setTime(date);
                                                     int year = calendar.get(Calendar.YEAR);
                                                     
                                                     if(wareid.equalsIgnoreCase("0")) {
                                                     //	wareid = "WARE_" + year + "_" + appConfig.getSequenceNbr(12);
                                                     } 
                                                     
                                                     System.out.println("WARE ID == " +wareid);
                                                     
                                                     Timestamp CreationDate, LastModified;
                                                     CreationDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
                                                     LastModified = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());

                                                     WarehouseAPI ware = new WarehouseAPI();
                                                     ware.setWareid(wareid);
                                                     ware.setCreatedDate(CreationDate);
                                                     ware.setLastModified(LastModified);
                                                     ware.setWarename(warehouse.getWarename().toString());
                                                     ware.setCity("0");
                                                     ware.setLongitude(warehouse.getLongitude().toString());
                                                     ware.setLattitude(warehouse.getLattitude().toString());
                                                     ware.setSiteid(warehouse.getSiteid().toString());
                                                     ware.setSite(warehouse.getSite().toString());
                                                     ware.setTech2g(warehouse.getTech2g().toString());
                                                     ware.setTech3g(warehouse.getTech3g().toString());
                                                     ware.setTech4g(warehouse.getTech4g().toString());
                                                     ware.setTech5g(warehouse.getTech5g().toString());
                                                     ware.setAreaid("0");
                                                     ware.setAreaname("0");
                                                     ware.setAddress(warehouse.getAddress().toString());
                                                     ware.setClusterid("0");
                                                     ware.setClustername("0");
                                                     ware.setStatus(null);
                                                     session.saveOrUpdate(ware);

                                                     data = "saved";
                                     } catch (Exception e) {
                                                     System.out.println("Error in creating session with the SavingorUpdatingWarehouse method " + e.getMessage());
                                                     logger.info("could not connect to DB in SavingorUpdatingWarehouse method " +e);
                                     }

                                     finally {
                                                     if (session != null && session.isOpen()) {
                                                                     tx.commit();
                                                                     session.close();
                                                     }
                                     }
                     } else {
                                     System.out.println("could not connect to DB in SavingorUpdatingWarehouse method");
                                     logger.info("could not connect to DB in SavingorUpdatingWarehouse method ");
                     }

                     return data;
     }
	 
	 


     @RequestMapping(value = "/deleteWarehouse", method = RequestMethod.POST)
     @ResponseBody
     public String deleteWarehouse(@RequestBody WarehouseAPI warehouse) {

                     data = null;
                     String wareid= warehouse.getWareid().toString();

                     session = almsessions.getSession();

                     if (session != null && session.isOpen()) {
                                     tx = session.beginTransaction();
                                     try {
                                                     query = session.createSQLQuery("delete  WAREHOUSE   where WARE_ID ='" + wareid + "'");
                                                     query.executeUpdate();

                                                     data = "deleted";
                                     } catch (Exception e) {
                                                     data = "not deleted";
                                                     System.out.println("Error in creating session with the deleteWarehouse method " + e.getMessage());
                                                     logger.info("could not connect to DB in deleteWarehouse method " + e);
                                     }

                                     finally {
                                                     if (session != null && session.isOpen()) {
                                                                     tx.commit();
                                                                     session.close();
                                                     }
                                     }
                     } else {
                                     System.out.println("could not connect to DB in deleteWarehouse method");
                                     logger.info("could not connect to DB in deleteWarehouse method ");
                     }

                     return data;
     }
	 




     @RequestMapping(value = "/UploadWarehouseImage", method = RequestMethod.POST)
     @ResponseBody
     public String SavingWarehouseImage(@RequestBody WarehouseImageAPI image) {

     	
     	String wareid= image.getWareid().toString();
     	String pathNbr = image.getPathNbr().toString();
     	
     	
                     data = "not Saved";

                     session = almsessions.getSession();

                     if (session != null && session.isOpen()) {
                                     tx = session.beginTransaction();
                                     try {
                                                     Date date = new Date();
                                                     Calendar calendar = new GregorianCalendar();
                                                     calendar.setTime(date);
                                                     
                                                     //pathNbr= "PATH_"+ appConfig.getSequenceNbr(73);

                                                         Timestamp UploadDate;
                                                         UploadDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());

                                                         WarehouseImageAPI wareimage = new WarehouseImageAPI();
                                                         wareimage.setWareid(wareid);
                                                         wareimage.setPathNbr(pathNbr);
                                                         wareimage.setUploadDate(UploadDate);
                                                         wareimage.setImagePath(image.getImagePath().toString());
                                                         session.saveOrUpdate(wareimage);
                                                     

                                                     
                                                     data = "saved";
                                     } catch (Exception e) {
                                                     System.out.println("Error in creating session with the UploadWarehouseImage method " + e.getMessage());
                                                     logger.info("could not connect to DB in UploadWarehouseImage method " +e);
                                     }

                                     finally {
                                                     if (session != null && session.isOpen()) {
                                                                     tx.commit();
                                                                     session.close();
                                                     }
                                     }
                     } else {
                                     System.out.println("could not connect to DB in UploadWarehouseImage method");
                                     logger.info("could not connect to DB in UploadWarehouseImage method ");
                     }

                     return data;
     }
	 
     
     
     @RequestMapping(value ="/getWarehouseImage",method = RequestMethod.POST)
     @ResponseBody
     public List<WarehouseImageAPI> getWarehouseImage(@RequestBody WarehouseImageAPI wareimage) {
		 
		 List<WarehouseImageAPI> data = new ArrayList<WarehouseImageAPI>();
     	        String saveddata =null;

     	     	String wareid= wareimage.getWareid();

         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("Select IMAGE_PATH FROM WAREHOUSE_IMAGE WHERE WARE_ID='" + wareid + "' ").list(); 
  
                	 saveddata = "Saved";
                     System.out.println(saveddata);
                     
                     ObjectMapper mapper = new ObjectMapper();
                     System.out.println("List "+mapper.writeValueAsString(data));
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase getWarehouseImage" +e.getMessage());
              logger.info("Error in creating session with the DataBase getWarehouseImage: " +e);
              saveddata="not update";
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in getWarehouseImage" );
             	logger.info("could not connect to DB getWarehouseImage: " );
             	saveddata="not update";
             }
               return data;       
                     
                     
     }
     
     

     @RequestMapping(value = "/deleteWarehouseImage", method = RequestMethod.POST)
     @ResponseBody
     public String deleteWarehouseImage(@RequestBody WarehouseImageAPI image) {

                     data = null;
                     String path= image.getImagePath().toString();

                     session = almsessions.getSession();

                     if (session != null && session.isOpen()) {
                                     tx = session.beginTransaction();
                                     try {
                                                     query = session.createSQLQuery("delete  WAREHOUSE_IMAGE  where IMAGE_PATH ='" + path + "'");
                                                     query.executeUpdate();

                                                     data = "deleted";
                                     } catch (Exception e) {
                                                     data = "not deleted";
                                                     System.out.println("Error in creating session with the deleteWarehouseImage method " + e.getMessage());
                                                     logger.info("could not connect to DB in deleteWarehouseImage method " +e);
                                     }

                                     finally {
                                                     if (session != null && session.isOpen()) {
                                                                     tx.commit();
                                                                     session.close();
                                                     }
                                     }
                     } else {
                                     System.out.println("could not connect to DB in deleteWarehouseImage method");
                                     logger.info("could not connect to DB in deleteWarehouseImage method ");
                     }

                     return data;
     }
	 
     
     
     @RequestMapping(value = "/DisplayScanItemData", method = RequestMethod.POST)
     @ResponseBody 
     public List<ScanDataAPI> DisplayScanItemData (@RequestBody String display) {

    	 List<ScanDataAPI> scanAPI = new ArrayList<ScanDataAPI>(); 
         String wareid = null,myfirstdata,myseconddata; 
         int myrownum;
         String[] data,res,res2;
		 
		 display = display.substring(2, Integer.parseInt(String.valueOf(display.length())));
		 StringBuffer sb = new StringBuffer(display);
         sb.deleteCharAt(sb.length() - 1);
         display = sb.toString();
         data = display.split(",");
         myfirstdata = data[0];
         myseconddata = data[1];
         res = myfirstdata.split(":");
         wareid = res[1].replace("\"", "");
         res2 = myseconddata.split(":");
         myrownum = Integer.parseInt(res2[1]);
      
        
  
session = almsessions.getSession();
   if (session != null && session.isOpen()) {
    	tx = session.beginTransaction();
       try {                   
      	 

    scanAPI=session.createSQLQuery("SELECT * FROM (select ROW_NUMBER() OVER (ORDER BY SCAN_ID ASC) row_num,WARE_ID,SCAN_ID,ITEM_NAME,ITEM_CODE,ITEM_MODEL,ITEM_PART_NUMBER, "
    		+ " BARCODE,SERIAL_NUMBER,SERIAL_CHECK,QUANTITY FROM WAREHOUSE_SCAN_ONSITE WHERE WARE_ID ='" + wareid + "') WHERE row_num='" + myrownum + "' ").list(); 
    
           ObjectMapper mapper = new ObjectMapper();
           System.out.println("DisplayScanItemData List "+mapper.writeValueAsString(scanAPI));
           
     } catch (Exception e) {
     System.out.println("Error in creating session with the DataBase DisplayScanItemData" +e.getMessage());
    logger.info("Error in creating session with the DataBase DisplayScanItemData: " +e);
    } 
     finally
	 		{
	 			if (session != null && session.isOpen()) {
	 				 tx.commit();
	 				 session.close();
	 				} 			
	 		}             
   } else {
   	 System.out.println("could not connect to DB in DisplayScanItemData" );
   	logger.info("could not connect to DB DisplayScanItemData: " );
   }

           
        return scanAPI; 
     
        
  }
	 
     
     

     @RequestMapping(value = "/SavingScan", method = RequestMethod.POST)
     @ResponseBody
     public String SavingScan (@RequestBody String save) {

         String scanId = null; 
         String[] result;
         String[] wareIdresult,itemcoderesult,barcoderesult,serialNumberesult,serialCheckresult,quantityresult,processStatusresult;
         String[] userIdresult,userMobileresult,itemModelresult,itemPartNumberresult,scanIdresult,itemNameresult;
         String wareId,itemcode,barcode,serialNumber,serialCheck,quantity,processStatus,userId,userMobile,itemModel,itemPartNumber,itemName;
         
		 
         save = save.substring(2, Integer.parseInt(String.valueOf(save.length())));
		 StringBuffer sb = new StringBuffer(save);
         sb.deleteCharAt(sb.length() - 1);
         save = sb.toString();
         String data1 = String.valueOf(sb).trim();

         data1 = data1.replace("\"", "");
         result = data1.split(",");
         
         wareIdresult = result[0].split(":");
         itemcoderesult = result[1].split(":");
         barcoderesult = result[2].split(":");
         serialNumberesult = result[3].split(":");
         serialCheckresult = result[4].split(":");
         quantityresult = result[5].split(":");
         processStatusresult = result[6].split(":");
         userIdresult = result[7].split(":");
         userMobileresult = result[8].split(":");
         itemModelresult = result[9].split(":");
         itemPartNumberresult = result[10].split(":");
         scanIdresult = result[11].split(":");
         itemNameresult = result[12].split(":");
         
         wareId = wareIdresult[1];
         itemcode = itemcoderesult[1];
         barcode = barcoderesult[1];
         serialNumber = serialNumberesult[1];
         serialCheck = serialCheckresult[1];
         quantity = quantityresult[1]; 
         processStatus = processStatusresult[1]; 
         userId = userIdresult[1]; 
         userMobile = userMobileresult[1]; 
         itemModel = itemModelresult[1]; 
         itemPartNumber = itemPartNumberresult[1]; 
         //scanId = scanIdresult[1]; 
         itemName = itemNameresult[1];
         		 
         data = "not Saved";

         session = almsessions.getSession();

         if (session != null && session.isOpen()) {
                         tx = session.beginTransaction();
                         try {
                                         Date date = new Date();
                                         Calendar calendar = new GregorianCalendar();
                                         calendar.setTime(date);
                                         
                                         
                                         scanId = session.createSQLQuery("Select WAREHOUSE_SCAN_ONSITE_SEQ.nextval as nbr from dual").uniqueResult().toString();
                                         

                                             Timestamp ScanDate,UpdateDate;
                                             ScanDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
                                             UpdateDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());

                                             ScanDataAPI saving = new ScanDataAPI();
                                             saving.setWareId(wareId);
                                             saving.setItemCode(itemcode);
                                             saving.setBarcode(barcode);
                                             saving.setSerialNumber(serialNumber);
                                             saving.setSerialCheck(serialCheck);
                                             saving.setQuantity(Integer.parseInt(quantity));
                                             saving.setProcessStatus(processStatus);
                                             saving.setUserId(userId);
                                             saving.setScanDate(ScanDate);
                                             saving.setUpdateDate(UpdateDate);
                                             saving.setUserMobPhone(userMobile);
                                             saving.setItemModel(itemModel);
                                             saving.setItemPartNumber(itemPartNumber);
                                             saving.setScanId(Integer.parseInt(scanId));
                                             saving.setItemNAME(itemName);
                                             session.saveOrUpdate(saving);
                                         

                                             
                                         
                                         data = "saved";
                         } catch (Exception e) {
                                         System.out.println("Error in creating session with the SavingScan method " + e.getMessage());
                                         logger.info("could not connect to DB in SavingScan method " +e);
                         }

                         finally {
                                         if (session != null && session.isOpen()) {
                                                         tx.commit();
                                                         session.close();
                                         }
                         }
         } else {
                         System.out.println("could not connect to DB in SavingScan method");
                         logger.info("could not connect to DB in SavingScan method ");
         }

         return data;
     }

     

     @RequestMapping(value = "/deleteScan", method = RequestMethod.POST)
     @ResponseBody 
     public String deleteScan (@RequestBody String delete) {

    	 List<ScanDataAPI> scanAPI = new ArrayList<ScanDataAPI>(); 
    	 String ScanId = null,wareid=null,myfirstdata,myseconddata;
         int myrownum;
         data = null;
         String[] result,res,res2;

         delete = delete.substring(1, Integer.parseInt(String.valueOf(delete.length())));
         StringBuffer sb = new StringBuffer(delete);
         sb.deleteCharAt(sb.length() - 1);
         delete = sb.toString();
         result = delete.split(",");
         myfirstdata = result[0];
         myseconddata = result[1];
         res = myfirstdata.split(":");
         wareid = res[1].replace("\"", "");
         res2 = myseconddata.split(":");
         myrownum = Integer.parseInt(res2[1]);


         session = almsessions.getSession();

         if (session != null && session.isOpen()) {
                         tx = session.beginTransaction();
                         try {
                        	 
                        	 scanAPI = session.createSQLQuery("SELECT * FROM (select ROW_NUMBER() OVER (ORDER BY SCAN_ID ASC) row_num,SCAN_ID FROM "
                        			+ " WAREHOUSE_SCAN_ONSITE WHERE WARE_ID='" + wareid +"') WHERE row_num='" + myrownum +"' ").list();
                    
                        	 ObjectMapper mapper = new ObjectMapper();
                        	String listresult = mapper.writeValueAsString(scanAPI); 
                        	listresult = listresult.substring(2, Integer.parseInt(String.valueOf(listresult.length())));
                            StringBuffer listsb = new StringBuffer(listresult);
                            listsb.deleteCharAt(listsb.length() - 1);
                            listsb.deleteCharAt(listsb.length() - 1);
                            listresult = listsb.toString();
                            String [] listres = listresult.split(",");
                            ScanId = listres[1];

                    
                        	
                                         query = session.createSQLQuery("delete from warehouse_scan_onsite where SCAN_ID ='" + ScanId + "'");
                                         query.executeUpdate();

                                         data = "deleted";
                         } catch (Exception e) {
                                         data = "not deleted";
                                         System.out.println("Error in creating session with the deleteScan method " + e.getMessage());
                                         logger.info("could not connect to DB in deleteScan method " +e);
                         }

                         finally {
                                         if (session != null && session.isOpen()) {
                                                         tx.commit();
                                                         session.close();
                                         }
                         }
         } else {
                         System.out.println("could not connect to DB in deleteScan method");
                         logger.info("could not connect to DB in deleteScan method ");
         }

         return data;
     }
     
	 




	 
	 @RequestMapping(value ="/getScanPagination",method = RequestMethod.POST)
     @ResponseBody
     public String getScanPagination(@RequestBody String count){

		 String counter=null;
         String wareid = null; 
         String[] data;
		 String data1;

		 count = count.substring(2, Integer.parseInt(String.valueOf(count.length())));
         StringBuffer sb = new StringBuffer(count);
         sb.deleteCharAt(sb.length() - 1);
         sb.deleteCharAt(sb.length() - 1);
         count = sb.toString();
         
         data=count.split(":");
         data1=data[1]; 
         wareid = data1.substring(1);
         

                     
                 	 session = almsessions.getSession();
                 	 
                 	 if (session != null && session.isOpen()) {
                       	tx = session.beginTransaction();
                          try {
                              counter = session.createSQLQuery("SELECT COUNT(*) FROM WAREHOUSE_SCAN_ONSITE WHERE WARE_ID='" + wareid + "' ").uniqueResult().toString();
                         }


                      catch (Exception e) {
                       System.out.println("Error in creating session with the getScanPagination method" +e.getMessage());
                       logger.info("Error in creating session with the DataBase getScanPagination: " +e);
                       
                     } 
                          finally
               	 		{
               	 			if (session != null && session.isOpen()) {
               	 				 tx.commit();
               	 				 session.close();
               	 				} 			
               	 		}             
                      } else {
                      	 System.out.println("could not connect to DB in getScanPagination method" );
                      	 logger.info("could not connect to DB getScanPagination: " );
                      }


                     return counter;
                 	 }

	 



     @RequestMapping(value = "/getScanListData", method = RequestMethod.POST)
     @ResponseBody
     public List<ScanDataAPI> getScanListData (@RequestBody String scanlist) {

    	 List<ScanDataAPI> scanAPI = new ArrayList<ScanDataAPI>(); 
         String wareid = null,scanId = null; 
         String[] data,wareidresult,vfromresult,vtoresult;
         String VF,VT;

	     scanlist = scanlist.substring(2, Integer.parseInt(String.valueOf(scanlist.length())));
	     StringBuffer sb = new StringBuffer(scanlist);
         sb.deleteCharAt(sb.length() - 1);
         scanlist = sb.toString();

         
         data=scanlist.split(",");
         wareidresult = data[0].split(":");
         vfromresult = data[1].split(":");
         vtoresult = data[2].split(":");

         wareid = wareidresult[1].replace("\"", "");
         VF = vfromresult[1];
         VT = vtoresult[1];
         
         int vfrom = Integer.valueOf(VF);
         int vto = Integer.valueOf(VT);



	     	

session = almsessions.getSession();
   if (session != null && session.isOpen()) {
    	tx = session.beginTransaction();
       try {                   
      	 

    scanAPI=session.createSQLQuery("SELECT * FROM (select ROW_NUMBER() OVER (ORDER BY SCAN_ID DESC) row_num,WARE_ID,ITEM_CODE,BARCODE,SERIAL_NUMBER, " 
    		                        + " QUANTITY FROM WAREHOUSE_SCAN_ONSITE WHERE  WARE_ID ='" + wareid + "') T WHERE  " 
    		                        + " row_num >= '" + vfrom + "' AND row_num <='" + vto + "' ").list(); 
    
           ObjectMapper mapper = new ObjectMapper();
           System.out.println("List "+mapper.writeValueAsString(scanAPI));
           
     } catch (Exception e) {
     System.out.println("Error in creating session with the DataBase getScanListData" +e.getMessage());
    logger.info("Error in creating session with the DataBase getScanListData: " +e);
    } 
     finally
	 		{
	 			if (session != null && session.isOpen()) {
	 				 tx.commit();
	 				 session.close();
	 				} 			
	 		}             
   } else {
   	 System.out.println("could not connect to DB in getScanListData" );
   	logger.info("could not connect to DB getScanListData: " );
   }
     return scanAPI; 
         
     }
     

     

	 
	 @RequestMapping(value ="/getScanListPagination",method = RequestMethod.POST)
     @ResponseBody
     public String getScanListPagination(@RequestBody String count){

		 String counter=null;
         String wareid = null; 
         String[] data;
		 String data1;

		 count = count.substring(2, Integer.parseInt(String.valueOf(count.length())));
         StringBuffer sb = new StringBuffer(count);
         sb.deleteCharAt(sb.length() - 1);
         sb.deleteCharAt(sb.length() - 1);
         count = sb.toString();
         
         data=count.split(":");
         data1=data[1]; 
         wareid = data1.substring(1);
         

                     
                 	 session = almsessions.getSession();
                 	 
                 	 if (session != null && session.isOpen()) {
                       	tx = session.beginTransaction();
                          try {
                              counter = session.createSQLQuery("SELECT COUNT(*) FROM WAREHOUSE_SCAN_ONSITE WHERE WARE_ID='" + wareid + "' ").uniqueResult().toString();
                         }


                      catch (Exception e) {
                       System.out.println("Error in creating session with the getScanListPagination method" +e.getMessage());
                       logger.info("Error in creating session with the DataBase getScanListPagination: " +e);
                       
                     } 
                          finally
               	 		{
               	 			if (session != null && session.isOpen()) {
               	 				 tx.commit();
               	 				 session.close();
               	 				} 			
               	 		}             
                      } else {
                      	 System.out.println("could not connect to DB in getScanListPagination method" );
                      	 logger.info("could not connect to DB getScanListPagination: " );
                      }


                     return counter;
                 	 }

	 
	 
	 @RequestMapping(value = "/ScanResult", method = RequestMethod.POST)
     @ResponseBody 
     public List<String> ScanResult (@RequestBody String display) {

    	 List<ScanDataAPI> scanAPI1 = new ArrayList<ScanDataAPI>(); 
    	 List<ScanDataAPI> scanAPI2 = new ArrayList<ScanDataAPI>(); 
    	 List<ScanDataAPI> scanAPI3 = new ArrayList<ScanDataAPI>(); 
    	 List<ScanDataAPI> scanAPI4 = new ArrayList<ScanDataAPI>(); 
    	 List<ScanDataAPI> scanAPI5 = new ArrayList<ScanDataAPI>();      
    	 List<ScanDataAPI> scanAPI6 = new ArrayList<ScanDataAPI>();      
    	 ArrayList<String> scanlist=new ArrayList<String>();
    	 
         String scanInput = null; 
         String[] data,ScanResult,myScanResult,firstResult,itemScanResult;
         String ScanSerial,ScanBarcode,ScanItemCode,ScanItemModel,ScanItempartNb,ScanItemName,newScanBarcode;
         String myScanSerial,myScanBarcode,myScanItemCode,myScanItemModel,myScanItempartNb,myScanItemName;
         String itemScanItemCode,itemScanItemModel,itemScanItempartNb,theItemName;
		 
		 display = display.substring(0, Integer.parseInt(String.valueOf(display.length())));
		 StringBuffer sb = new StringBuffer(display);
         sb.deleteCharAt(sb.length() - 1);
         display = sb.toString();
         data = display.split(":");
         scanInput = data[1].replace("\"", "");


         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   

                	 
               scanAPI1=session.createSQLQuery("select serial_number, null  AS BARCODE_NUMBER,ITEM_CODE,item_model,item_partno,item_name FROM  "
               		+ " serial_number where serial_number='"+scanInput+"' OR item_code='"+scanInput+"' OR item_model='"+scanInput+"' "
               		+ " OR item_partno='"+scanInput+"' OR item_name='"+scanInput+"' UNION ALL SELECT  null AS SERIAL_NUMBER,barcode_number,item_code,null "
               		+ " as item_model,null as item_part_number,null as item_name FROM item_barcode where "
               		+ " barcode_number ='"+scanInput+"' OR item_code='"+scanInput+"' ").list(); 

                     ObjectMapper mapper = new ObjectMapper();
                     String result = mapper.writeValueAsString(scanAPI1);
                     if(result.contains("],[")) {
                    	 firstResult = result.split("\"],"); 
                    	 result = firstResult[0]+"]]";
                     }
                     result = result.substring(2, Integer.parseInt(String.valueOf(result.length())));
            		 StringBuffer buffer = new StringBuffer(result);
            		 buffer.deleteCharAt(buffer.length() - 1);
            		 buffer.deleteCharAt(buffer.length() - 1);
                     result = buffer.toString();
                     ScanResult = result.split(",");
                     ScanSerial = ScanResult[0].replace("\"", "");
                     ScanBarcode = ScanResult[1].replace("\"", "");
                     ScanItemCode = ScanResult[2].replace("\"", "");
                     ScanItemModel = ScanResult[3].replace("\"", "");
                     ScanItempartNb = ScanResult[4].replace("\"", "");
                     ScanItemName = ScanResult[5].replace("\"", "");

                     
                     

                     if (ScanItemCode.equalsIgnoreCase("null")) {
                    	 String totalresult = "Please create an item code";
                    	 scanlist.add(totalresult);
                    	 
                     } else {
                
                     
                     if (ScanBarcode.equalsIgnoreCase("null")) {
                    	 

                         scanAPI2=session.createSQLQuery("select barcode_number FROM item_barcode where  item_code='"+ScanItemCode+"' ").list(); 
                         
                         String newresult = mapper.writeValueAsString(scanAPI2);
                         if(newresult.equalsIgnoreCase("[]")) {
                    		 System.out.println("CASE NO BARCODE TABLE");
                        	 String totalresult = ScanSerial+"="+null+"="+ScanItemCode+"="+ScanItemModel+"="+ScanItempartNb+"="+ScanItemName;
                        	 scanlist.add(totalresult);
                    	 } else {
                    		 newresult = newresult.substring(2, Integer.parseInt(String.valueOf(newresult.length())));
                    		 StringBuffer newbuffer = new StringBuffer(newresult);
                    		 newbuffer.deleteCharAt(newbuffer.length() - 1);
                    		 newbuffer.deleteCharAt(newbuffer.length() - 1);
                    		 newScanBarcode = newbuffer.toString();
                    		 System.out.println("CASE NO BARCODE BUT EXIST BARCODE TABLE");
                        	 String totalresult = ScanSerial+"="+newScanBarcode+"="+ScanItemCode+"="+ScanItemModel+"="+ScanItempartNb+"="+ScanItemName;
                             scanlist.add(totalresult);	 
                        	 }
                         
                    	 }
                         
                     if (ScanSerial.equalsIgnoreCase("null")) {
                    	 
                    	 scanAPI4=session.createSQLQuery("select serial_number, null  AS BARCODE_NUMBER,ITEM_CODE,item_model,item_partno,item_name FROM  "
                         		+ " serial_number where item_code='"+ScanItemCode+"' ").list();
             	 
                         String serialquery = mapper.writeValueAsString(scanAPI4);
                         if(serialquery.equalsIgnoreCase("[]")) {
                    		 System.out.println("CASE NO SEIAL TABLE");
                    		 
                    		 scanAPI5=session.createSQLQuery("select ITEM_CODE,item_model,item_part_number FROM ITEM_MODEL_PARTNUMBER where item_code='"+ScanItemCode+"' ").list();
                    		 String itemresult = mapper.writeValueAsString(scanAPI5);

                    		 itemresult = itemresult.substring(2, Integer.parseInt(String.valueOf(itemresult.length())));
                    		 StringBuffer itembuffer = new StringBuffer(itemresult);
                    		 itembuffer.deleteCharAt(itembuffer.length() - 1);
                    		 itembuffer.deleteCharAt(itembuffer.length() - 1);
                    		 itemresult = itembuffer.toString();
                    		 itemScanResult = itemresult.split(",");
                    		 itemScanItemCode = itemScanResult[0].replace("\"", "");
                    		 itemScanItemModel = itemScanResult[1].replace("\"", "");
                    		 itemScanItempartNb = itemScanResult[2].replace("\"", "");
                    		 
                    		 

                    		 scanAPI6=session.createSQLQuery("select ITEM_NAME FROM ITEM where item_code='"+ScanItemCode+"' ").list();
                    		 String scanAPI6result = mapper.writeValueAsString(scanAPI6);
                    		 scanAPI6result = scanAPI6result.substring(2, Integer.parseInt(String.valueOf(scanAPI6result.length())));
                    		 StringBuffer scanAPI6buffer = new StringBuffer(scanAPI6result);
                    		 scanAPI6buffer.deleteCharAt(scanAPI6buffer.length() - 1);
                    		 scanAPI6buffer.deleteCharAt(scanAPI6buffer.length() - 1);
                    		 theItemName = scanAPI6buffer.toString();
                             
                        	 String totalresult = null+"="+ScanBarcode+"="+ScanItemCode+"="+itemScanItemModel+"="+itemScanItempartNb+"="+theItemName;
                        	 scanlist.add(totalresult);
                        	 
                    	 } else {

                    		 scanAPI3=session.createSQLQuery("select serial_number, null  AS BARCODE_NUMBER,ITEM_CODE,item_model,item_partno,item_name FROM  "
                                		+ " serial_number where item_code='"+ScanItemCode+"' ").list(); 

                    		 String myresult = mapper.writeValueAsString(scanAPI3);
                             myresult = myresult.substring(2, Integer.parseInt(String.valueOf(myresult.length())));
                    		 StringBuffer mybuffer = new StringBuffer(myresult);
                    		 mybuffer.deleteCharAt(mybuffer.length() - 1);
                    		 mybuffer.deleteCharAt(mybuffer.length() - 1);
                    		 myresult = mybuffer.toString();
                             myScanResult = myresult.split(",");
                             myScanSerial = myScanResult[0].replace("\"", "");
                             myScanBarcode = myScanResult[1].replace("\"", "");
                             myScanItemCode = myScanResult[2].replace("\"", "");
                             myScanItemModel = myScanResult[3].replace("\"", "");
                             myScanItempartNb = myScanResult[4].replace("\"", "");
                             myScanItemName = myScanResult[5].replace("\"", "");
                             
                    		 System.out.println("CASE NO SEIAL BUT EXIST SERIAL TABLE");
                        	 String totalresult = myScanSerial+"="+ScanBarcode+"="+myScanItemCode+"="+myScanItemModel+"="+myScanItempartNb+"="+myScanItemName;
                        	 scanlist.add(totalresult);
                    		 
                    	 }
                     }
                     
                	 
                 }
                     
                     System.out.println("List "+mapper.writeValueAsString(scanlist));
                     
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase ScanResult method" +e.getMessage());
              logger.info("Error in creating session with the DataBase ScanResult method: " +e);
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in ScanResult method" );
             	logger.info("could not connect to DB ScanResult method: " );
             }

           
        return scanlist; 
     
        
  }

     

     @RequestMapping(value = "/DeleteItem", method = RequestMethod.POST)
     @ResponseBody 
     public String DeleteItem (@RequestBody String delete) {


         String itemcode = null, serialnb = null; 
         data = null;
         String[] result,Code,Serial;


         delete = delete.substring(1, Integer.parseInt(String.valueOf(delete.length())));
         StringBuffer sb = new StringBuffer(delete);
         sb.deleteCharAt(sb.length() - 1);
         delete = sb.toString();
         result = delete.split(",");
         Code = result[0].split(":");
         itemcode = Code[1].replace("\"", "");
         Serial = result[1].split(":");
         serialnb = Serial[1].replace("\"", "");
         

         session = almsessions.getSession();

         if (session != null && session.isOpen()) {
                         tx = session.beginTransaction();
                         try {
                                         query = session.createSQLQuery("delete from warehouse_scan_onsite where ITEM_CODE ='" + itemcode + "' AND SERIAL_NUMBER ='" + serialnb + "' ");
                                         query.executeUpdate();

                                         data = "deleted";
                         } catch (Exception e) {
                                         data = "not deleted";
                                         System.out.println("Error in creating session with the DeleteItem method " + e.getMessage());
                                         logger.info("could not connect to DB in DeleteItem method " +e);
                         }

                         finally {
                                         if (session != null && session.isOpen()) {
                                                         tx.commit();
                                                         session.close();
                                         }
                         }
         } else {
                         System.out.println("could not connect to DB in DeleteItem method");
                         logger.info("could not connect to DB in DeleteItem method ");
         }

         return data;
     }
     
	 
     

     @RequestMapping(value = "/EditItem", method = RequestMethod.POST)
     @ResponseBody 
     public String EditItem (@RequestBody String edit) {


         String olditemCode = null, oldserialNumber = null,itemCode,serialNumber,barcode,quantity; 
         data = null;
         String[] result,Code,Serial,NewCode,NewSerial,NewBarcode,NewQuantity;

         edit = edit.substring(1, Integer.parseInt(String.valueOf(edit.length())));
         StringBuffer sb = new StringBuffer(edit);
         sb.deleteCharAt(sb.length() - 1);
         edit = sb.toString();
         result = edit.split(",");
         Code = result[0].split(":");
         olditemCode = Code[1].replace("\"", "");
         Serial = result[1].split(":");
         oldserialNumber = Serial[1].replace("\"", "");
         
         NewCode = result[2].split(":");
         itemCode = NewCode[1].replace("\"", "");
         NewSerial = result[3].split(":");
         serialNumber = NewSerial[1].replace("\"", "");
         
         NewBarcode = result[4].split(":");
         barcode = NewBarcode[1].replace("\"", "");
         NewQuantity = result[5].split(":");
         quantity = NewQuantity[1].replace("\"", "");
         

         session = almsessions.getSession();
         if (session != null && session.isOpen()) {
                         tx = session.beginTransaction();
                         try {

                                         query = session.createSQLQuery("update WAREHOUSE_SCAN_ONSITE set ITEM_CODE='" + itemCode + "',BARCODE='" + barcode + "',  "
                                         		+ " SERIAL_NUMBER='" + serialNumber + "',QUANTITY='" + quantity + "' Where "
                                         		+ " ITEM_CODE ='" + olditemCode + "' AND SERIAL_NUMBER ='" + oldserialNumber + "'");
                                         query.executeUpdate();
                                         data = "saved";

                         } catch (Exception e) {
                                         System.out.println("Error in creating session with the EditItem method " + e.getMessage());
                                         logger.info("could not connect to DB in EditItem method " +e);
                         }

                         finally {
                                         if (session != null && session.isOpen()) {
                                                         tx.commit();
                                                         session.close();
                                         }
                         }
         } else {
                         System.out.println("could not connect to DB in EditItem method");
                         logger.info("could not connect to DB in EditItem method ");
         }
         
         return data;
     }
     
     ////////////////////////////////////////////Mohammad Turkieh//////////////////////////////////////////////////////////////////////////////
     //method to get Tickets and fill it in listview
     @RequestMapping(value = "/getTicketsListView", method = RequestMethod.POST)
     @ResponseBody
     public String getTicketsListView(@RequestBody String ticketData) {

                     data = null;
                     
                    
                    String date=null,ticketstatus=null;
                    int vfrom=0,vto=0;
                    String[] data1,data2,data3,data4;
                    
                    ticketData=ticketData.substring(9, Integer.parseInt(String.valueOf(ticketData.length())));
                    data1=ticketData.split(",");
                    vfrom=Integer.parseInt(data1[0]);
                    data2=data1[1].split(":");
                    vto=Integer.parseInt(data2[1]);
                    StringBuffer sb = new StringBuffer(data1[2]);
                    sb.deleteCharAt(sb.length() - 1);
                    data1[2]=sb.toString();
                    data3=data1[2].split("\":\"");
                    date=data3[1];
                    StringBuffer sb1 = new StringBuffer(data1[3]);
                    sb1.deleteCharAt(sb1.length() - 1);
                    sb1.deleteCharAt(sb1.length() - 1);
                    data1[3]=sb1.toString();
                    data4=data1[3].split("\":\"");
                    ticketstatus=data4[1];
                    

                    

                     session = almsessions.getSession();

                     if (session != null && session.isOpen()) {
                                     tx = session.beginTransaction();
                                     try {
                                                     query = session.createSQLQuery("select * from(select ROW_NUMBER() OVER (ORDER BY CREATION_DATE) row_num, TICKET_ID,SITE_ID,SITE_NAME,DEPARTMENT,SUBJECT,STATUS from TROUBLE_TICKETS where TO_DATE(TO_CHAR(CREATION_DATE,'DD-MM-YYYY'),'DD-MM-YYYY') =TO_DATE('"
                                                     + date + "','DD-MM-YYYY') and STATUS='"+ticketstatus+"') T WHERE row_num >= '" + vfrom + "' AND row_num <='" + vto + "'");
                                                     
                                                     List<String> list = (List<String>) query.list();
                                                     ObjectMapper mapper = new ObjectMapper();
                                                     data = mapper.writeValueAsString(list);
                                                     
                                                     System.out.println(data);
                                     } catch (Exception e) {
                                                     
                                                     System.out.println("Error in creating session with the getTickets method " + e.getMessage());
                                                     logger.info("could not connect to DB in getTickets method ");
                                     }

                                     finally {
                                                     if (session != null && session.isOpen()) {
                                                                     tx.commit();
                                                                     session.close();
                                                     }
                                     }
                     } else {
                                     System.out.println("could not connect to DB in getTickets method");
                                     logger.info("could not connect to DB in getTickets method ");
                     }

                     return data;
     }
     
     //get Ticket count
     @RequestMapping(value ="/getTicketsListPagination",method = RequestMethod.POST)
     @ResponseBody
     public String getTicketsListPagination(@RequestBody String ticketspagination){


                     String counter=null,date=null,ticketstatus=null;
                     String[] data1,data2;
                 	 session = almsessions.getSession();
                 	ticketspagination=ticketspagination.substring(9, Integer.parseInt(String.valueOf(ticketspagination.length())));
                	 data1=ticketspagination.split("\",\"");
                	 date=data1[0];
                	 StringBuffer sb = new StringBuffer(data1[1]);
                     sb.deleteCharAt(sb.length() - 1);
                     sb.deleteCharAt(sb.length() - 1);
                     data1[1]=sb.toString();                	
                	 data2=data1[1].split("\":\"");
                	 ticketstatus=data2[1];
                 	 if (session != null && session.isOpen()) {
                       	tx = session.beginTransaction();
                          try {
                              counter = session.createSQLQuery("SELECT COUNT(*) FROM TROUBLE_TICKETS where TO_DATE(TO_CHAR(CREATION_DATE,'DD-MM-YYYY'),'DD-MM-YYYY') =TO_DATE('"+ 
                              date + "','DD-MM-YYYY') and STATUS='"+ticketstatus+"'").uniqueResult().toString();
                           
                         }


                      catch (Exception e) {
                       System.out.println("Error in creating session with the getTicketsListPagination method" +e.getMessage());
                       logger.info("Error in creating session with the DataBase getTicketsListPagination: " +e.getMessage());
                       
                     } 
                          finally
               	 		{
               	 			if (session != null && session.isOpen()) {
               	 				 tx.commit();
               	 				 session.close();
               	 				} 			
               	 		}             
                      } else {
                      	 System.out.println("could not connect to DB in getTicketsListPagination method" );
                      	 logger.info("could not connect to DB getTicketsListPagination: " );
                      }


                     return counter;
                 	 }
	 
	 
	 //client autocomplete 
	 @RequestMapping(value ="/AutoCompleteClient",method = RequestMethod.POST)
     @ResponseBody
     public List<SitesListViewAPI> AutoCompleteClient() {
		 
		 List<SitesListViewAPI> data = new ArrayList<SitesListViewAPI>();
     	 

                     
         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("select CLIENT_ID,DISPLAY_NAME from CLIENTS").list(); 
  
                     ObjectMapper mapper = new ObjectMapper();
                     System.out.println("List "+mapper.writeValueAsString(data));
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase AutoCompleteClient method" +e.getMessage());
              logger.info("Error in creating session with the DataBase AutoCompleteClient method: " +e.getMessage());
              
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in  AutoCompleteClient method" );
             	logger.info("could not connect to DB AutoCompleteClient method: " );
             	
             }
               return data;       
                     
                     
     }
	 
	 //get ticket data
	 @RequestMapping(value ="/getTicketData",method = RequestMethod.POST)
     @ResponseBody
     public List<Mobile_TroubleTickets_Info> getTicketData(@RequestBody String ticketID) {
		 
		 List<Mobile_TroubleTickets_Info> data = new ArrayList<Mobile_TroubleTickets_Info>();
		 String[] data1;
     	 System.out.println(ticketID);
     	 
     	 StringBuffer sb = new StringBuffer(ticketID);
     	sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        
        ticketID=sb.toString();
        data1=ticketID.split("\":\"");
        ticketID=data1[1];
        System.out.println(ticketID);

                     
         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("select DEPARTMENT,SUBJECT,DESCRIPTION,CLIENT_ID,SERVICE,SERVICE_ISSUE,STATUS,SITE_ID,SITE_NAME,REGIONNAME,TO_CHAR(ISSUE_APPEARED,'DD-MM-YYYY') FROM TROUBLE_TICKETS WHERE TICKET_ID='"+ticketID+"'").list(); 
  
                     ObjectMapper mapper = new ObjectMapper();
                     System.out.println("List "+mapper.writeValueAsString(data));
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase getTicketData method" +e.getMessage());
              logger.info("Error in creating session with the DataBase getTicketData method: " +e.getMessage());
              
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in  getTicketData method" );
             	logger.info("could not connect to DB getTicketData method: " );
             	
             }
               return data;       
                     
                     
     }	 
     
     
     //saving or updating tickets
     @RequestMapping(value = "/SavingorUpdatingTicket", method = RequestMethod.POST)
     @ResponseBody
     public String SavingorUpdatingTicket(@RequestBody Mobile_TroubleTickets_Info TT_info) {

     	
     	String ticketId = TT_info.getTicketId().toString();
     	
                     data = "not Saved";
                    session = almsessions.getSession();
                     if (session != null && session.isOpen()) {
                                     tx = session.beginTransaction();
                                     try {
                                    	  Timestamp CreationDate, LastModified;
                                          CreationDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
                                          LastModified = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
                                          Mobile_TroubleTickets_Info TT_Info = new Mobile_TroubleTickets_Info();
                                                     Date date = new Date();
                                                     Calendar calendar = new GregorianCalendar();
                                                     calendar.setTime(date);
                                                     int year = calendar.get(Calendar.YEAR);
                                                     
                                                     
                                                     if(ticketId.equalsIgnoreCase("0")) {
                                                    	 //ticketId =  "TT_"+year+"_" +appConfig.getSequenceNbr(35);
                                                    	
                                                     } 
                                                     TT_Info.setTicketId(ticketId);
                                                     TT_Info.setTkclientId(TT_info.getTkclientId().toString());
                                                     TT_Info.setTkclientName(TT_info.getTkclientName().toString());
                                                     TT_Info.setTkdepartment(TT_info.getTkdepartment().toString());
                                                     TT_Info.setTkdescription(TT_info.getTkdescription().toString());
                                                     TT_Info.setTkcreationDate(CreationDate);
                                                     TT_Info.setTklastModifiedDate(LastModified);
                                                     TT_Info.setTkissueAppeared(TT_info.getTkissueAppeared());
                                                     TT_Info.setTkregionId(TT_info.getTkregionId().toString());
                                                     TT_Info.setTkregionName(TT_info.getTkregionName().toString());
                                                     TT_Info.setTkservice(TT_info.getTkservice().toString());
                                                     TT_Info.setTkserviceIssue(TT_info.getTkserviceIssue().toString());
                                                     TT_Info.setTksiteId(TT_info.getTksiteId().toString());
                                                     TT_Info.setTksiteName(TT_info.getTksiteName().toString());
                                                     TT_Info.setTkstatus(TT_info.getTkstatus().toString());
                                                     TT_Info.setTksubject(TT_info.getTksubject().toString());
                                                     session.saveOrUpdate(TT_Info);
                                                     System.out.println(ticketId);
                                                     data = ticketId;
                                     } catch (Exception e) {
                                                     System.out.println("Error in creating session with the SavingorUpdatingTicket method " + e.getMessage());
                                                     logger.info("could not connect to DB in SavingorUpdatingTicket method ");
                                     }

                                     finally {
                                                     if (session != null && session.isOpen()) {
                                                                     tx.commit();
                                                                     session.close();
                                                     }
                                     }
                     } else {
                                     System.out.println("could not connect to DB in SavingorUpdatingTicket method");
                                     logger.info("could not connect to DB in SavingorUpdatingTicket method ");
                     }

                     return data;
     }
	 
     //get tickets coordinates
	 @RequestMapping(value ="/getTicketCoordinates",method = RequestMethod.POST)
     @ResponseBody
     public List<Mobile_TroubleTickets_Info> getTicketCoordinates(@RequestBody String ticketID) {
		 
		 List<Mobile_TroubleTickets_Info> data = new ArrayList<Mobile_TroubleTickets_Info>();
		 String[] data1;
     	 System.out.println(ticketID);
     	 
     	 StringBuffer sb = new StringBuffer(ticketID);
     	sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        
        ticketID=sb.toString();
        data1=ticketID.split("\":\"");
        ticketID=data1[1];
        System.out.println(ticketID);

                     
         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("select WAREHOUSE.LONGITUDE as longi,WAREHOUSE.LATITUDE as lat from TROUBLE_TICKETS INNER JOIN warehouse ON trouble_tickets.site_id=warehouse.site_id where TICKET_ID='"+ticketID+"'").list(); 
  
                     ObjectMapper mapper = new ObjectMapper();
                     System.out.println("List "+mapper.writeValueAsString(data));
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase getTicketCoordinates method" +e.getMessage());
              logger.info("Error in creating session with the DataBase getTicketCoordinates method: " +e.getMessage());
              
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in  getTicketCoordinates method" );
             	logger.info("could not connect to DB getTicketCoordinates method: " );
             	
             }
               return data;       
             
     }	 
     
     //delete ticket 
     @RequestMapping(value = "/deleteTicket", method = RequestMethod.POST)
     @ResponseBody
     public String deleteWarehouse(@RequestBody String ticketID) {
    	 String[] data1;
     	 System.out.println(ticketID);
     	 
     	 StringBuffer sb = new StringBuffer(ticketID);
     	sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        
        ticketID=sb.toString();
        data1=ticketID.split("\":\"");
        ticketID=data1[1];
        System.out.println(ticketID);
                     data = null;
                     

                     session = almsessions.getSession();

                     if (session != null && session.isOpen()) {
                                     tx = session.beginTransaction();
                                     try {
                                                     query = session.createSQLQuery("Delete TROUBLE_Tickets where Ticket_ID='"+ticketID+"'");
                                                     query.executeUpdate();
                                                     query=session.createSQLQuery("Delete TROUBLE_Tickets_Actions where Ticket_ID='"+ticketID+"'");
                                                     query.executeUpdate();
                                                     query=session.createSQLQuery("Delete TT_ASSIGN where Ticket_ID='"+ticketID+"'");
                                                     query.executeUpdate();
                                                     query=session.createSQLQuery("Delete TT_LINK_FAILURE_PLACES where Ticket_ID='"+ticketID+"'");
                                                     query.executeUpdate();
                                                     query=session.createSQLQuery("Delete TT_DEVICES_CAUSED_PROBLEM where Ticket_ID='"+ticketID+"'");
                                                     query.executeUpdate();
                                                   
                                                     data = "deleted";
                                     } catch (Exception e) {
                                                     data = "not deleted";
                                                     System.out.println("Error in creating session with the deleteTicket method " + e.getMessage());
                                                     logger.info("could not connect to DB in deleteTicket method ");
                                     }

                                     finally {
                                                     if (session != null && session.isOpen()) {
                                                                     tx.commit();
                                                                     session.close();
                                                     }
                                     }
                     } else {
                                     System.out.println("could not connect to DB in deleteTicket method");
                                     logger.info("could not connect to DB in deleteTicket method ");
                     }
                     System.out.println(data);

                     return data;
     }
     
     //get employee
     @RequestMapping(value ="/getAssignEmployees",method = RequestMethod.POST)
     @ResponseBody
     public List<Mobile_Trouble_Tickets_Assign> getAssignEmployees() {
		 
    	 List<Mobile_Trouble_Tickets_Assign> data=new ArrayList<Mobile_Trouble_Tickets_Assign>();
         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("select USER_FIRST_NAME,USER_LAST_NAME from USERS_TABLE").list(); 
  
                     System.out.println("List "+data);
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase getAssignEmployees method" +e.getMessage());
              logger.info("Error in creating session with the DataBase getAssignEmployees method: " +e.getMessage());
              
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in  getAssignEmployees method" );
             	logger.info("could not connect to DB getAssignEmployees method: " );
             	
             }
         
               return data;       
             
     }	
     
     //save or update ticket assignment
     @RequestMapping(value = "/SavingorUpdatingTicketAssignment", method = RequestMethod.POST)
     @ResponseBody
     public String SavingorUpdatingTicketAssignment(@RequestBody Mobile_Trouble_Tickets_Assign TT_assign) {

     	
     	String assignID = TT_assign.getAssignId().toString();
     	
                     data = "not Saved";
                     String assign=null;
                    session = almsessions.getSession();
                     if (session != null && session.isOpen()) {
                                     tx = session.beginTransaction();
                                     try {
                                    	  Timestamp CreationDate, LastModified;
                                          CreationDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
                                          LastModified = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
                                          Mobile_Trouble_Tickets_Assign TT_Assign = new Mobile_Trouble_Tickets_Assign();
                                                     Date date = new Date();
                                                     Calendar calendar = new GregorianCalendar();
                                                     calendar.setTime(date);
                                                     int year = calendar.get(Calendar.YEAR);
                                                    
                                                     
                                                     if(assignID.equalsIgnoreCase("0")) {
                                                    	 //assignID =  "TT_ASSIGN_"+year+"_" +appConfig.getSequenceNbr(41);
                                                    	
                                                     } 
                                                     TT_Assign.setAssignId(assignID);
                                                     TT_Assign.setAssignBy(TT_assign.getAssignBy().toString());
                                                     TT_Assign.setAssignTo(TT_assign.getAssignTo().toString());;
                                                     TT_Assign.setCreateDate(CreationDate);
                                                     TT_Assign.setLastModifiedDate(LastModified);
                                                     TT_Assign.setRequiredAction(TT_assign.getRequiredAction().toString());
                                                     TT_Assign.setTicketId(TT_assign.getTicketId().toString());
                                                     TT_Assign.setAssign_date(TT_assign.getAssign_date());
                                                     session.saveOrUpdate(TT_Assign);
                                                     data = assignID;
                                                     
                                                     assign=session.createSQLQuery("Select COUNT(*) FROM TT_ASSIGN where TICKET_ID='"+TT_assign.getTicketId().toString()+"'" ).uniqueResult().toString();
                                                     if(assign.equalsIgnoreCase("0")) {
                                                     query=session.createSQLQuery("UPDATE TROUBLE_TICKETS set STATUS='Assigned', LAST_MODIFIED_DATE=SYSDATE where TICKET_ID='"+TT_assign.getTicketId().toString()+"'");
                                                     }else{
                                                         query=session.createSQLQuery("UPDATE TROUBLE_TICKETS set STATUS='Re-Assigned', LAST_MODIFIED_DATE=SYSDATE where TICKET_ID='"+TT_assign.getTicketId().toString()+"'");

                                                     }
                                                	 query.executeUpdate();
                                     } catch (Exception e) {
                                                     System.out.println("Error in creating session with the SavingorUpdatingTicketAssignment method " + e.getMessage());
                                                     logger.info("could not connect to DB in SavingorUpdatingTicketAssignment method ");
                                     }

                                     finally {
                                                     if (session != null && session.isOpen()) {
                                                                     tx.commit();
                                                                     session.close();
                                                     }
                                     }
                     } else {
                                     System.out.println("could not connect to DB in SavingorUpdatingTicketAssignment method");
                                     logger.info("could not connect to DB in SavingorUpdatingTicketAssignment method ");
                     }

                     return data;
     }
     
     //get ticket assigned data
     @RequestMapping(value ="/getAssignedData",method = RequestMethod.POST)
     @ResponseBody
     public List<Mobile_Trouble_Tickets_Assign> getAssignedData(@RequestBody String ticketID) {
    	 
    	 List<Mobile_Trouble_Tickets_Assign> data = new ArrayList<Mobile_Trouble_Tickets_Assign>();
    	 String[] data1;
     	 System.out.println(ticketID);
     	 
     	 StringBuffer sb = new StringBuffer(ticketID);
     	sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        
        ticketID=sb.toString();
        data1=ticketID.split("\":\"");
        ticketID=data1[1];
        System.out.println(ticketID);
		// data = null;
	     
         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("select TO_CHAR(ASSIGN_DATE,'DD-MM-YYYY'),ASSIGNTO,ASSIGNBY,ASSIGN_ID,TICKET_ID from TT_ASSIGN where TICKET_ID='"+ticketID+"' ORDER BY ASSIGN_DATE DESC").list(); 
            System.out.println("List "+data);
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase getAssignedData method" +e.getMessage());
              logger.info("Error in creating session with the DataBase getAssignedData method: " +e.getMessage());
              
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in  getAssignedData method" );
             	logger.info("could not connect to DB getAssignedData method: " );
             	
             }
         
               return data;       
             
     }
     
     //save or update ticket actions
     @RequestMapping(value = "/SavingorUpdatingTicketActions", method = RequestMethod.POST)
     @ResponseBody
     public String SavingorUpdatingTicketActions(@RequestBody Mobile_Trouble_Tickets_Actions TT_actions) {

     	
     	String actionID = TT_actions.getActionId().toString();
     	System.out.println(TT_actions.getTicketId().toString());
                     data = "not Saved";
                    session = almsessions.getSession();
                     if (session != null && session.isOpen()) {
                                     tx = session.beginTransaction();
                                     try {
                                    	  Timestamp CreationDate, LastModified;
                                          CreationDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
                                          LastModified = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
                                          Mobile_Trouble_Tickets_Actions TT_Action = new Mobile_Trouble_Tickets_Actions();
                                                     Date date = new Date();
                                                     Calendar calendar = new GregorianCalendar();
                                                     calendar.setTime(date);
                                                     int year = calendar.get(Calendar.YEAR);
                                                   
                                                     if(actionID.equalsIgnoreCase("0")) {
                                                    	 //actionID =  "TT_A_"+year+"_" +appConfig.getSequenceNbr(36);
                                                    	
                                                     } 
                                                     System.out.println(actionID);
                                                     TT_Action.setActionId(actionID);
                                                     TT_Action.setTeam(TT_actions.getTeam().toString());
                                                     TT_Action.setEmployee(TT_actions.getEmployee().toString());
                                                     TT_Action.setCreationDate(CreationDate);
                                                     TT_Action.setLastModifiedDate(LastModified);
                                                     TT_Action.setDescription(TT_actions.getDescription().toString());
                                                     TT_Action.setTicketId(TT_actions.getTicketId().toString());
                                                     TT_Action.setAction(TT_actions.getAction().toString());
                                                     TT_Action.setStatus(TT_actions.getStatus().toString());
                                                     session.saveOrUpdate(TT_Action);
                                                     
                                                     query=session.createSQLQuery("UPDATE TROUBLE_TICKETS set LAST_MODIFIED_DATE=SYSDATE,  STATUS='"+TT_actions.getStatus().toString()+"' where TICKET_ID='"+TT_actions.getTicketId().toString()+"'");
                                                     query.executeUpdate();
                                                     data = actionID;
                                     } catch (Exception e) {
                                                     System.out.println("Error in creating session with the SavingorUpdatingTicketActions method " + e.getMessage());
                                                     logger.info("could not connect to DB in SavingorUpdatingTicketActions method ");
                                     }

                                     finally {
                                                     if (session != null && session.isOpen()) {
                                                                     tx.commit();
                                                                     session.close();
                                                     }
                                     }
                     } else {
                                     System.out.println("could not connect to DB in SavingorUpdatingTicketActions method");
                                     logger.info("could not connect to DB in SavingorUpdatingTicketActions method ");
                     }

                     return data;
     }
     
     //get ticket actions data
     @RequestMapping(value ="/getActionsData",method = RequestMethod.POST)
     @ResponseBody
     public List<Mobile_Trouble_Tickets_Assign> getActionsData(@RequestBody String ticketID) {
    	 
    	 List<Mobile_Trouble_Tickets_Assign> data = new ArrayList<Mobile_Trouble_Tickets_Assign>();
    	 String[] data1;
     	 System.out.println(ticketID);
     	 
     	 StringBuffer sb = new StringBuffer(ticketID);
     	sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        
        ticketID=sb.toString();
        data1=ticketID.split("\":\"");
        ticketID=data1[1];
        System.out.println(ticketID);
		// data = null;
	     
         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("select ACTION_ID,ACTION,EMPLOYEE,STATUS,TEAM,DESCRIPTION,TO_CHAR(CREATION_DATE,'DD-MM-YYYY') FROM TROUBLE_TICKETS_ACTIONS where TICKET_ID='"+ticketID+"' ORDER BY CREATION_DATE DESC").list(); 
            System.out.println("List "+data);
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase getActionsData method" +e.getMessage());
              logger.info("Error in creating session with the DataBase getActionsData method: " +e.getMessage());
              
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in  getActionsData method" );
             	logger.info("could not connect to DB getActionsData method: " );
             	
             }
         
               return data;       
             
     }
     
     //delete action
     @RequestMapping(value ="/DeleteAction",method = RequestMethod.POST)
     @ResponseBody
     public String DeleteAction(@RequestBody String ticketID) {
    	 
    	 
    	 String[] data1;
     	 System.out.println(ticketID);
     	 
     	 StringBuffer sb = new StringBuffer(ticketID);
     	sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        
        ticketID=sb.toString();
        data1=ticketID.split("\":\"");
        ticketID=data1[1];
        System.out.println(ticketID);
		// data = null;
	     
         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	   query = session.createSQLQuery("Delete TROUBLE_TICKETS_ACTIONS where ACTION_ID='"+ticketID+"'");
                       query.executeUpdate();

                       data="deleted";
                     
     } catch (Exception e) {
    	 		data="not deleted";
              System.out.println("Error in creating session with the DataBase getActionsData method" +e.getMessage());
              logger.info("Error in creating session with the DataBase getActionsData method: " +e.getMessage());
              
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in  getActionsData method" );
             	logger.info("could not connect to DB getActionsData method: " );
             	
             }
         
               return data;       
             
     }
     
     //delete action
     @RequestMapping(value ="/DeleteAssignment",method = RequestMethod.POST)
     @ResponseBody
     public String DeleteAssignment(@RequestBody String ticketID) {
    	 
    	 
    	 String[] data1;
     	 System.out.println(ticketID);
     	 
     	 StringBuffer sb = new StringBuffer(ticketID);
     	sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        
        ticketID=sb.toString();
        data1=ticketID.split("\":\"");
        ticketID=data1[1];
        System.out.println(ticketID);
		// data = null;
	     
         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	   query = session.createSQLQuery("Delete TT_ASSIGN where ASSIGN_ID='"+ticketID+"'");
                       query.executeUpdate();

                       data="deleted";
                     
     } catch (Exception e) {
    	 		data="not deleted";
              System.out.println("Error in creating session with the DataBase getActionsData method" +e.getMessage());
              logger.info("Error in creating session with the DataBase getActionsData method: " +e.getMessage());
              
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in  getActionsData method" );
             	logger.info("could not connect to DB getActionsData method: " );
             	
             }
         
               return data;       
             
     }
     
     
     //save reason and resolution
     @RequestMapping(value ="/SavingRCP_RES",method = RequestMethod.POST)
     @ResponseBody
     public String SavingRCP_RES(@RequestBody String RCP_RES) {
    	 System.out.println(RCP_RES);
    	 String ticketID=null,rcp_reason=null,rcp_description=null,res_action=null,res_description=null,res_date=null,globalstatus=null;
         String[] data1,data2,data3,data4,data5,data6,data7,data8;
         Timestamp resolution_date=null;
         
        
         data1=RCP_RES.split(",");
         data2=data1[0].split("\":\"");
         ticketID=data2[1];
         StringBuffer sb = new StringBuffer(ticketID);
         sb.deleteCharAt(sb.length() - 1);
         ticketID=sb.toString();
    	 System.out.println(ticketID);
    	 data3=data1[1].split("\":\"");
    	 rcp_reason=data3[1];
    	 StringBuffer sb1 = new StringBuffer(rcp_reason);
         sb1.deleteCharAt(sb1.length() - 1);
         rcp_reason=sb1.toString();
    	 System.out.println(rcp_reason);
    	 data4=data1[2].split("\":\"");
    	 rcp_description=data4[1];
    	 StringBuffer sb2 = new StringBuffer(rcp_description);
         sb2.deleteCharAt(sb2.length() - 1);
         rcp_description=sb2.toString();
    	 System.out.println(rcp_description);
    	 data5=data1[3].split("\":\"");
    	 res_action=data5[1];
    	 StringBuffer sb3 = new StringBuffer(res_action);
         sb3.deleteCharAt(sb3.length() - 1);
         res_action=sb3.toString();
    	 System.out.println(res_action);
    	 data6=data1[4].split("\":\"");
    	 res_description=data6[1];
    	 StringBuffer sb4 = new StringBuffer(res_description);
         sb4.deleteCharAt(sb4.length() - 1);
         res_description=sb4.toString();
    	 System.out.println(res_description);
    	 data7=data1[5].split("\":\"");
    	 res_date=data7[1];
    	 StringBuffer sb5 = new StringBuffer(res_date);
         sb5.deleteCharAt(sb5.length() - 1);
         res_date=sb5.toString();
      	 System.out.println(res_date);
      	 data8=data1[6].split("\":\"");
      	 globalstatus=data8[1];
      	 StringBuffer sb6 = new StringBuffer(globalstatus);
      	 sb6.deleteCharAt(sb6.length()-1);
      	 sb6.deleteCharAt(sb6.length()-1);
      	 globalstatus=sb6.toString();
      	 System.out.println(globalstatus);
         String inDate = res_date;
         DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
         try {
        	 resolution_date = new Timestamp(((Date)df.parse(inDate)).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
      	 
	     
         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            query=session.createSQLQuery("Update TROUBLE_TICKETS set RESOLUTION_ACTION='"+res_action+"',RESOLUTION_DESCRIPTION='"+res_description+"', "
            		+"REASON_FOR_PROBLEM='"+rcp_reason+"', RCP_DESCRIPTION='"+rcp_description+"', RESOLUTION_DATE='"+res_date+"', LAST_MODIFIED_DATE=SYSDATE, STATUS='"+globalstatus+"' where TICKET_ID='"+ticketID+"'"); 
         	 System.out.println(query);
            query.executeUpdate();
           
           data="updated";
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase SavingRCP_RES method" +e.getMessage());
              logger.info("Error in creating session with the DataBase SavingRCP_RES method: " +e.getMessage());
              data="not updated";
              
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in  SavingRCP_RES method" );
             	logger.info("could not connect to DB SavingRCP_RES method: " );
             	
             }
         
               return data;       
             
     }
     
   //get ticket actions data
     @RequestMapping(value ="/getRCA_RES",method = RequestMethod.POST)
     @ResponseBody
     public List<Mobile_TroubleTickets_Info> getRCA_RES(@RequestBody String ticketID) {
    	 
    	 String[] data1;
     	 System.out.println(ticketID);
     	 
     	 StringBuffer sb = new StringBuffer(ticketID);
     	sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        
        ticketID=sb.toString();
        data1=ticketID.split("\":\"");
        ticketID=data1[1];
        System.out.println(ticketID);
        List<Mobile_TroubleTickets_Info> data = new ArrayList<Mobile_TroubleTickets_Info>();   
        
        session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("select RESOLUTION_ACTION,RESOLUTION_DATE,RESOLUTION_DESCRIPTION,REASON_FOR_PROBLEM,RCP_DESCRIPTION FROM TROUBLE_TICKETS where TICKET_ID='"+ticketID+"' AND STATUS='Completed'").list(); 
            System.out.println("List1111 "+data);
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase getActionsData method" +e.getMessage());
              logger.info("Error in creating session with the DataBase getActionsData method: " +e.getMessage());
              
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in  getActionsData method" );
             	logger.info("could not connect to DB getActionsData method: " );
             	
             }
         
               return data;       
             
     }
     
     
     @RequestMapping(value ="/getNode",method = RequestMethod.POST)
     @ResponseBody
     public List<Mobile_Trouble_Tickets_Assign> getNode(@RequestBody String ticketID) {
    	 String[] data1;
     	 System.out.println(ticketID);
     	 
     	 StringBuffer sb = new StringBuffer(ticketID);
     	sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        
        ticketID=sb.toString();
        data1=ticketID.split("\":\"");
        ticketID=data1[1];
        System.out.println("site ID : "+ticketID);
    	 List<Mobile_Trouble_Tickets_Assign> data=new ArrayList<Mobile_Trouble_Tickets_Assign>();
         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("select NODE_PK,NODE_ID,NODE_NAME from NODE_ACTIVE where SITE_ID='"+ticketID+"'").list(); 
  
            ObjectMapper mapper = new ObjectMapper();
            System.out.println("List Nodesss ==  "+mapper.writeValueAsString(data));
            
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase getNode method" +e.getMessage());
              logger.info("Error in creating session with the DataBase getNode method: " +e.getMessage());
              
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in  getNode method" );
             	logger.info("could not connect to DB getNode method: " );
             	
             }
         
               return data;       
             
     }	
     
     @RequestMapping(value ="/getLink",method = RequestMethod.POST)
     @ResponseBody
     public List<Mobile_TT_LINK_FAILURE> getLink() {
		 
    	 List<Mobile_TT_LINK_FAILURE> data=new ArrayList<Mobile_TT_LINK_FAILURE>();
         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("Select fiber_cable_id,fiber_cable_name from fiber_cables " + 
            		"union " + 
            		"select TUBID,TUBE_NAME from FIBER_TUBES " + 
            		"union " + 
            		"select strand_id,strand_name from fiber_strands").list(); 
  
                     System.out.println("List Links"+data);
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase getLink method" +e.getMessage());
              logger.info("Error in creating session with the DataBase getLink method: " +e.getMessage());
              
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in  getLink method" );
             	logger.info("could not connect to DB getLink method: " );
             	
             }
         
               return data;       
             
     }	
     
     @RequestMapping(value = "/SavingorUpdatingLinkFailure", method = RequestMethod.POST)
     @ResponseBody
     public String SavingorUpdatingLinkFailure(@RequestBody Mobile_TT_LINK_FAILURE TT_lf) {

     	
     	String LFID = TT_lf.getLinkfailure_id();
     	
     	System.out.println();
     	System.out.println();
     	System.out.println(TT_lf.getLongitude());
     	System.out.println(TT_lf.getLatitude());
     	System.out.println(TT_lf.getReason());
     	System.out.println();
     	System.out.println(); 
     	System.out.println(LFID);


     	
                     data = "not Saved";
                    session = almsessions.getSession();
                     if (session != null && session.isOpen()) {
                                     tx = session.beginTransaction();
                                     try {
                                        
                                    	  Mobile_TT_LINK_FAILURE TT_LF = new Mobile_TT_LINK_FAILURE();
                                                     Date date = new Date();
                                                     Calendar calendar = new GregorianCalendar();
                                                     calendar.setTime(date);
                                                     int year = calendar.get(Calendar.YEAR);
                                                    
                                                     if(LFID.equalsIgnoreCase("0")) {
                                                    	 //LFID =  "TT_LFP_"+year+"_" + appConfig.getSequenceNbr(39);
                                                      } 
                                   
                                                     TT_LF.setLinkfailure_id(LFID);
                                                     TT_LF.setLink_id(TT_lf.getLink_id());
                                                     TT_LF.setLink_name(TT_lf.getLink_name());
                                                     TT_LF.setLongitude(TT_lf.getLongitude().toString());
                                                     TT_LF.setLatitude(TT_lf.getLatitude().toString());
                                                     TT_LF.setReason(TT_lf.getReason().toString());
                                                     TT_LF.setDescription(TT_lf.getDescription());
                                                     TT_LF.setTicketID(TT_lf.getTicketID());
                                                     session.saveOrUpdate(TT_LF);
                                                     data = LFID;
                                     } catch (Exception e) {
                                                     System.out.println("Error in creating session with the SavingorUpdatingLinkFailure method " + e.getMessage());
                                                     logger.info("could not connect to DB in SavingorUpdatingLinkFailure method ");
                                     }

                                     finally {
                                                     if (session != null && session.isOpen()) {
                                                                     tx.commit();
                                                                     session.close();
                                                     }
                                     }
                     } else {
                                     System.out.println("could not connect to DB in SavingorUpdatingLinkFailure method");
                                     logger.info("could not connect to DB in SavingorUpdatingLinkFailure method ");
                     }

                     return data;
     }
	 
     
   //get ticket actions data
     @RequestMapping(value ="/getLFData",method = RequestMethod.POST)
     @ResponseBody
     public List<Mobile_TT_LINK_FAILURE> getLFData(@RequestBody String ticketID) {
    	 
    	 List<Mobile_TT_LINK_FAILURE> data = new ArrayList<Mobile_TT_LINK_FAILURE>();
    	 String[] data1;
     	 System.out.println(ticketID);
     	 
     	 StringBuffer sb = new StringBuffer(ticketID);
     	sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        
        ticketID=sb.toString();
        data1=ticketID.split("\":\"");
        ticketID=data1[1];
        System.out.println(ticketID);
		// data = null;
	     
         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("select * FROM TT_LINK_FAILURE_PLACES where TICKET_ID='"+ticketID+"'").list(); 
            ObjectMapper mapper = new ObjectMapper();
            System.out.println("List LF : "+mapper.writeValueAsString(data));
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase getLFData method" +e.getMessage());
              logger.info("Error in creating session with the DataBase getLFData method: " +e.getMessage());
              
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in  getLFData method" );
             	logger.info("could not connect to DB getLFData method: " );
             	
             }
         
               return data;       
             
     }
     
     //delete action
     @RequestMapping(value ="/DeleteLF",method = RequestMethod.POST)
     @ResponseBody
     public String DeleteLF(@RequestBody String ticketID) {
    	 
    	 
    	 String[] data1;
     	 System.out.println(ticketID);
     	 
     	 StringBuffer sb = new StringBuffer(ticketID);
     	sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        
        ticketID=sb.toString();
        data1=ticketID.split("\":\"");
        ticketID=data1[1];
        System.out.println(ticketID);
		// data = null;
	     
         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	   query = session.createSQLQuery("Delete TT_LINK_FAILURE_PLACES where LFP_ID='"+ticketID+"'");
                       query.executeUpdate();

                       data="deleted";
                     
     } catch (Exception e) {
    	 		data="not deleted";
              System.out.println("Error in creating session with the DataBase getActionsData method" +e.getMessage());
              logger.info("Error in creating session with the DataBase getActionsData method: " +e.getMessage());
              
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in  getActionsData method" );
             	logger.info("could not connect to DB getActionsData method: " );
             	
             }
         
               return data;       
             
     }
     
     
     //cabinet autocomplete
     
     /*@RequestMapping(value ="/getCabinet",method = RequestMethod.POST)
     @ResponseBody
     public List<Mobile_Trouble_Tickets_Assign> getCabinet(@RequestBody String ticketID) {
		 
    	 String[] data1;
     	 System.out.println(ticketID);
     	 
     	 StringBuffer sb = new StringBuffer(ticketID);
     	sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        
        ticketID=sb.toString();
        data1=ticketID.split("\":\"");
        ticketID=data1[1];
        System.out.println(ticketID);
    	 List<Mobile_Trouble_Tickets_Assign> data=new ArrayList<Mobile_Trouble_Tickets_Assign>();
         session = almsessions.getALMSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("Select CABINETNO FROM NODE_CABINET where NODE_PK = (select NODE_PK from NODE_ACTIVE where NODE_ID='"+ticketID+"')").list(); 
  
                     System.out.println("List "+data);
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase getCabinet method" +e.getMessage());
              logger.info("Error in creating session with the DataBase getCabinet method: " +e.getMessage());
              
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in  getCabinet method" );
             	logger.info("could not connect to DB getCabinet method: " );
             	
             }
         
               return data;       
             
     }	
     
     //cabinet autocomplete
     
     @RequestMapping(value ="/getSlot",method = RequestMethod.POST)
     @ResponseBody
     public List<Mobile_Trouble_Tickets_Assign> getSlot(@RequestBody String ticketID) {
    	 String[] data1;
     	 System.out.println(ticketID);
     	 
     	 StringBuffer sb = new StringBuffer(ticketID);
     	sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        
        ticketID=sb.toString();
        data1=ticketID.split("\":\"");
        ticketID=data1[1];
        System.out.println(ticketID);
    	 List<Mobile_Trouble_Tickets_Assign> data=new ArrayList<Mobile_Trouble_Tickets_Assign>();
         session = almsessions.getALMSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("Select SLOTNO FROM NODE_SLOT where NODE_PK = (select NODE_PK from NODE_ACTIVE where NODE_ID='"+ticketID+"')").list(); 
  
                     System.out.println("List "+data);
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase getCabinet method" +e.getMessage());
              logger.info("Error in creating session with the DataBase getCabinet method: " +e.getMessage());
              
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in  getCabinet method" );
             	logger.info("could not connect to DB getCabinet method: " );
             	
             }
         
               return data;       
             
     }	
     
     //cabinet autocomplete
     
     @RequestMapping(value ="/getBoard",method = RequestMethod.POST)
     @ResponseBody
     public List<Mobile_Trouble_Tickets_Assign> getBoard(@RequestBody String ticketID) {
    	 String[] data1,data2,data3,data4;
    	 String node=null,slot=null,cabinet=null;
     	 System.out.println(ticketID);
     	 
     	 data1=ticketID.split(",");
         data2=data1[0].split("\":\"");
         node=data2[1];
         StringBuffer sb = new StringBuffer(node);
         sb.deleteCharAt(sb.length() - 1);
         node=sb.toString();
    	 System.out.println(node);
    	 data3=data1[1].split("\":\"");
    	 cabinet=data3[1];
    	 StringBuffer sb1 = new StringBuffer(cabinet);
         sb1.deleteCharAt(sb1.length() - 1);
         cabinet=sb1.toString();
    	 System.out.println(cabinet);
    	 data4=data1[2].split("\":\"");
    	 slot=data4[1];
      	 StringBuffer sb6 = new StringBuffer(slot);
      	 sb6.deleteCharAt(sb6.length()-1);
      	 sb6.deleteCharAt(sb6.length()-1);
      	 slot=sb6.toString();
      	 System.out.println(slot);
    	 
    	 
    	 List<Mobile_Trouble_Tickets_Assign> data=new ArrayList<Mobile_Trouble_Tickets_Assign>();
    	   session = almsessions.getALMSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("Select BOARDNAME FROM NODE_BOARD where NODE_PK = (select NODE_PK from NODE_ACTIVE where NODE_ID='"+node+"') and CABINETNO='"+cabinet+"' and SLOTNO='"+slot+"'").list(); 
  
            ObjectMapper mapper = new ObjectMapper();
            System.out.println("List board : "+mapper.writeValueAsString(data));
                     
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase getCabinet method" +e.getMessage());
              logger.info("Error in creating session with the DataBase getCabinet method: " +e.getMessage());
              
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in  getCabinet method" );
             	logger.info("could not connect to DB getCabinet method: " );
             	
             }
         
               return data;       
             
     }	
     
     
 //cabinet autocomplete
     
     @RequestMapping(value ="/getAntenna",method = RequestMethod.POST)
     @ResponseBody
     public List<Mobile_Trouble_Tickets_Assign> getAntenna(@RequestBody String ticketID) {
    	 String[] data1;
     	 System.out.println(ticketID);
     	 
     	 StringBuffer sb = new StringBuffer(ticketID);
     	sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        
        ticketID=sb.toString();
        data1=ticketID.split("\":\"");
        ticketID=data1[1];
        System.out.println(ticketID);
    	 List<Mobile_Trouble_Tickets_Assign> data=new ArrayList<Mobile_Trouble_Tickets_Assign>();
         session = almsessions.getALMSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("Select ANTENNADEVICENO FROM NODE_ANTENNA where NODE_PK = (select NODE_PK from NODE_ACTIVE where NODE_ID='"+ticketID+"')").list(); 
  
                     System.out.println("List "+data);
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase getCabinet method" +e.getMessage());
              logger.info("Error in creating session with the DataBase getCabinet method: " +e.getMessage());
              
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in  getCabinet method" );
             	logger.info("could not connect to DB getCabinet method: " );
             	
             }
         
               return data;       
             
     }	*/
     
   //saving or updating tickets
     @RequestMapping(value = "/SavingorUpdatingDCP", method = RequestMethod.POST)
     @ResponseBody
     public String SavingorUpdatingDCP(@RequestBody Mobile_TT_DCP TT_dcp) {

     	
     	String dcpID = TT_dcp.getDcpID().toString();
     	System.out.println(dcpID);
     	System.out.println(TT_dcp.getType());
     	System.out.println(TT_dcp.getSiteid());
     	System.out.println(TT_dcp.getSitename());
     	System.out.println(TT_dcp.getNodeid());
     	System.out.println(TT_dcp.getNodename());
     	System.out.println(TT_dcp.getCabinet());
     	System.out.println(TT_dcp.getSlot());
     	System.out.println(TT_dcp.getBoard());
     	System.out.println(TT_dcp.getAntenna());
     	System.out.println(TT_dcp.getVersion());
     	System.out.println(TT_dcp.getNote());
     	System.out.println(TT_dcp.getTicketID());

                     data = "not Saved";
                    session = almsessions.getSession();
                     if (session != null && session.isOpen()) {
                                     tx = session.beginTransaction();
                                     try {
                                    	  
                                          Mobile_TT_DCP TT_DCP = new Mobile_TT_DCP();
                                                     Date date = new Date();
                                                     Calendar calendar = new GregorianCalendar();
                                                     calendar.setTime(date);
                                                     int year = calendar.get(Calendar.YEAR);
                                                     
                                                     
                                                     if(dcpID.equalsIgnoreCase("0")) {
                                                    	// dcpID =  "TT_DCP_"+year+"_" +appConfig.getSequenceNbr(38);
                                                    	
                                                     } 
                                                     TT_DCP.setDcpID(dcpID);
                                                     TT_DCP.setType(TT_dcp.getType());
                                                     TT_DCP.setSiteid(TT_dcp.getSiteid());
                                                     TT_DCP.setSitename(TT_dcp.getSitename());
                                                     TT_DCP.setNodeid(TT_dcp.getNodeid());
                                                     TT_DCP.setNodename(TT_dcp.getNodename());
                                                     TT_DCP.setCabinet(TT_dcp.getCabinet());
                                                     TT_DCP.setSlot(TT_dcp.getSlot());
                                                     TT_DCP.setBoard(TT_dcp.getBoard());
                                                     TT_DCP.setAntenna(TT_dcp.getAntenna());
                                                     TT_DCP.setVersion(TT_dcp.getVersion());
                                                     TT_DCP.setNote(TT_dcp.getNote());
                                                     TT_DCP.setTicketID(TT_dcp.getTicketID());
                                                     
                                                     session.saveOrUpdate(TT_DCP);
                                                     data = dcpID;
                                     } catch (Exception e) {
                                                     System.out.println("Error in creating session with the SavingorUpdatingDCP method " + e.getMessage());
                                                     logger.info("could not connect to DB in SavingorUpdatingDCP method ");
                                     }

                                     finally {
                                                     if (session != null && session.isOpen()) {
                                                                     tx.commit();
                                                                     session.close();
                                                     }
                                     }
                     } else {
                                     System.out.println("could not connect to DB in SavingorUpdatingDCP method");
                                     logger.info("could not connect to DB in SavingorUpdatingDCP method ");
                     }

                     return data;
     }
     
     @RequestMapping(value ="/getDCPData",method = RequestMethod.POST)
     @ResponseBody
     public List<Mobile_TT_DCP> getDCPData(@RequestBody String ticketID) {
    	 
    	 
    	 List<Mobile_TT_DCP> data = new ArrayList<Mobile_TT_DCP>();
    	 String[] data1;
     	 System.out.println(ticketID);
     	 
     	 StringBuffer sb = new StringBuffer(ticketID);
     	sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        
        ticketID=sb.toString();
        data1=ticketID.split("\":\"");
        ticketID=data1[1];
        System.out.println(ticketID);
		// data = null;
	     
         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("select * FROM TT_DEVICES_CAUSED_PROBLEM where TICKET_ID='"+ticketID+"'").list(); 
            ObjectMapper mapper = new ObjectMapper();
            System.out.println("List LF : "+mapper.writeValueAsString(data));
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase getLFData method" +e.getMessage());
              logger.info("Error in creating session with the DataBase getLFData method: " +e.getMessage());
              
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in  getLFData method" );
             	logger.info("could not connect to DB getLFData method: " );
             	
             }
         
               return data;       
             
     }
     
     
   //delete action
     @RequestMapping(value ="/DeleteDCP",method = RequestMethod.POST)
     @ResponseBody
     public String DeleteDCP(@RequestBody String ticketID) {
    	 
    	 
    	 String[] data1;
     	 System.out.println(ticketID);
     	 
     	 StringBuffer sb = new StringBuffer(ticketID);
     	sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        
        ticketID=sb.toString();
        data1=ticketID.split("\":\"");
        ticketID=data1[1];
        System.out.println(ticketID);
		// data = null;
	     
         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	   query = session.createSQLQuery("Delete TT_DEVICES_CAUSED_PROBLEM where ID='"+ticketID+"'");
                       query.executeUpdate();

                       data="deleted";
                     
     } catch (Exception e) {
    	 		data="not deleted";
              System.out.println("Error in creating session with the DataBase DeleteDCP method" +e.getMessage());
              logger.info("Error in creating session with the DataBase DeleteDCP method: " +e.getMessage());
              
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in  DeleteDCP method" );
             	logger.info("could not connect to DB DeleteDCP method: " );
             	
             }
         
               return data;       
             
     }
/////////////////////////END TICKETS Mohammad Turkieh//////////////////////////////////////////////////////////////////////////////
     
     
     


     
	 @RequestMapping(value ="/getShopsData_Aliat",method = RequestMethod.POST)
     @ResponseBody
     public List<ShopsListViewAPI> getShopsData(@RequestBody ShopsListViewAPI shopslist) {
		 
		 List<ShopsListViewAPI> data = new ArrayList<ShopsListViewAPI>();
     	        String saveddata =null;

               int vfrom = shopslist.getVfrom(); 
               int vto = shopslist.getVto();
                     
         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("SELECT * FROM (select ROW_NUMBER() OVER (ORDER BY SHOPS_ID) row_num,SHOPS_ID,SHOP_NAME,OWNER,ADDRESS,LATITUDE,LONGTITUDE from SHOPS "
            		+ " order by SHOPS_ID) T WHERE row_num >= '" + vfrom +"' AND row_num <='" + vto +"' ").list(); 
  
                	 saveddata = "Saved";
                     System.out.println(saveddata);
                     
                     ObjectMapper mapper = new ObjectMapper();
                     System.out.println("List "+mapper.writeValueAsString(data));
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase getShopsData method" +e.getMessage());
              logger.info("Error in creating session with the DataBase getShopsData method: " +e);
              saveddata="not update";
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in getShopsData method" );
             	logger.info("could not connect to DB getShopsData method: " );
             	saveddata="not update";
             }
               return data;       
                     
                     
     }




	 
	 @RequestMapping(value ="/getShopsListPagination",method = RequestMethod.POST)
     @ResponseBody
     public String getShopsListPagination(@RequestBody ShopsListViewAPI shopslist){


                     String counter=null;
                 	 session = almsessions.getSession();
                 	 
                 	 if (session != null && session.isOpen()) {
                       	tx = session.beginTransaction();
                          try {
                              counter = session.createSQLQuery("SELECT COUNT(*) FROM SHOPS ").uniqueResult().toString();
                         }


                      catch (Exception e) {
                       System.out.println("Error in creating session with the getShopsListPagination method" +e.getMessage());
                       logger.info("Error in creating session with the DataBase getShopsListPagination: " +e);
                       
                     } 
                          finally
               	 		{
               	 			if (session != null && session.isOpen()) {
               	 				 tx.commit();
               	 				 session.close();
               	 				} 			
               	 		}             
                      } else {
                      	 System.out.println("could not connect to DB in getShopsListPagination method" );
                      	 logger.info("could not connect to DB getShopsListPagination: " );
                      }


                     return counter;
                 	 }
	 

	 
	 

	 @RequestMapping(value ="/DisplayShops_Aliat",method = RequestMethod.POST)
     @ResponseBody

     public List<DisplayShopsAPI> DisplayShops (@RequestBody String display) {

    	 List<DisplayShopsAPI> data = new ArrayList<DisplayShopsAPI>();
         String shopid = null,saveddata;
         String[] myresult;

		 display = display.substring(1, Integer.parseInt(String.valueOf(display.length())));
		 StringBuffer sb = new StringBuffer(display);
		 sb.deleteCharAt(sb.length() - 1);
         display = sb.toString();
         myresult = display.split(":");
         shopid = myresult[1].replace("\"", "");
         
         session = almsessions.getSession();
         if (session != null && session.isOpen()) {
          	tx = session.beginTransaction();
             try {                   
            	 
        data=session.createSQLQuery("select SHOPS_ID, OWNER, LONGTITUDE, LATITUDE, ADDRESS, SHOP_NAME,REGION_NAME from SHOPS where SHOPS_ID='"+shopid+"' ").list(); 

            	 saveddata = "Saved";
                 System.out.println(saveddata);

                 ObjectMapper mapper = new ObjectMapper();
                 System.out.println("List "+mapper.writeValueAsString(data));
                 
 } catch (Exception e) {
          System.out.println("Error in creating session with the DataBase DisplayShops method" +e.getMessage());
          logger.info("Error in creating session with the DataBase DisplayShops method: " +e);
          saveddata="not update";
        } 
        finally
  	 		{
  	 			if (session != null && session.isOpen()) {
  	 				 tx.commit();
  	 				 session.close();
  	 				} 			
  	 		}             
         } else {
         	 System.out.println("could not connect to DB in DisplayShops method" );
         	logger.info("could not connect to DB DisplayShops method: " );
         	saveddata="not update";
         }
         
		return data; 
         
     }

	 
	 

	 

     @RequestMapping(value = "/SavingorUpdatingShops_Aliat", method = RequestMethod.POST)
     @ResponseBody
     public String savingShops(@RequestBody String shops) {
    	
    	 String shopsId = null; 
         String[] result,shopsIdresult,ownerresult,longresult,lattresult,addressresult,shopsNameresult,salesManageresult;
         String[] agentIdresult,agentNameresult,regionIdresult,regionNameresult,regionCoderesult,regionManagerresult,statusresult,areaIdresult,areaNameresult;
         String owner,longitude,latitude,address,shopsName,salesManager,agentId,agentName,regionId,regionName,regionCode,regionManager,status,areaId,areaName;

    	 shops = shops.substring(2, Integer.parseInt(String.valueOf(shops.length())));
		 StringBuffer sb = new StringBuffer(shops);
         sb.deleteCharAt(sb.length() - 1);
         sb.deleteCharAt(sb.length() - 1);
         shops = sb.toString();
         String data1 = String.valueOf(sb).trim();
         data1 = data1.replace("\"", "");
         result = data1.split(",");
         
         shopsIdresult = result[0].split(":");
         ownerresult = result[1].split(":");
         longresult= result[2].split(":");
         lattresult = result[3].split(":");
         addressresult = result[4].split(":");
         shopsNameresult = result[5].split(":");
         salesManageresult= result[6].split(":");
         agentIdresult = result[7].split(":");
         agentNameresult = result[8].split(":");
         regionIdresult = result[9].split(":");
         regionNameresult = result[10].split(":");
         regionCoderesult = result[11].split(":");
         regionManagerresult = result[12].split(":");
         statusresult = result[13].split(":");
         areaIdresult = result[14].split(":");
         areaNameresult = result[15].split(":");


         shopsId = shopsIdresult[1];
         owner = ownerresult[1];
         longitude = longresult[1];
         latitude = lattresult[1];
         address = addressresult[1];
         shopsName = shopsNameresult[1];
         salesManager = salesManageresult[1]; 
         agentId = agentIdresult[1]; 
         //agentName = agentNameresult[1]; 
         regionId = regionIdresult[1]; 
         regionName = regionNameresult[1]; 
         regionCode = regionCoderesult[1]; 
         regionManager = regionManagerresult[1];
         status = statusresult[1];
         areaId = areaIdresult[1];
         areaName = areaNameresult[1];


                     data = "not Saved";

                     session = almsessions.getSession();

                     if (session != null && session.isOpen()) {
                                     tx = session.beginTransaction();
                                     try {
                                    	 
                                    	 
                                    	 			 agentName=session.createSQLQuery("SELECT FULL_NAME FROM AGENT where MSISDN='" + agentId + "'").uniqueResult().toString();
                                                     Date date = new Date();
                                                     Calendar calendar = new GregorianCalendar();
                                                     calendar.setTime(date);
                                                     int year = calendar.get(Calendar.YEAR);
                                                     
                                                     if(shopsId.equalsIgnoreCase("0")) {
                                                    	// shopsId = "SHOP_" + year + "_" + appConfig.getSequenceNbr(40);
                                                     } 
                                                     
                                                     
                                                     Timestamp CreationDate, LastModified;
                                                     CreationDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());
                                                     LastModified = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());

                                                     ShopsAPI shop = new ShopsAPI();
                                                    // shop.setShpsid(shopsId);
                                                     shop.setOwner(owner);
                                                     shop.setLongitude(longitude);
                                                     shop.setLatitude(latitude);
                                                     shop.setAddress(address);
                                                     shop.setShopName(shopsName);
                                                   //  shop.setSalesManager(salesManager);
                                                     shop.setAgentId(agentId);
                                                     shop.setAgentName(agentName);
                                                     shop.setRegionId(regionId);
                                                     shop.setRegionName(regionName);
                                                     shop.setRegionCode(regionCode);
                                                     //shop.setRegionManager(regionManager);
                                                     //shop.setStatus(status);
                                                     shop.setCreatedDate(CreationDate);
                                                     shop.setLastModified(LastModified);
                                                     //shop.setAreaId(areaId);
                                                     //shop.setAreaName(areaName);
                                                     session.saveOrUpdate(shop);

                                                     data = shopsId;
                                     } catch (Exception e) {
                                                     System.out.println("Error in creating session with the SavingorUpdatingShops method " + e.getMessage());
                                                     logger.info("could not connect to DB in SavingorUpdatingShops method " +e);
                                     }

                                     finally {
                                                     if (session != null && session.isOpen()) {
                                                                     tx.commit();
                                                                     session.close();
                                                     }
                                     }
                     } else {
                                     System.out.println("could not connect to DB in SavingorUpdatingShops method");
                                     logger.info("could not connect to DB in SavingorUpdatingShops method ");
                     }

                     return data;
     }
	 
     
     
     
     
     @RequestMapping(value = "/deleteShop_Aliat", method = RequestMethod.POST)
     @ResponseBody
     public String deleteShop (@RequestBody String delete) {

    	 String ShopId = null;
    	 data = null;
    	 String[] result;
    	 
    	 delete = delete.substring(1, Integer.parseInt(String.valueOf(delete.length())));
         StringBuffer sb = new StringBuffer(delete);
         sb.deleteCharAt(sb.length() - 1);
         delete = sb.toString();	 
         result = delete.split(":");
         ShopId = result[1].replace("\"", "");
                     
     
                     session = almsessions.getSession();

                     if (session != null && session.isOpen()) {
                                     tx = session.beginTransaction();
                                     try {
                                                     query = session.createSQLQuery("delete SHOPS where SHOPS_ID='" + ShopId + "'");
                                                     query.executeUpdate();

                                                     data = "deleted";
                                     } catch (Exception e) {
                                                     data = "not deleted";
                                                     System.out.println("Error in creating session with the deleteWarehouse method " + e.getMessage());
                                                     logger.info("could not connect to DB in deleteWarehouse method " + e);
                                     }

                                     finally {
                                                     if (session != null && session.isOpen()) {
                                                                     tx.commit();
                                                                     session.close();
                                                     }
                                     }
                     } else {
                                     System.out.println("could not connect to DB in deleteWarehouse method");
                                     logger.info("could not connect to DB in deleteWarehouse method ");
                     }

                     return data;
     }
	 



     /*@RequestMapping(value = "/UploadShopImage_Aliat", method = RequestMethod.POST)
     @ResponseBody
     public String SavingShopImage(@RequestBody ShopImageAPI image) {

     	
     	String shopid= image.getShopid().toString();
     	String pathNbr = image.getPathNbr().toString();
     	
     	
     	
                     data = "not Saved";

                     session = almsessions.getALMSession();

                     if (session != null && session.isOpen()) {
                                     tx = session.beginTransaction();
                                     try {
                                                     Date date = new Date();
                                                     Calendar calendar = new GregorianCalendar();
                                                     calendar.setTime(date);
                                                     
                                                     pathNbr= "PATH_"+ appConfig.getSequenceNbr(73);

                                                         Timestamp UploadDate;
                                                         UploadDate = new Timestamp(new Timestamp(System.currentTimeMillis()).getTime());

                                                         ShopImageAPI shopimage = new ShopImageAPI();
                                                         shopimage.setShopid(shopid);
                                                         shopimage.setPathNbr(pathNbr);
                                                         shopimage.setUploadDate(UploadDate);
                                                         shopimage.setImagePath(image.getImagePath().toString());
                                                         session.saveOrUpdate(shopimage);
                                                     

                                                     
                                                     data = "saved";
                                     } catch (Exception e) {
                                                     System.out.println("Error in creating session with the UploadShopImage method " + e.getMessage());
                                                     logger.info("could not connect to DB in UploadShopImage method " +e);
                                     }

                                     finally {
                                                     if (session != null && session.isOpen()) {
                                                                     tx.commit();
                                                                     session.close();
                                                     }
                                     }
                     } else {
                                     System.out.println("could not connect to DB in UploadShopImage method");
                                     logger.info("could not connect to DB in UploadShopImage method ");
                     }

                     return data;
     }*/

     

     
     @RequestMapping(value ="/getShopImage_Aliat",method = RequestMethod.POST)
     @ResponseBody
     public List<ShopImageAPI> getShopImage(@RequestBody ShopImageAPI shopimage) {
		 
		 List<ShopImageAPI> data = new ArrayList<ShopImageAPI>();
     	        String saveddata =null;

     	     	String shopid= shopimage.getShopid();

         session = almsessions.getSession();
             if (session != null && session.isOpen()) {
              	tx = session.beginTransaction();
                 try {                   
                	 
            data=session.createSQLQuery("Select IMAGE_PATH FROM SHOPS_IMAGE WHERE SHOP_ID='" + shopid + "' ").list(); 
  
                	 saveddata = "Saved";
                     System.out.println(saveddata);
                     
                     ObjectMapper mapper = new ObjectMapper();
                     System.out.println("List "+mapper.writeValueAsString(data));
                     
     } catch (Exception e) {
              System.out.println("Error in creating session with the DataBase getShopImage" +e.getMessage());
              logger.info("Error in creating session with the DataBase getShopImage: " +e);
              saveddata="not update";
            } 
            finally
      	 		{
      	 			if (session != null && session.isOpen()) {
      	 				 tx.commit();
      	 				 session.close();
      	 				} 			
      	 		}             
             } else {
             	 System.out.println("could not connect to DB in getShopImage" );
             	logger.info("could not connect to DB getShopImage: " );
             	saveddata="not update";
             }
             
               return data;       
                     
                     
     }
     
     

    /* @RequestMapping(value = "/deleteShopImage_Aliat", method = RequestMethod.POST)
     @ResponseBody
     public String deleteShopImage(@RequestBody ShopImageAPI image) {

                     data = null;
                     String path= image.getImagePath().toString();

                     session = almsessions.getALMSession();

                     if (session != null && session.isOpen()) {
                                     tx = session.beginTransaction();
                                     try {
                                                     query = session.createSQLQuery("delete  SHOPS_IMAGE  where IMAGE_PATH ='" + path + "'");
                                                     query.executeUpdate();

                                                     data = "deleted";
                                     } catch (Exception e) {
                                                     data = "not deleted";
                                                     System.out.println("Error in creating session with the deleteShopImage method " + e.getMessage());
                                                     logger.info("could not connect to DB in deleteShopImage method " +e);
                                     }

                                     finally {
                                                     if (session != null && session.isOpen()) {
                                                                     tx.commit();
                                                                     session.close();
                                                     }
                                     }
                     } else {
                                     System.out.println("could not connect to DB in deleteShopImage method");
                                     logger.info("could not connect to DB in deleteShopImage method ");
                     }

                     return data;
     }*/
     
     

     
     
     
}

     
     

