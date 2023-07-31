package com.aliat.alm.Parser;
import java.io.BufferedReader;
import java.io.File;  
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Iterator;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 
import org.apache.commons.lang3.StringUtils;

import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

public class ReadAreaClusterFromExcel_WIN {
	
	static Connection con ;
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
	static String vstartdate;
	static String venddate;
	static int voverride;
	
	public static void main(String[] args)  {
		
		Statement stmt0 = null;
		 BufferedReader objReader = null;
		 	
	 
		 ////////////////////////////////////////////////////////////////////////////////////////////
		 ///// BEFORE RUNNING THIS SCRIPT PLEASE REMOVE FROM EXCEL ALL      ' AND / AND , 
		 ////////////////////////////////////////////////////////////////////////////////////////////
		 
		 
		 try	{
		    	
				objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\"+"almconfig.dat"));
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
					 if (data.contains("readexcel")) {
						 data1=data.split(";",-1);
						 vreadexcel=data1[1];
						 //System.out.println("read excel from :" + vreadexcel);
					 }
					 
				}
				 objReader1.close();

				 	//get only year from today date
				 	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDateTime now = LocalDateTime.now();
					Gyear=dtf.format(now).substring(0,4);
					String lofilename="ReadAreaClusterFromExcel-"+dtf.format(now)+".log";
				
				
					logger = Logger.getLogger("MyLog"); 
					logger.setUseParentHandlers(false);
					
					// This block configure the logger with handler and formatter  and PATH
			        fh = new FileHandler(logpath+"\\"+lofilename);
			        logger.addHandler(fh);
			        SimpleFormatter formatter = new SimpleFormatter();  
			        fh.setFormatter(formatter);
			        
			        
				    	// Connect to almparser DB 
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
						
						
						
					///////////////////////////////////////////////////////////////////////
						System.out.println("Get Start and End Date from excel sheet 3");
						ReadDates();
					///////////////////////////////////////////////////////////////////////
						
						//validate if the satrt date and end date already handeled
						Statement stmttype = null;
						String query2 = "Select * from AREA_FINANCE where START_DATE = '"+ vstartdate +"' and END_DATE= '"+ venddate +"' ";      
					    stmttype = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					    ResultSet rs2 = stmttype.executeQuery(query2);
					    rs2.last();
				 	    int totalrec = rs2.getRow(); 
				 	   if ((totalrec > 0 ) && (voverride==0 )){  // if found start date and end date and override = 0 do nothing
				 		  System.out.println("Start Date: " + vstartdate + "  and End date:  " +venddate + "  already handled previuosly");
				 		  logger.info("Start Date: " + vstartdate + "  and End date:  " +venddate + "  already handled previuosly");
				 	   } else { // here either not found or overide =1
				 		   
				 			// if overrride ==1 then delete All data in table in area_finance and warehouse_profit and loss
							if (voverride==1 ) {
								PreparedStatement stmt10 = con.prepareStatement("delete from AREA_FINANCE where START_DATE = '"+ vstartdate +"' and END_DATE= '"+ venddate +"' ");
						 		stmt10.executeUpdate();
						 		stmt10.close();
						 		
						 		PreparedStatement stmt11 = con.prepareStatement("delete from WAREHOUSE_PROFIT_LOSS where START_DATE = '"+ vstartdate +"' and END_DATE= '"+ venddate +"' ");
						 		stmt11.executeUpdate();
						 		stmt11.close();
							}
				 		   
							logger.info("1-Read areas form excel 1st sheet and save in DB");

							//read areas form excel 1st sheet and save in DB
							ReadAreas();
							
							System.out.println("==========================================");

							logger.info("1-Read areas form excel 1st sheet and save in DB COMPLETED");

							///////////////////////////////////////////////////////////////////////
									
							logger.info("2-Read Clusters from excel 2nd sheet and save in DB");

							//read Clusters from excel 2nd sheet and save in DB
							Readclusters();
									
							logger.info("2-Read Clusters from excel 2nd sheet and save in DB COMPLETED");
								    
						   ///////////////////////////////////////////////////////////////////////
							logger.info("3-Fill data in AREA FINANCE table");

							//Fill data in AREA FINANCE table from excel 1st sheet and save in DB
							FillAreaFinance();
									
							logger.info("3-Fill data in AREA FINANCE table");
							
							
				 	   }

					con.close();
					
			}
			catch(Exception e){
			      System.err.println(e);
			      e.printStackTrace();
			      logger.info("Error : "+e);
			   }
		
		
	}
	
	
	private static void  ReadDates() {
		{  
					
				try  
				{  
			
						File file = new File(vreadexcel+"\\"+"kiniya.xlsx");   //creating a new file instance  
						FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
						//creating Workbook instance that refers to .xlsx file  
						XSSFWorkbook wb = new XSSFWorkbook(fis);   
						XSSFSheet sheet = wb.getSheetAt(3);     //creating a Sheet object to retrieve object  
						Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
						while (itr.hasNext())                 
						{  
						try {
						Row row = itr.next();  
						Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
					    
					     //for(int cn = 0; cn < blankRow.getLastCellNum(); cn++) 
					     for(int cn = 0; cn < 6; cn++) 
					        {
					           Cell cells = row.getCell(cn, Row.CREATE_NULL_AS_BLANK);
					            
					            switch (cells.getCellType())               
					        	{  
						        	case Cell.CELL_TYPE_STRING:    //field that represents string cell type 
						        		//System.out.print(cells.getStringCellValue() + ": ");
						        		if (cn==1) { vstartdate=cells.getStringCellValue(); System.out.print(vstartdate +"\t"); }
						        		if (cn==3) { venddate=cells.getStringCellValue();System.out.print(venddate +"\t");}	
						        		break;  
						        	case Cell.CELL_TYPE_NUMERIC:    //field that represents number cell type  
						        		//System.out.print(cells.getDateCellValue() +"\t"); 
						        		String vardata2=String.valueOf(cells.getDateCellValue());
						        		if (cn==1) { vstartdate=vardata2;  }
						        		if (cn==3) { venddate=vardata2;}
						        		if (cn==5) { voverride=(int) cells.getNumericCellValue(); System.out.print(voverride +"\t");}   
						        		break;  
						        	case Cell.CELL_TYPE_BLANK:
						        		//System.out.print("null"); 
						        		break;	
						        			
					        	}  
					            
					            
					        }
					     
					    }
						catch(Exception e)  
						{  
							logger.info("error at ReadDates is :"+ e.toString());
							System.out.println("error at ReadDates is :"+ e.toString()); 
						}  

				}
						System.out.println();
						fis.close();
				}  
				catch(Exception e)  
				{  
					logger.info("error at ReadDates is :"+ e.toString());
					System.out.println("error at ReadDates is :"+ e.toString()); 
				}   
		}  
} //end ReadDates
	 
		private static void  ReadAreas() {
			{  
						int startnbr=0;
					try  
					{  
				
							File file = new File(vreadexcel+"\\"+"kiniya.xlsx");   //creating a new file instance  
							FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
							//creating Workbook instance that refers to .xlsx file  
							XSSFWorkbook wb = new XSSFWorkbook(fis);   
							XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
							Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
							while (itr.hasNext())                 
							{  
							try {
							Row row = itr.next();  
							Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
						    
						     //for(int cn = 0; cn < blankRow.getLastCellNum(); cn++) 
						     for(int cn = 0; cn < 1; cn++) 
						        {
						           Cell cells = row.getCell(cn, Row.CREATE_NULL_AS_BLANK);
						            
						            switch (cells.getCellType())               
						        	{  
							        	case Cell.CELL_TYPE_STRING:    //field that represents string cell type  
							        		if (StringUtils.equalsIgnoreCase (cells.getStringCellValue(),"Row Labels")) {
							        			startnbr=1;
							        			break;	
							        		} else {
							        			if (startnbr == 0) {break;}
							        		}
							        			
							        		if (StringUtils.equalsIgnoreCase (cells.getStringCellValue(),"Grand Total")) {
							        			break;	
							        		}
							        		//System.out.print(cells.getStringCellValue());
							        		AddtoAreaTable(cells.getStringCellValue());
							        		System.out.println(""); 
							        		break;  
							        	case Cell.CELL_TYPE_NUMERIC:    //field that represents number cell type  
							        		//System.out.print(cells.getNumericCellValue()); 
							        		String vardata=String.valueOf(cells.getNumericCellValue());
							        		AddtoAreaTable(vardata);
							        		System.out.println(""); 
							        		break;  
							        	case Cell.CELL_TYPE_BLANK:
							        		//System.out.print("null"); 
							        		break;	
						        	}  
						            
						            
						        }
						     
						    }
							catch(Exception e)  
							{  
								logger.info("error at ReadAreas is :"+ e.toString());
								System.out.println("error at ReadAreas is :"+ e.toString()); 
							}

					}
							fis.close();
					}  
					catch(Exception e)  
					{  
						logger.info("error at ReadAreas is :"+ e.toString());
						System.out.println("error at ReadAreas is :"+ e.toString()); 
					} 
			}  
	} //end readAreas
		
		private static void AddtoAreaTable(String varea) throws SQLException  {
			String vareadname="0";
			// Get area id and name having same name as in table Area
			Statement stmttype = null;
			String query2 = "select AREA_NAME from AREA where AREA_NAME='"+ varea +"' ";      
		    stmttype = con.createStatement();
		    ResultSet rs2 = stmttype.executeQuery(query2);
		    while (rs2.next()) {
		    	try {
 		    	vareadname= rs2.getString("AREA_NAME");
		    	}
		    	catch(Exception e)  
				{  
					logger.info("error at AddtoAreaTable is :"+ e.toString());
					System.out.println("error at AddtoAreaTable is :"+ e.toString()); 
				}  
		     }
		     rs2.close();
		     stmttype.close();
		     // if Area not found in DB add it
		     if (StringUtils.equalsIgnoreCase (vareadname,"0")) {
		    	 // display the area if it is a new one
		    	 System.out.println(varea);
		    	 try {
		    	 PreparedStatement stmtinsert2=null;
					stmtinsert2 = con.prepareStatement("insert into  AREA(ID,AREA_NAME,LONGITUDE,LATITUDE,CREATION_DATE,LAST_MODIFICATION_DATE) values ('" + Gyear +"' ||'_AREA_'|| AREA_SEQ.nextval,'" + varea + "','0','0',sysdate,sysdate)");
					stmtinsert2.executeUpdate();
					stmtinsert2.close();
		    	 }
		    	 catch(Exception e)  
					{  
						logger.info("error at AddtoAreaTable is :"+ e.toString());
						System.out.println("error at AddtoAreaTable is :"+ e.toString()); 
					}  
		     }
			
			
		}
		
		private static void  Readclusters() {
			{  
						int startnbr=0;
					try  
					{  
				            
							File file = new File(vreadexcel+"\\"+"kiniya.xlsx");   //creating a new file instance  
							FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
							//creating Workbook instance that refers to .xlsx file  
							XSSFWorkbook wb = new XSSFWorkbook(fis);   
							XSSFSheet sheet = wb.getSheetAt(1);     //creating a Sheet object to retrieve object  
							Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
							while (itr.hasNext())                 
							{  
								try  
								{  				
							Row row = itr.next();  
							Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
							
							if(startnbr >0 ) {
								  //for(int cn = 0; cn < blankRow.getLastCellNum(); cn++) 
								startnbr=startnbr+1;
							     for(int cn = 19; cn < 20; cn++) 
							        {
							           Cell cells = row.getCell(cn, Row.CREATE_NULL_AS_BLANK);
							           
							            switch (cells.getCellType())               
							        	{  
								        	case Cell.CELL_TYPE_STRING:    //field that represents string cell type  
								        		//System.out.print(cells.getStringCellValue());
								        		AddtoClusterTable(cells.getStringCellValue());
								        		break;  
								        	case Cell.CELL_TYPE_NUMERIC:    //field that represents number cell type  
								        		//System.out.print(cells.getNumericCellValue()); 
								        		String vardata=String.valueOf(cells.getNumericCellValue());
								        		AddtoClusterTable(vardata);
								        		break;  
								        	case Cell.CELL_TYPE_BLANK:  //field that represents BLANK cell type 
								        		//System.out.print("null"); 
								        		
								        		break;	
								        	case Cell.CELL_TYPE_FORMULA: //field that represents formula cell type 
								        		//System.out.print(cells.getStringCellValue());
								        		AddtoClusterTable(cells.getStringCellValue());
									        	break;
								        		  
							        	}  
							            
							            
							        }
							     System.out.println(""); 
							  }else {
								startnbr=startnbr+1;
							    }
							}  // end try inside loop 
								catch(Exception e)  
								{ 
									// catch inside loop
									System.out.println("error in Readclusters at line  "+ startnbr + "  "+ e.toString());
									logger.info("error in Readclusters at line  "+ startnbr + "  "+ e.toString());
								}
								
						    }
							
							fis.close();
					}  
					
					catch(Exception e)  
					{  
						System.out.println("error in Readclusters at line  "+ startnbr + "  "+ e.toString());
						logger.info("error in Readclusters at line  "+ startnbr + "  "+ e.toString());
					}  
			}  
	} //end Readclusters
		
		private static void AddtoClusterTable(String vcluster) throws SQLException  {
			PreparedStatement stmtinsert2=null;
			String vareadid="";
			String vareadname="";
			// Get area id and name having same name as cluster
			Statement stmttype = null;
			String query2 = "select ID,AREA_NAME from AREA where AREA_NAME='"+ vcluster +"' ";      
		    stmttype = con.createStatement();
		    ResultSet rs2 = stmttype.executeQuery(query2);
		    while (rs2.next()) {
		    	try {
		    	vareadid= rs2.getString("ID");   
		    	vareadname= rs2.getString("AREA_NAME");
		    	}
		    	catch(Exception e)  
				{  
					logger.info("error at AddtoClusterTable is :"+ e.toString());
					System.out.println("error at AddtoClusterTable is :"+ e.toString()); 
				} 
		     }
		     rs2.close();
		     stmttype.close();
		     
		     String vclustername="0";
		  // Get area id and name having same name as in table Area
				Statement stmttype3 = null;
				String query3 = "select CLUSTER_NAME from CLUSTERS where CLUSTER_NAME='"+ vcluster +"' ";      
			    stmttype3 = con.createStatement();
			    ResultSet rs3 = stmttype3.executeQuery(query3);
			    while (rs3.next()) {
			    	try {
			    	vclustername= rs3.getString("CLUSTER_NAME");
			    	}
			    	catch(Exception e)  
					{  
						logger.info("error at AddtoClusterTable is :"+ e.toString());
						System.out.println("error at AddtoClusterTable is :"+ e.toString()); 
					} 
			     }
			     rs3.close();
			     stmttype3.close();

		     
			     
			     if (StringUtils.equalsIgnoreCase (vclustername,"0")) {
			    	// display the cluster if it is a new one
			    	 System.out.println(vcluster);
			    	 try {
			    	//insert into cluster linked to area id and name if found previuosly 
						stmtinsert2 = con.prepareStatement("insert into  CLUSTERS(ID,CLUSTER_NAME,CREATION_DATE,LAST_MODIFICATION_DATE,AREA_ID,AREA_NAME,LONGITUDE,LATITUDE) values ('" + Gyear +"' ||'_CLUSTERS_'|| CLUSTER_SEQ.nextval,'" + vcluster + "',sysdate,sysdate,'"+ vareadid +"','"+ vareadname +"','0','0')");
						stmtinsert2.executeUpdate();
						stmtinsert2.close(); 
			    	 }
			    	 catch(Exception e)  
						{  
							logger.info("error at AddtoClusterTable is :"+ e.toString());
							System.out.println("error at AddtoClusterTable is :"+ e.toString()); 
						} 
			     }
			
			
		}
		
		
		private static void  FillAreaFinance() throws SQLException {
			  
						int startnbr=0;
						int vline=0;
						String[] financetab =new String[13];
						
						// check if start and end date already found in DB in that case no nedd to enter Data
						Statement stmttype = null;
						
						String query2 = "Select * from AREA_FINANCE where START_DATE = '"+ vstartdate +"' and END_DATE= '"+ venddate +"' ";      
					    stmttype = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					    ResultSet rs2 = stmttype.executeQuery(query2);
					    rs2.last();
				 	    int totalrec = rs2.getRow(); 
				 	   
				 	   if (totalrec == 0 ) {
				 		  rs2.close();
						     stmttype.close();
				 		  try  
							{  
						
									File file = new File(vreadexcel+"\\"+"kiniya.xlsx");   //creating a new file instance  
									FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
									//creating Workbook instance that refers to .xlsx file  
									XSSFWorkbook wb = new XSSFWorkbook(fis);   
									XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
									Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
									while (itr.hasNext())                 
									{  
									try {
									Row row = itr.next();  
									Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
									financetab =new String[13];
								     //for(int cn = 0; cn < blankRow.getLastCellNum(); cn++) 
								     for(int cn = 0; cn < 13; cn++) 
								        {
								           Cell cells = row.getCell(cn, Row.CREATE_NULL_AS_BLANK);
								            
								            switch (cells.getCellType())               
								        	{  
									        	case Cell.CELL_TYPE_STRING:    //field that represents string cell type  
									        		if (StringUtils.equalsIgnoreCase (cells.getStringCellValue(),"Row Labels")) {
									        			startnbr=1;
									        			vline=-1;
									        			break;	
									        		} else {
									        			if ( (startnbr == 0) || (vline==-1)) {break;}
									        		}
									        			
									        		if (StringUtils.equalsIgnoreCase (cells.getStringCellValue(),"Grand Total")) {
									        			vline=-1;
									        			break;	
									        		}
									        		//System.out.print(cells.getStringCellValue()+"\t");
									        		financetab[cn]=cells.getStringCellValue();
									        		break;  
									        	case Cell.CELL_TYPE_NUMERIC:    //field that represents number cell type  
									        		if ( (startnbr == 0) || (vline==-1)) {break;}
									        		//System.out.print(cells.getNumericCellValue() +"\t"); 
									        		String vardata=String.valueOf(cells.getNumericCellValue());
									        		financetab[cn]=vardata;
									        		//String vardata=String.valueOf(cells.getNumericCellValue());
									        		//AddtoAreaTable(vardata);
									        		break;  
									        	case Cell.CELL_TYPE_BLANK:
									        		if ( (startnbr == 0) || (vline==-1)) {break;}
									        			//System.out.print("null"+"\t"); 
									        			financetab[cn]="0";
									        		break;	
								        	}  
								            
								            
								        }
								     vline=vline+1;
								     AddtoAreaFinanceTable(financetab); 
								    }
									catch(Exception e)  
									{  
										logger.info("error in FillAreaFinance at line  "+ startnbr + "  "+ e.toString());
										System.out.println("error in FillAreaFinance at line  "+ startnbr + "  "+ e.toString());   
									}  
									if ((startnbr >  0) && (vline >  0)) { //avoid adding line if we did not start display data
									System.out.println(""); 
									}
							}
									fis.close();
									
							}  
							catch(Exception e)  
							{  
								logger.info("error in FillAreaFinance at line  "+ startnbr + "  "+ e.toString());
								System.out.println("error in FillAreaFinance at line  "+ startnbr + "  "+ e.toString()); 
							}  
				 		   
				 		 System.out.println("Adding to Area Finance COMPLETED");
				 		logger.info("Adding to Area Finance COMPLETED");
				 		
				 		 //now read page 2 from excel sheet to insert data into  Warehouse, warehouse_passive , warehous_profit_loss
				 		
				 		///// here to fill in WAREHOUSE_PROFIT_LOSS
				 		AddToWarehoueProfitLoss();   
				 		
				 		
				 		
				 	   } else {// end if totrec==0
				 		  rs2.close();
						     stmttype.close();
				 		   System.out.println("Start Date: " + vstartdate + "  and End date:  " +venddate + "  already handled previuosly");
				 		  logger.info("Start Date: " + vstartdate + "  and End date:  " +venddate + "  already handled previuosly");
				 	   }
			
	} //end FillAreaFinance
		
		private static void AddtoAreaFinanceTable(String[] varea) throws SQLException  {
			PreparedStatement stmtinsert2=null;
			String vareadid="0";
			String vareadname="0";
	     
			if (StringUtils.equalsIgnoreCase (varea[0],null)) {
				//System.out.println("EMPTY");
			} else {
				// Get area id and name having same name as in table Area
				Statement stmttype = null;
				String query2 = "select ID,AREA_NAME from AREA where AREA_NAME='"+ varea[0] +"' ";      
			    stmttype = con.createStatement();
			    ResultSet rs2 = stmttype.executeQuery(query2);
			    while (rs2.next()) {
			    	try {
			    	vareadid= rs2.getString("ID");   
			    	vareadname= rs2.getString("AREA_NAME");
			    	}
			    	catch(Exception e)  
					{  
						logger.info("error at AddtoAreaFinanceTable is :"+ e.toString());
						System.out.println("error at AddtoAreaFinanceTable is :"+ e.toString()); 
					}
			     }
			     rs2.close();
			     stmttype.close();
			     
			    // insert data into table Area Finance
			     try {
				stmtinsert2 = con.prepareStatement("insert into  AREA_FINANCE(ID,START_DATE,END_DATE,CREATION_DATE,LAST_MODIFY_DATE,AREA_ID,NO_2G_SITES,PROFIT_AND_LOSS_2G,NO_2G_3G_SITES,PROFIT_AND_LOSS_2G_3G,NO_2G_3G_4G_SITES,PROFIT_AND_LOSS_2G_3G_4G,TOTAL_NO_SITES,SUM_PROFIT_LOSS,AREA_NAME) "
						+ "values ('" + Gyear +"' ||'_FINANCE_'|| AREAFINANCE_SEQ.nextval,'" + vstartdate +"','" + venddate +"',sysdate,sysdate,'"+ vareadid +"','"+ varea[3] +"','"+ varea[4] +"','"+ varea[5] +"','"+ varea[6] +"','"+ varea[7] +"','"+ varea[8] +"','"+ varea[11] +"','"+ varea[12] +"','"+ vareadname +"')");
				stmtinsert2.executeUpdate();
				stmtinsert2.close();
			     }
			     catch(Exception e)  
					{  
						logger.info("error at AddtoAreaFinanceTable is :"+ e.toString());
						System.out.println("error at AddtoAreaFinanceTable is :"+ e.toString()); 
					}
				
			}
			
		}
		
		private static void AddToWarehoueProfitLoss() throws SQLException  {
				int startnbr=0;
				String vsiteid="0";
				String vsitename="0";
				String vlat="0";
				String vlng="0";
				String vtech="0";
				String vareaid="0";
				String vareaname="0";
				String vclustid="0";
				String vclustname="0";
				String vtransmission="0";
				String vsiteowner="0";
				String vtowertype="0";
				String vtowerheiht="0";
				String vpopulation="0";
				String util2G="0";
				String util3G="0";
				String util4G="0";
				String avlbl2G="0";
				String avlbl3G="0";
				String avlbl4G="0";
				String avlbl5G="0";
				String vgads="0";
				String vcsso="0";
				String vcustbase="0";
				String vdata="0";
				String vrevenue="0";
				String vsmsrevenue="0";
				String vGrevenue="0";
				String vdatatraffic="0";
				String vsmstog="0";
				String vvtog="0";
				String vvtic="0";
				String vsmstic="0";
				String vopexmonth="0";
				String vpl="0";
				
			try  
			{  
		            
					File file = new File(vreadexcel+"\\"+"kiniya.xlsx");   //creating a new file instance  
					FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
					//creating Workbook instance that refers to .xlsx file  
					XSSFWorkbook wb = new XSSFWorkbook(fis);   
					XSSFSheet sheet = wb.getSheetAt(1);     //creating a Sheet object to retrieve object  
					Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
					while (itr.hasNext())                 
					{  
						try  
						{  				
					Row row = itr.next();  
					Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
					
					if(startnbr >0 ) {
						  //for(int cn = 0; cn < blankRow.getLastCellNum(); cn++) 
						startnbr=startnbr+1;
						
						 vsiteid="0";
						 vsitename="0";
						 vlat="0";
						 vlng="0";
						 vtech="0";
						 vareaid="0";
						 vareaname="0";
						 vclustid="0";
						 vclustname="0";
						 vtransmission="0";
						 vsiteowner="0";
						 vtowertype="0";
						 vtowerheiht="0";
						 vpopulation="0";
						 util2G="0";
						 util3G="0";
						 util4G="0";
						 avlbl2G="0";
						 avlbl3G="0";
						 avlbl4G="0";
						 avlbl5G="0";
						 vgads="0";
						 vcsso="0";
						 vcustbase="0";
						 vdata="0";
						 vrevenue="0";
						 vsmsrevenue="0";
						 vGrevenue="0";
						 vdatatraffic="0";
						 vsmstog="0";
						 vvtog="0";
						 vvtic="0";
						 vsmstic="0";
						 vopexmonth="0";
						 vpl="0";
						
					     for(int cn = 1; cn < 35; cn++) 
					        {
					           Cell cells = row.getCell(cn, Row.CREATE_NULL_AS_BLANK);
					           
					            switch (cells.getCellType())               
					        	{  
						        	case Cell.CELL_TYPE_STRING:    //field that represents string cell type  
						        		//System.out.print("column "+cn +"::" +cells.getStringCellValue() +"\t");
						        		if (cn==1) { vsiteid=cells.getStringCellValue();}
						        		if (cn==2) { vsitename=cells.getStringCellValue();}
						        		if (cn==5) { vtech=cells.getStringCellValue();}
						        		if (cn==6) { vtransmission=cells.getStringCellValue();}
						        		if (cn==7) { vsiteowner=cells.getStringCellValue();}
						        		if (cn==10) { vtowertype=cells.getStringCellValue();}
						        		break;  
						        	case Cell.CELL_TYPE_NUMERIC:    //field that represents number cell type  
						        		String vardata=String.valueOf(cells.getNumericCellValue());
						        		//System.out.print("column "+cn +"::" +vardata +"\t");
						        		if (cn==3) { vlat=vardata;}
						        		if (cn==4) { vlng=vardata;}
						        		if (cn==11) { vtowerheiht=vardata;}
						        		if (cn==12) { vpopulation=vardata;}
						        		if (cn==13) { util2G=vardata;}
						        		if (cn==14) { util3G=vardata;}
						        		if (cn==15) { util4G=vardata;}
						        		if (cn==16) { avlbl2G=vardata;}
						        		if (cn==17) { avlbl3G=vardata;}
						        		if (cn==18) { avlbl4G=vardata;}
						        		
						        		if (cn==21) { vcsso=vardata;}
						        		
						        		if (cn==24) { vdata=vardata;}
						        		if (cn==25) { vrevenue=vardata;}
						        		if (cn==26) { vsmsrevenue=vardata;}
						        		if (cn==27) { vGrevenue=vardata;}
						        		if (cn==28) { vdatatraffic=vardata;}
						        		if (cn==29) { vsmstog=vardata;}
						        		if (cn==30) { vvtog=vardata;}
						        		
						        		break;  
						        	case Cell.CELL_TYPE_BLANK:  //field that represents BLANK cell type 
						        		//System.out.print("column "+cn +"::" +"null"+"\t"); 
						        		if (cn==12) { vpopulation="0";}
						        		if (cn==21) { vcsso="0";}
						        		
						        		break;	
						        	case Cell.CELL_TYPE_FORMULA: //field that represents formula cell type 
						        		if (cn==19) { //System.out.print("column "+cn +"::" + cells.getStringCellValue()+"\t");  
						        		vclustname=cells.getStringCellValue();} else {
						        			//System.out.print("column "+cn +"::" + cells.getNumericCellValue()+"\t");
						        		}
						        		if (cn==20) { vardata=String.valueOf(cells.getNumericCellValue()); vgads=vardata;}
						        		if (cn==23) { vardata=String.valueOf(cells.getNumericCellValue()); vcustbase=vardata;}
						        		if (cn==31) { vardata=String.valueOf(cells.getNumericCellValue()); vvtic=vardata;}
						        		if (cn==32) { vardata=String.valueOf(cells.getNumericCellValue()); vsmstic=vardata;}
						        		if (cn==33) { vardata=String.valueOf(cells.getNumericCellValue()); vopexmonth=vardata;}
						        		if (cn==34) { vardata=String.valueOf(cells.getNumericCellValue()); vpl=vardata;}
							        	break;
						        		  
					        	}  
					            
					            
					        }
					     System.out.println(""); 
					     AddrecordtoWarehouse(vsiteid,vsitename,vlat,vlng,vtech,vclustname,vtransmission,vsiteowner,vtowertype,vtowerheiht,vpopulation,util2G,util3G,util4G,avlbl2G,avlbl3G,avlbl4G,vgads,vcsso,vcustbase,vdata,vrevenue,vsmsrevenue,vGrevenue,vdatatraffic,vsmstog,vvtog,vvtic,vsmstic,vopexmonth,vpl);
					     //System.out.println("line =  " + vclustname +":"+vsiteid + ":"+ vopexmonth +":"+ vsmstic +":"+ vvtic );
					     //System.out.println("");
					     
					  }else {
						startnbr=startnbr+1;
					    }
					}  // end try inside loop 
						catch(Exception e)  
						{ 
							// catch inside loop
							System.out.println("error in AddToWarehoueProfitLoss at line  "+ startnbr + "  "+ e.toString());
							logger.info("error in AddToWarehoueProfitLoss at line  "+ startnbr + "  "+ e.toString());
						}
						
				    }
					
					fis.close();
					System.out.println("Add to table WAREHOUSE_PROFIT_LOSS   COMPLETED");
				     logger.info("Add to table WAREHOUSE_PROFIT_LOSS   COMPLETED");
			}  
			
			catch(Exception e)  
			{  
				logger.info("error in AddToWarehoueProfitLoss at line  "+ startnbr + "  "+ e.toString());
				System.out.println("error in AddToWarehoueProfitLoss at line  "+ startnbr + "  "+ e.toString());
			}  
	
			
	}// end AddToWarehoueProfitLoss
		
		
	private static void AddrecordtoWarehouse(String varsiteid,String varsitename,String varlat,String varlng,String vartech,String varclustname,String vartransmission,String varsiteowner,String vartowertype,String vartowerheiht,String varpopulation,String varutil2G,String varutil3G,String varutil4G,String varavlbl2G,String varavlbl3G,String varavlbl4G,String vargads,String varcsso,String varcustbase,String vrdata,String varrevenue,String varsmsrevenue,String varGrevenue,String vardatatraffic,String varsmstog,String varvtog,String varvtic,String varsmstic,String varopexmonth,String varpl) throws SQLException  {
			PreparedStatement stmtinsert2=null;
			PreparedStatement stmtinsert3=null;
			String vwareid ="0";
			String vwarename ="0";
			String vareadid="0";
			String vareadname="0";
			String vartech2="0";
			String vartech3="0";
			String vartech4="0";
			String vartech5="0";  
			
			////// working on data in warehouse////////////////////////////
			
			String var =vartech;
			 int n = var.indexOf("2G");
			 if (n>-1) {vartech2="1";}
			 
			  n = var.indexOf("3G");
			  if (n>-1) {vartech3="1";}
			 
			  n = var.indexOf("4G");
			  if (n>-1) {vartech4="1";}
			 
			  n = var.indexOf("5G");
			  if (n>-1) {vartech5="1";}
			 
			 //System.out.println(vartech2 + ":"+ vartech3 + ":"+ vartech4 + ":"+ vartech5);
			
				// Get area id and name having same name as in table Area
				Statement stmttype = null;
				String query2 = "select ID,AREA_NAME from AREA where AREA_NAME='"+ varclustname +"' ";      
			    stmttype = con.createStatement();
			    ResultSet rs2 = stmttype.executeQuery(query2);
			    while (rs2.next()) {
			    	try {
			    	vareadid= rs2.getString("ID");   
			    	vareadname= rs2.getString("AREA_NAME");
			    	}
			    	catch(Exception e)  
					{  
						logger.info("error at AddrecordtoWarehouse is :"+ e.toString());
						System.out.println("error at AddrecordtoWarehouse is :"+ e.toString()); 
					}
			     }
			     rs2.close();
			     stmttype.close();
			     
			     //System.out.println(vareadid +" : "+ vareadname );
			     
			     String varclusterid="0";
				  // Get area id and name having same name as in table Area
						Statement stmttype3 = null;
						String query3 = "select ID from CLUSTERS where CLUSTER_NAME='"+ varclustname +"' ";      
					    stmttype3 = con.createStatement();
					    ResultSet rs3 = stmttype3.executeQuery(query3);
					    while (rs3.next()) {
					    	try {
					    	varclusterid= rs3.getString("ID");
					    	}
					    	catch(Exception e)  
							{  
								logger.info("error at AddrecordtoWarehouse is :"+ e.toString());
								System.out.println("error at AddrecordtoWarehouse is :"+ e.toString()); 
							}
					     }
					     rs3.close();
					     stmttype3.close();
					     
					     //System.out.println(varclusterid );
			     
					
					 	// Get warehouse id based on site id
							Statement stmttype4 = null;
							String query4 = "select WARE_ID,WARE_NAME from WAREHOUSE where SITE_ID='"+ varsiteid +"' and WARE_NAME ='"+ varsitename +"' and LONGITUDE='"+ varlng +"' and LATITUDE='"+ varlat +"' ";      
						    stmttype4 = con.createStatement();
						    ResultSet rs4 = stmttype4.executeQuery(query4);
						    while (rs4.next()) {
						    	try {
						    	vwareid= rs4.getString("WARE_ID"); 
						    	vwarename= rs4.getString("WARE_NAME"); 
						    	}
						    	catch(Exception e)  
								{  
									logger.info("error at AddrecordtoWarehouse is :"+ e.toString());
									System.out.println("error at AddrecordtoWarehouse is :"+ e.toString()); 
								}
						     }
						     rs4.close();
						     stmttype4.close();
					     
						     // if warwhouse found just update data inside if not found create new one
						     if (StringUtils.equalsIgnoreCase (vwareid,"0")) {
						    	 
						    	// get warehouse new sequence number 
						    	 String query5 = "select WAREHOUSE_SEQ.nextval as nbr from dual";      
						    	 Statement stmttype5 = con.createStatement();
						          ResultSet rs5 = stmttype5.executeQuery(query5);
						         
						          while (rs5.next()) {
						        	  try {
						        	  vwareid= rs5.getString("nbr");   
						        	  }
						        	  catch(Exception e)  
										{  
											logger.info("error at AddrecordtoWarehouse is :"+ e.toString());
											System.out.println("error at AddrecordtoWarehouse is :"+ e.toString()); 
										}
						          	}
						          rs5.close();
						          stmttype5.close();
						    	 
						          vwareid=Gyear +"_WARE_"+ vwareid;
						    	   
						    	 
						    	    //insert data into table WAREHOUSE
									stmtinsert2 = con.prepareStatement("insert into  WAREHOUSE(WARE_ID,CREATION_DATE,LAST_MODIFY_DATE,WARE_NAME,CITY,LONGITUDE,LATITUDE,SITE,SITE_ID,TECH_2G,TECH_3G,TECH_4G,TECH_5G,AREA_ID,AREA_NAME,CLUSTER_ID,CLUSTER_NAME,ADDRESS) "
											+ "values ('" + vwareid +"' ,sysdate,sysdate,'"+ varsitename +"','"+ vareadname +"','"+ varlng +"','"+ varlat +"','0','"+ varsiteid +"','"+ vartech2 +"','"+ vartech3 +"','"+ vartech4 +"','"+ vartech5 +"','"+ vareadid +"','"+ vareadname +"','"+ varclusterid +"','"+ varclustname +"','0')");
									stmtinsert2.executeUpdate();
									stmtinsert2.close();
									
									///////////////////working on data in warehouse_passive /////
									//insert data into table WAREHOUSE_PASSIVE
									stmtinsert2 = con.prepareStatement("insert into  WAREHOUSE_PASSIVE(WARE_ID,WARE_NAME,CREATION_DATE,LAST_MODIFY_DATE,SITE_ID,SITE_ADDRESS,CIRCLE_ID,TOWN_NAME,PINCODE,SDCA,DISTRICT_NAME,EXISTING_NEW_TOWN,SHOWCASE_NON_SHOWCASE,SITE_PRINCIPAL_OWNER,TOWER_CO_ID,SITE_MODE,TOWER_TYPE,TOWER_HEIGHT,SITE_OWNER,BUILDING_HEIGHT,SHARED_NON_SHARED,TRANSMISSION,ICR_CATEGORY,HVS_NON_HVS,CLUSTER_ID,STATUS,NEP_SYNCH,DISCOVERY_DATE,LAST_SHOWN_DATE,LAST_MODIFIED_DATE,HUB_SITE,LOCATION_NOTES,SHELTER,SHELTER_ID,SHELTER_TYPE,SHELTER_VENDOR,SHELTER_WITH_WITHOUT) "
											+ "values ('" + vwareid +"','"+ varsitename +"' ,sysdate,sysdate,'"+ varsiteid +"','0','0','"+ varsitename +"','0','0','0','0','0','0','0','0','"+ vartowertype +"','"+ vartowerheiht +"','"+ varsiteowner +"','0','0','"+ vartransmission +"','0','0','"+ varclusterid +"','0','0',sysdate,sysdate,sysdate,'0','0','0','0','0','0','0')");
									stmtinsert2.executeUpdate();
									stmtinsert2.close();
									/////////////////////////////////////////////////////////
									
								

						     } else {
						    	//update data into table WAREHOUSE based on varsiteid/wareid
									stmtinsert3 = con.prepareStatement("update  WAREHOUSE set LAST_MODIFY_DATE=sysdate, TECH_2G='"+ vartech2 +"',TECH_3G='"+ vartech3 +"',TECH_4G='"+ vartech4 +"',TECH_5G='"+ vartech2 +"'  where WARE_ID ='"+ vwareid +"'");
									stmtinsert3.executeUpdate();
									stmtinsert3.close();
									
									
									///////////////////working on data in warehouse_passive /////
									//update data into table WAREHOUSE_PASSIVE based on varsiteid/wareid
									stmtinsert3 = con.prepareStatement("update  WAREHOUSE_PASSIVE set LAST_MODIFY_DATE=sysdate,WARE_NAME='"+ vwarename +"', TRANSMISSION='"+ vartransmission +"', SITE_OWNER='"+ varsiteowner +"' ,TOWER_TYPE='"+ vartowertype +"', TOWER_HEIGHT='"+ vartowerheiht +"'   where WARE_ID ='"+ vwareid +"' " );
									stmtinsert3.executeUpdate();
									stmtinsert3.close();
									/////////////////////////////////////////////////////////
									
									
						     }
					

						      
						 	///////////////////working on data in WAREHOUSE_PROFIT_LOSS /////
								//insert data into table WAREHOUSE_PROFIT_LOSS
						       					     
								stmtinsert2 = con.prepareStatement("insert into  WAREHOUSE_PROFIT_LOSS(ID,START_DATE,END_DATE,CREATION_DATE,LAST_MODIFY_DATE,WARE_ID,WARE_NAME,AREA_ID,AREA_NAME,CLUSTER_ID,CLUSTER_NAME,POPULATION,TECH_2G,TECH_3G,TECH_4G,TRANSMISSION,SITE_OWNER,TOWER_TYPE,TOWER_HEIGHT,UTILIZATION2G,UTILIZATION3G,UTILIZATION4G,UTILIZATION5G,AVAILABILITY_2G,AVAILABILITY_3G,AVAILABILITY_4G,AVAILABILITY_5G,GROSS_ADS,COUNT_OF_SSO,CUSTOMER_BASE,DATA,VOICE_REVENU,SMS_REVENU,GROSS_REVENU,DATA_TRAFFIC,TOTAL_SMS_TRAFFIC_OG,TOTAL_VOICE_TRAFFIC_OG,TOTAL_SMS_TRAFFIC_IC,TOTAL_VOICE_TRAFFIC_IC,TOTAL_OPEX_MONTHLY,PROFIT_AND_LOSS,SITE_ID) "
										+ "values ('" + Gyear +"' ||'_WAREPROFIT_'|| WARE_PROFIT_LOSS_SEQ.nextval,'"+ vstartdate +"' ,'"+ venddate +"',sysdate,sysdate,'"+ vwareid +"','"+ varsitename +"','"+ vareadid +"','"+ vareadname +"','"+ varclusterid +"','"+ varclustname +"','"+ varpopulation +"','"+ vartech2 +"','"+ vartech3 +"','"+ vartech4 +"','"+ vartransmission +"','"+ varsiteowner +"','"+ vartowertype +"','"+ vartowerheiht +"','"+ varutil2G +"','"+ varutil3G +"','"+ varutil4G +"','0','"+ varavlbl2G +"','"+ varavlbl3G +"','"+ varavlbl4G +"','0','"+ vargads +"','"+ varcsso +"','"+ varcustbase +"','"+ vrdata +"','"+ varrevenue +"','"+ varsmsrevenue +"','"+ varGrevenue +"','"+ vardatatraffic +"','"+ varsmstog +"','"+ varvtog +"','"+ varsmstic +"','"+ varvtic +"','"+ varopexmonth +"','"+ varpl +"','"+ varsiteid +"')");
								stmtinsert2.executeUpdate();
								stmtinsert2.close();
								
								/////////////////////////////////////////////////////////
								
								

		}

}
