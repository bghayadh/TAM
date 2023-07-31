package com.aliat.alm.Parser;

 

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

 

public class connectSQL {
     

	static BufferedReader objReader1 = null;
	static String strCurrentLine1;
	static String db1path;
	static String dbname;
	static String username1;
	static String password1;

    
    public static void main(String[] args) throws Exception {
        
        System.out.println(" hi all");
        
        String r = "";
        
        objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\"+"sqlconfig.dat"));
		 while ((strCurrentLine1 = objReader1.readLine()) != null){
			 String data = strCurrentLine1;
			 String[] data1 ;
			 String[] data2 ;
			
			 if (data.contains("sqldbpath")) {
				 data1=data.split(";",-1);
				 db1path=data1[1];
				 dbname=data1[2];
				 username1=data1[3];
				 password1=data1[4];
				 db1path = db1path+";databaseName="+dbname;
				// System.out.println("db1path found :" + db1path+" dbname: "+dbname+" username1: "+username1+" password1:"+password1);
			 }
		}
		 objReader1.close();
     
        //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        //    //BISSO\\SQLALM     BISSO is the hostname    ;databaseName=test     SQLALM is the main SQLservername   test is our Database
	    DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
        try (Connection connection = DriverManager.getConnection(db1path,username1,password1);) 
        {
            
            System.out.println(" connected to SQL");
            

            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT NAME FROM titi");

 

            while (rs.next()) {
                  String lastName = rs.getString("NAME");
                  System.out.println(lastName + "\n");
                }

 

            rs.close();
            stmt.close();
            // Code here.
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            System.out.println(e.toString());
            e.printStackTrace();
            System.out.println(" NOTTTT   connected");
        }
        

 

     
    }

 

}