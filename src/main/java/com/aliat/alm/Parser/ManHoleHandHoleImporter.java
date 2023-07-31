package com.aliat.alm.Parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.aliat.alm.models.HandHole;
import com.aliat.alm.models.ManHole;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

public class ManHoleHandHoleImporter {
	
	public List<ManHole> excelImport() throws ApiException, InterruptedException, IOException{
//		public List<HandHole> excelImport() throws ApiException, InterruptedException, IOException{
			List<ManHole> listManH=new ArrayList<>();
			//List<HandHole> listManH=new ArrayList<>();
			
			String ID = null;
			//String MHDmName = null;
			String MHModel = null;
			String ManholeDmName = null;
			String ManhHandName = null;
			Double longitude = null;
			Double latitude = null;
			String city = null;
			int nb = 0;
			int ManholeCount =0;
			
			Date date = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);
			//String excelFilePath="D:\\Work\\Backbone & Metro & Access-TKL Fiber 2021_Coordinates.xlsx";
			//String excelFilePath="C:\\Users\\Lillian\\Desktop\\KENYA FILES\\Backbone & Metro & Access-TKL Fiber 2021_Coordinates.xlsx";
			String excelFilePath="C:\\Users\\User\\Desktop\\Data\\Backbone & Metro & Access-TKL Fiber 2021_Coordinates.xlsx";

			
			long start = System.currentTimeMillis();


			FileInputStream inputStream;
			try {
				inputStream = new FileInputStream(excelFilePath);
				Workbook workbook=new XSSFWorkbook(inputStream);
				Sheet firstSheet=workbook.getSheet("NAME");
				Iterator<Row> rowIterator=firstSheet.iterator();
				long end;
				Row nextRow = rowIterator.next();
				while(rowIterator.hasNext()) {
					 nextRow = rowIterator.next();
					Iterator<Cell> cellIterator=nextRow.cellIterator();
					int rowIndex = nextRow.getRowNum();
					Row sheetRow = firstSheet.getRow(rowIndex);
					Cell Str = sheetRow.getCell(1);
					String subStr = Str.getStringCellValue();
				
				if(!subStr.contains("HH") == true) {

					while(cellIterator.hasNext()) {
						Cell nextCell=cellIterator.next();
						int columnIndex=nextCell.getColumnIndex();
						switch (columnIndex) {
						case 0:
							ManholeCount++;
							ID = (String) ("MH_" + year + "_" +ManholeCount);
							nb = (int) nextCell.getNumericCellValue();
							break;
						case 1:
							ManholeDmName=nextCell.getStringCellValue();
							//ManhHandName=nextCell.getStringCellValue();
							break;
						case 2:
							longitude=nextCell.getNumericCellValue();
							break;
						case 3:
							latitude=nextCell.getNumericCellValue();
							city =	parseResult(latitude, longitude);
							break;
						}
					
						
						}
					System.out.println(city);
					String manholeName ="";
					if(ManholeDmName.contains("J") == true){
						manholeName = "MH_"+city+"_"+year+"_"+ManholeCount+"_J";				
	
					}
					else {
						manholeName = "MH_"+city+"_"+year+"_"+ManholeCount;				
					}
					listManH.add(new ManHole(ID, manholeName, MHModel, longitude, latitude,city,"CurrentPhysicalLayer",ManholeDmName));				
					
					//HH_J_CityName_2022_1
					
					}
				}
				
				end = System.currentTimeMillis();
				System.out.printf("Import done in %d ms\n", (end - start));
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			return listManH;
		}
		public List<HandHole> excelHImport() throws ApiException, InterruptedException, IOException{
			List<HandHole> listManH=new ArrayList<>();
					
					String ID = null;
					//String MHDmName = null;
					String MHModel = null;
					String HandholeName = null;
					String HandoleDmName = null;
					Double longitude = null;
					Double latitude = null;
					String city = null;
					int nb = 0;
					int HandholeCount=0;
					
					Date date = new Date();
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(date);
					int year = calendar.get(Calendar.YEAR);
					//String excelFilePath="D:\\Work\\Backbone & Metro & Access-TKL Fiber 2021_Coordinates.xlsx";
					//String excelFilePath="C:\\Users\\Lillian\\Desktop\\KENYA FILES\\Backbone & Metro & Access-TKL Fiber 2021_Coordinates.xlsx";
					String excelFilePath="C:\\Users\\User\\Desktop\\Data\\Backbone & Metro & Access-TKL Fiber 2021_Coordinates.xlsx";

					
					long start = System.currentTimeMillis();


					FileInputStream inputStream;
					try {
						inputStream = new FileInputStream(excelFilePath);
						Workbook workbook=new XSSFWorkbook(inputStream);
						Sheet firstSheet=workbook.getSheet("NAME");
						Iterator<Row> rowIterator=firstSheet.iterator();
						long end;
						Row nextRow = rowIterator.next();
						while(rowIterator.hasNext()) {
							 nextRow = rowIterator.next();
							Iterator<Cell> cellIterator=nextRow.cellIterator();
							int rowIndex = nextRow.getRowNum();
							Row sheetRow = firstSheet.getRow(rowIndex);
							Cell Str = sheetRow.getCell(1);
							String subStr = Str.getStringCellValue();
							
							if(subStr.contains("HH") == true) {	

							while(cellIterator.hasNext()) {
								Cell nextCell=cellIterator.next();
								int columnIndex=nextCell.getColumnIndex();
								switch (columnIndex) {
								case 0:
									HandholeCount++;
									ID = (String) ("HH_" + year + "_" +HandholeCount);
									nb = (int) nextCell.getNumericCellValue();
									break;
								case 1:
									HandoleDmName=nextCell.getStringCellValue();
									//ManhHandName=nextCell.getStringCellValue();
									break;
								case 2:
									longitude=nextCell.getNumericCellValue();
									break;
								case 3:
									latitude=nextCell.getNumericCellValue();
									city =	parseResult(latitude, longitude);
									break;
								}
							
								
								}
							System.out.println(city);
							if(HandoleDmName.contains("J") == true){
								HandholeName = "HH_"+city+"_"+year+"_"+HandholeCount+"_J";				
			
							}
							else {
								HandholeName = "HH_"+city+"_"+year+"_"+HandholeCount;				
							}
							//HandholeName = "HH_"+city+"_"+HandholeCount;
							listManH.add(new HandHole(ID, HandholeName, MHModel, longitude, latitude,city,"CurrentPhysicalLayer",HandoleDmName)); 
							
							//HH_J_CityName_2022_1
							
							}
						}
						
						end = System.currentTimeMillis();
						System.out.printf("Import done in %d ms\n", (end - start));
						
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					
					return listManH;
		}
		
		private String parseResult(Double latitude, Double longitude) {
			
			String city = "";
			GeoApiContext context = new GeoApiContext.Builder()
				    .apiKey("AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo")
				    .build();
				GeocodingResult[] results = null;
				try {
					results =  GeocodingApi.newRequest(context).latlng(new LatLng(latitude, longitude)).language("en").resultType(AddressType.COUNTRY, AddressType.ADMINISTRATIVE_AREA_LEVEL_4).await();
					city = (results[0].formattedAddress.split(","))[0];
					if(city.contains("'")) city = (city.split("'"))[0];
				} catch (ApiException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				// Invoke .shutdown() after your application is done making requests
				context.shutdown();
			return city;
		
		}
	


}
