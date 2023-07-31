package com.aliat.alm.models;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileWriter;   // Import the FileWriter class
public class ReadXlsUsingPOI {

	public static void main(String[] args) {
		 
		

		FileInputStream inputStream = null;
		Workbook workbook = null;
		try {
	      
    
		      
			String excelFilePath = "C:\\parser\\student.xls";
			inputStream = new FileInputStream(new File(excelFilePath));
 
			// Create a Workbook from the file input stream
			workbook = new XSSFWorkbook(inputStream);
 
			// Get the First sheet from the wrokbook
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();
 
			while (iterator.hasNext()) {
				// Get the Row that we need to read
				Row nextRow = iterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();
 
				while (cellIterator.hasNext()) {
					// Get the Cell to get data from it
					Cell cell = cellIterator.next();
 
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						System.out.print(cell.getStringCellValue());
					    break;
					case Cell.CELL_TYPE_BOOLEAN:
						System.out.print(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						System.out.print(cell.getNumericCellValue());
						break;
					}
					System.out.print(" - ");
				}
				System.out.println();
			}
			
		} catch (Exception e) {
			System.out.println("Exception while reading Excel " + e);
		} finally {
			try {
				if (workbook != null) {
					//workbook.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
 
			} catch (IOException e) {
			}
 
		}
 
	}

	
}
