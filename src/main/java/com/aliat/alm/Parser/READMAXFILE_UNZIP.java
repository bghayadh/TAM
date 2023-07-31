package com.aliat.alm.Parser;
import org.apache.commons.io.FileUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.Spring;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class READMAXFILE_UNZIP {
	public static String folderpath="";
	public static long vmax=0;
	public static String vmaxfoldername="";
	
	public static void main(String[] args) throws SecurityException, IOException, InterruptedException, SQLException, ParseException {
		
		GetMaxdirectoryName();
		System.out.println(folderpath+vmaxfoldername);
		GetFolderAndUnzipfile(folderpath+vmaxfoldername);
		//ArrayTree();
	}
	
	public static  void GetMaxdirectoryName (){
		String[] pr;
		String str;
		long lng=0;
		
		//set path to read from 
		File folder = new File("C:\\Dumps\\readmaxtime\\");
		
		//File[] directories = (new File("C:\\Dumps\\readmaxtime\\").listFiles(File::isDirectory));
		File[] directories = (folder.listFiles(File::isDirectory));
		for (int temp = 0; temp < directories.length; temp++) {
			str=directories[temp].toString();
			pr =str.split("\\\\",-1);
			System.out.println(pr[(pr.length)-1]);
			lng=GetNumberFromString(str);
			if (vmax <lng ) { // to define biggest long number
				folderpath="";
				vmax=lng; 
				vmaxfoldername=pr[(pr.length)-1]; // to read bigest folder name
				for (int temp1 = 0; temp1 < directories.length-2; temp1++) {
					if (temp1 == 0) { folderpath=folderpath.trim()+ pr[temp1]+"\\\\";} else {
					folderpath=folderpath + pr[temp1]+"\\\\"; // to get path of biggest foldername
					}
				}//end for
			}//end if vmax
		}
		System.out.println("Maximun folder is :  "+vmaxfoldername); 
		System.out.println("Maximun is :  "+vmax); 
		System.out.println("folder path is :  "+folderpath); 
		
	}
	
	public static  void ArrayTree (){

        int[] arr = {13, 7, 6, 45, 21, 9, 101, 102};
        Arrays.sort(arr);
        System.out.printf("Modified arr[] : %s",  Arrays.toString(arr));
 
	}

	public static  long GetNumberFromString (String str){
          long vcount=0;
		  //String str="sdfvsdf68fsdfsf8999fsdf09";
		   String numberOnly= str.replaceAll("[^0-9]", "");
		   System.out.println(numberOnly);
		   vcount= Long.parseLong (numberOnly);
		return vcount;
 
	}

	public static  void GetFolderAndUnzipfile (String foldr){
		String vstr;
		String vstr1;
		File folder = new File(foldr);
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				if (file.getName().toString().contains("gz"))  {
					vstr=file.getName().toString();
					System.out.println(vstr);
					vstr1=vstr.substring(0, (vstr.length()-3)).trim();
					System.out.println(vstr1);
					unzip(folderpath+vmaxfoldername+"\\\\"+file.getName().toString() ,folderpath+vmaxfoldername+"\\\\"+vstr1);
				}
		    }
		}
       
	}
	
	private static void unzip(String zipFilePath, String destDir) {
		System.out.println("read file gz "+ zipFilePath);
		System.out.println("moved to folder "+ destDir);
		byte[] buffer = new byte[1024];
		 
        try {
 
            FileInputStream fileIn = new FileInputStream(zipFilePath);
 
            GZIPInputStream gZIPInputStream = new GZIPInputStream(fileIn);
 
            FileOutputStream fileOutputStream = new FileOutputStream(destDir);
 
            int bytes_read;
 
            while ((bytes_read = gZIPInputStream.read(buffer)) > 0) {
 
                fileOutputStream.write(buffer, 0, bytes_read);
            }
 
            gZIPInputStream.close();
            fileOutputStream.close();
 
            System.out.println("The file was decompressed successfully!");
 
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
        
    }

