package com.aliat.alm.Reports;
import java.io.BufferedReader;
import java.io.FileReader;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.mail.Session;

import org.apache.commons.lang3.StringUtils;

import com.jcraft.jsch.JSch;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Get_Warehouse_WIN {

static Connection con,connectionsql,conalm ;
static Logger logger;
static FileHandler fh;
static BufferedReader objReader1 = null;
static String strCurrentLine1;
static String logpath;
static String db1path;
static String username1;
static String password1;
static String db2path;
static String username2;
static String password2;
static String logsid="0";
static String Gyear;
static String Gcellid="0";
static int nbOfErrors = 0;
    static String VPARTKEY="0";
    static String validatewarehouse="0";
    static String sqlpath="0";
    static String vartech=null;
    static String tech2g="0";
    static String tech3g="0";
    static String tech4g="0";
    static String tech5g="0";
    static String gsitecluster="0";
    static com.jcraft.jsch.Session session;
     
    public static void main(String[] args) throws Exception {
   ResultSet resultSet = null;
   ResultSet resultSetcell = null;
   ResultSet resultSetcluster = null;
   
   
 //get only year from today date
DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
LocalDateTime now1 = LocalDateTime.now();
Gyear=dtf1.format(now1).substring(0,4);
System.out.println(Gyear);





   
   //connect to oracle.
try {
objReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\"+"almconfig.dat"));
     
while ((strCurrentLine1 = objReader1.readLine()) != null){
String data = strCurrentLine1;
String[] data1 ;
if (data.contains("logpath")) {
data1=data.split(";",-1);
logpath=data1[1];
//System.out.println("logpath found :" + logpath);
}
if (data.contains("db3path")) {
data1=data.split(";",-1);
db1path=data1[1];
username1=data1[2];
password1=data1[3];
}

if (data.contains("db1path")) {
data1=data.split(";",-1);
db2path=data1[1];
username2=data1[2];
password2=data1[3];
//System.out.println("db1path found :" + db1path);
}

if (data.contains("sqlpath::")) {
data1=data.split("::",-1);
sqlpath=data1[1];
//System.out.println("sqlpath found :" + sqlpath);
}

}
objReader1.close();
}
catch(Exception e){
   System.err.println(e);
   e.printStackTrace();
   logger.info("Error : "+e);
}



//// way to connect using SSH in case of we are running it in LINUX

String systm="WIN";
if (systm !="WIN" ) {


String sshHost = "10.22.28.33";
String sshuser = "oracle";
String passwrd="admin1234";
int localPort = 22;
String remoteHost = "10.22.28.33";
int remotePort = 1523;
String usr = "alm";
String pwd = "alm";
String SshKeyFilepath =
   "pathOfKey";

try {
session = new JSch().getSession(sshuser, sshHost, localPort);
   session.setPassword(passwrd);
   session.setConfig("StrictHostKeyChecking", "no");
   session.connect();

System.out.println("SSH Connected ...");
int assinged_port =
session.setPortForwardingL(localPort, remoteHost, remotePort);
System.out.println("localhost:" + assinged_port + " -> " +
              remoteHost + ":" + remotePort);
System.out.println("Port Forwarded ...");

try {
   Class.forName("oracle.jdbc.pool.OracleDataSource");

   conalm =
DriverManager.getConnection(db2path,username2,password2);
   System.out.println("Connected to ALM oracle DB");
} catch (SQLException ex) {
   ex.printStackTrace();
   System.out.println("Opss, error connect to ALM");
}


} catch (Exception e) {
System.out.println("Not Connected to SSH ...");
}

}

else {
try {
   Class.forName("oracle.jdbc.pool.OracleDataSource");

   conalm =
DriverManager.getConnection(db2path,username2,password2);
   System.out.println("Connected to ALM oracle DB");
} catch (SQLException ex) {
   ex.printStackTrace();
   System.out.println("Opss, error connect to ALM");
}
}







  //conect to sqlserver
   Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
   //try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://192.168.56.1\\SQLALM:1433;databaseName=test;user=sa;password=admin1234");) {
   // try (Connection connection = DriverManager.getConnection("jdbc:sqlserver://BISSO\\SQLALM:1433;databaseName=test;user=sa;password=admin1234");) {
 

try
   {
   
   //String url = "jdbc:sqlserver://10.22.28.23\\FSTSQLBDD:1433;DatabaseName=dwh_tkl;encrypt = true;trustServerCertificate=true;user=katelecomuser;password=K@t3l4comuser@2021";  
System.out.println("Read sqlserver from almconfig");
   String url=sqlpath.toString();
   
   //String url = "jdbc:sqlserver://10.22.28.23\\FSTSQLBDD:1433;DatabaseName=dwh_tkl;user=katelecomuser;password=K@t3l3comuser@2021";    
 
   //String url = "jdbc:sqlserver://192.168.0.170\\SQLVALM:1433;DatabaseName=vtest;encrypt = true;trustServerCertificate=true;user=alm;password=alm12345";
   //String url = "jdbc:sqlserver://192.168.0.192\\SQLALM:1433;DatabaseName=test1;encrypt = true;trustServerCertificate=true;user=alm;password=alm";
   connectionsql = DriverManager.getConnection(url);

   System.out.println(" connected to MS SQLServer");
   
   }
// Handle any errors that may have occurred.
catch (Exception e) {
System.out.println(e.toString());
   e.printStackTrace();
   System.out.println(" Error: Not connected to MS SQLServer");
}





//get all distinct Site_Cluster to get all warehouse
Statement statementcluster = connectionsql.createStatement();
       String selectSqlcluster="select distinct [Site_Cluster] from [dbo].[GSM_CELL_ID_NEW] where [Site_Cluster]  <>''";
 
       resultSetcluster = statementcluster.executeQuery(selectSqlcluster);
       while (resultSetcluster.next()) {
       
       
        //site cluster one by one get the related site and warehouse
          System.out.println("Value of Site_Cluster "+ resultSetcluster.getString(1));
         
         
          //remove ' from cluster_id
          String clusterid=resultSetcluster.getString(1);
          String newclusterid="0";
          gsitecluster=resultSetcluster.getString(1);
        int  m = 0;
        m = clusterid.indexOf("'");
        while (m>0) {
//System.out.println(""+clusterid.substring(0, m));
//System.out.println(""+clusterid.substring(m+1, clusterid.length()));
newclusterid=clusterid.substring(0, m) +clusterid.substring(m+1, clusterid.length());
gsitecluster=clusterid.substring(0, m) +"''"+clusterid.substring(m+1, clusterid.length());
clusterid=newclusterid;
//System.out.println(clusterid);
m = clusterid.indexOf("'");
        }
         
         
         
         
         
        Statement statementcell = connectionsql.createStatement();
       //String selectSqlcell="select distinct [Site_ID],[SITE_NAME],[SALES_AREA],[DATA_CELL_TYPE],[LONGITUDE],[LATITUDE],[Kenya Provinces ],[Site_Cluster],[Commercial Sites ] from [dbo].[GSM_CELL_ID_NEW] where Site_Cluster='Nyeri Town'";
       String selectSqlcell="select distinct t.Site_id,t.SITE_NAME,SALES_AREA,'tech',LONGITUDE,LATITUDE,[Kenya Provinces ],[Site_Cluster],[Commercial Sites ] from\r\n" +
        "(select distinct [Site_ID],[SITE_NAME],[SALES_AREA],[DATA_CELL_TYPE],[LONGITUDE],[LATITUDE],[Kenya Provinces ],[Site_Cluster],[Commercial Sites ] from [dbo].[GSM_CELL_ID_NEW] where Site_Cluster='" + gsitecluster + "') t where t.[Site_ID]  <>''";
 
 resultSetcell = statementcell.executeQuery(selectSqlcell);
       while (resultSetcell.next()) {
        //System.out.println("site id is " +resultSetcell.getString(2));
       
        //remove _ and ' from siteid    
        String vsiteid=resultSetcell.getString(2);
        String newsiteid="0";
        int n = 0;
       
       
        newsiteid="0";
        n = 0;
        n = vsiteid.indexOf("'");
        while (n>0) {
//System.out.println(""+vsiteid.substring(0, n));
//System.out.println(""+vsiteid.substring(n+1, vsiteid.length()));
newsiteid=vsiteid.substring(0, n) +vsiteid.substring(n+1, vsiteid.length());
vsiteid=newsiteid;
//System.out.println(newsiteid);
n = vsiteid.indexOf("'");
        }
       
        //get combination tech per site
        GET_TECHNOLOGY_BY_SITE(vsiteid,resultSetcell.getString(3),resultSetcell.getString(5),resultSetcell.getString(6),resultSetcell.getString(1),resultSetcell.getString(7),gsitecluster,resultSetcell.getString(9));
       
        //validate if warehouse exist in warehouse if no add it else skip it
        VALIDATE_WAREHOUSE_IN_ALM(vsiteid,resultSetcell.getString(3),Float.parseFloat(resultSetcell.getString(5)),Float.parseFloat(resultSetcell.getString(6)),resultSetcell.getString(1),vartech,resultSetcell.getString(7),clusterid,resultSetcell.getString(9));
       
        if (StringUtils.equalsIgnoreCase (validatewarehouse,"0")) {
        // fillwarehouse from sqlserver
        //System.out.println("NEW NEW NEW " + resultSetcell.getString(3));
        FILL_WAREHOUSE_ALM(vsiteid,resultSetcell.getString(3),Float.parseFloat(resultSetcell.getString(5)),Float.parseFloat(resultSetcell.getString(6)),resultSetcell.getString(1),tech2g,tech3g,tech4g,tech5g,resultSetcell.getString(7),clusterid,resultSetcell.getString(9),vartech);
    }
       
       }
       
       
       
       
       
       
       
       }
       resultSetcluster.close();
       statementcluster.close();
System.out.println("Warehouse data migration completed");
 
       
       
       
       connectionsql.close();
       con.close();
       conalm.close();
       session.disconnect();
   }
   
   
   //read from sqlserver and fil in ALM_WAREHOUSE
    private static void FILL_WAREHOUSE_ALM(String sitename,String salesarea,float lng,float lat,String siteid,String tech2g,String tech3g,String tech4g,String tech5g,String kynprov,String sitecluster,String commsites,String combtech) throws SQLException  {

    String wareid="0";
   
      // Get cellprimary key from sequence
        wareid="WARE_"+Gyear+"_"+localgetseqNbr(0);
   
    try {
       
   
   
    System.out.println("insert into WAREHOUSE (WARE_ID,CREATION_DATE,LAST_MODIFY_DATE,WARE_NAME,CITY,LONGITUDE,LATITUDE,SITE,SITE_ID,TECH_2G,TECH_3G,TECH_4G,TECH_5G,AREA_ID,AREA_NAME,ADDRESS,CLUSTER_ID,CLUSTER_NAME,STATUS,COMBINATION_TECHNOLOGY)"
    + "values( '" + wareid +"',sysdate,sysdate,'" + sitename +"','" + salesarea +"'," + lng +"," + lat +",'1','" + siteid +"','" + tech2g +"','" + tech3g +"','" + tech4g +"','" + tech5g +"','0','" + salesarea +"','" + kynprov +"','0','" + sitecluster +"','" + commsites +"', '" + combtech +"')");
     
    PreparedStatement insert_bundlerevenue = conalm.prepareStatement("insert into WAREHOUSE (WARE_ID,CREATION_DATE,LAST_MODIFY_DATE,WARE_NAME,CITY,LONGITUDE,LATITUDE,SITE,SITE_ID,TECH_2G,TECH_3G,TECH_4G,TECH_5G,AREA_ID,AREA_NAME,ADDRESS,CLUSTER_ID,CLUSTER_NAME,STATUS,COMBINATION_TECHNOLOGY)"
      + "values( '" + wareid +"',sysdate,sysdate,'" + sitename +"','" + salesarea +"'," + lng +"," + lat +",'1','" + siteid +"','" + tech2g +"','" + tech3g +"','" + tech4g +"','" + tech5g +"','0','" + salesarea +"','" + kynprov +"','0','" + sitecluster +"','" + commsites +"', '" + combtech +"')");
       insert_bundlerevenue.executeUpdate();
       insert_bundlerevenue.close();
      }  catch (Exception e) {
          System.out.println(e.toString());
          System.out.println(" Error: GetDatafrombundle_revenue insert to Oracle PREPAID_PAYG_REVENUE");
      }
   
    }
   
   
  //validate WAREHOUSE in ALM
    private static void VALIDATE_WAREHOUSE_IN_ALM(String sitename,String salesarea,float lng,float lat,String siteid,String datacelltype,String kynprov,String sitecluster,String commsites) throws SQLException  {
     Statement stmt30 = null;
     ResultSet resultSet = null;
  tech2g="0";
    tech3g="0";
    tech4g="0";
    tech5g="0";

    String vteccomb=null;
   
    validatewarehouse="0";
    vteccomb=datacelltype;
   
    //split combtech
    String[] data1 ;
   
    if (StringUtils.equalsIgnoreCase (vteccomb,null)) {
   
    } else {
    data1=vteccomb.split("_",-1);
    for (int i=0; i < (data1.length);i++) {
    if (StringUtils.equalsIgnoreCase (data1[i],"2G")) {
    tech2g="1";
    }
        if (StringUtils.equalsIgnoreCase (data1[i],"3G")) {
    tech3g="1";
    }
    if (StringUtils.equalsIgnoreCase (data1[i],"4G")) {
    tech4g="1";
    }
    if (StringUtils.equalsIgnoreCase (data1[i],"5G")) {
    tech5g="1";
    }
    }
    }
   
try {
          // get last date insterted into revenue table
//System.out.println("select * from  WAREHOUSE where WARE_NAME='"+ sitename +"' and CITY='"+ salesarea +"' and LONGITUDE="+ lng +" and LATITUDE="+ lat +" and SITE_ID='"+ siteid +"' and TECH_2G='"+ tech2g +"' and TECH_3G='"+ tech3g +"' and TECH_4G='"+ tech4g +"' and TECH_5G='"+ tech5g +"' and AREA_NAME='"+ salesarea +"' and ADDRESS='"+ kynprov +"' and CLUSTER_ID='"+ sitecluster +"' and STATUS='"+ commsites +"' ");
      //String query3 = "select * from  WAREHOUSE where WARE_NAME='"+ sitename +"' and CITY='"+ salesarea +"' and LONGITUDE="+ lng +" and LATITUDE="+ lat +" and SITE_ID='"+ siteid +"' and TECH_2G='"+ tech2g +"' and TECH_3G='"+ tech3g +"' and TECH_4G='"+ tech4g +"' and TECH_5G='"+ tech5g +"' and AREA_NAME='"+ salesarea +"' and ADDRESS='"+ kynprov +"' and CLUSTER_ID='"+ sitecluster +"' and STATUS='"+ commsites +"' ";  

    //System.out.println("select * from  WAREHOUSE where WARE_NAME='"+ sitename +"' and LONGITUDE="+ lng +" and LATITUDE="+ lat +" and SITE_ID='"+ siteid +"' ");
      String query3 = "select * from  WAREHOUSE where WARE_NAME='"+ sitename +"' and LONGITUDE="+ lng +" and LATITUDE="+ lat +" and SITE_ID='"+ siteid +"' ";  
   
      stmt30 = conalm.createStatement();
     
          ResultSet rs30 = stmt30.executeQuery(query3);
        while (rs30.next()) {  

           validatewarehouse="1";
           CHECK_WAREHOUSE_DATA_INFO(sitename,salesarea,lng,lat,siteid,tech2g,tech3g,tech4g, tech5g, kynprov, sitecluster, commsites,rs30.getString("WARE_ID"));

            }


          rs30.close();  
          stmt30.close();
         
}  catch (Exception e) {
      System.out.println(e.toString());
      System.out.println(" Error: VALIDATE_WAREHOUSE_IN_ALM");
  }
         
    }
   
    //GET_TECHNOLOGY_BY_SITE
    private static void GET_TECHNOLOGY_BY_SITE( String sitename,String salesarea,String lng,String lat,String siteid,String kynprov,String sitecluster,String commsites) throws SQLException  {
   Statement stmt3 = null;
   ResultSet resultSettech = null;
     vartech =null;
     try {
Statement statementtech = connectionsql.createStatement();

/*System.out.println("select distinct [DATA_CELL_TYPE] from [dbo].[GSM_CELL_ID_NEW] where\r\n" +
        "[Site_id]='" + siteid +"' and [SITE_NAME]='" + sitename +"' and [SALES_AREA]='" + salesarea +"' and [LONGITUDE]=" + lng +" and [LATITUDE]=" + lat +" \r\n" +
        "and [Kenya Provinces ]='" + kynprov +"' and [Site_Cluster]='" + sitecluster +"' and [Commercial Sites ]='" + commsites +"'"); */


        String selectSqltech="select distinct [DATA_CELL_TYPE] from [dbo].[GSM_CELL_ID_NEW] where\r\n" +
        "[Site_id]='" + siteid +"' and [SITE_NAME]='" + sitename +"' and [SALES_AREA]='" + salesarea +"' and [LONGITUDE]=" + lng +" and [LATITUDE]=" + lat +" \r\n" +
        "and [Kenya Provinces ]='" + kynprov +"' and [Site_Cluster]='" + sitecluster +"' and [Commercial Sites ]='" + commsites +"'";
       
       
       
 
 resultSettech = statementtech.executeQuery(selectSqltech);
        while (resultSettech.next()) {
               
        if (StringUtils.equalsIgnoreCase (vartech,null)) {
        vartech= resultSettech.getString(1);
        } else {
        vartech=vartech + "_"+resultSettech.getString(1);
        }
       

        }
        resultSettech.close();
        statementtech.close();
        //System.out.println("GET_TECHNOLOGY_BY_SITE  vartech is " +vartech);

   }  catch (Exception e) {
      System.out.println(e.toString());
      System.out.println(" Error: GET_TECHNOLOGY_BY_SITE");
  }
       
  }
   
   
    //CHECK_WAREHOUSE_DATA_INFO()
    private static void CHECK_WAREHOUSE_DATA_INFO(String sitename,String salesarea,float lng,float lat,String siteid,String tech2g,String tech3g,String tech4g,String tech5g,String kynprov,String sitecluster,String commsites,String wareid) throws SQLException  {

   
    try {
       
         
    PreparedStatement update_bundlerevenue = conalm.prepareStatement("update WAREHOUSE  set LAST_MODIFY_DATE=sysdate, CITY='" + salesarea +"',TECH_2G='"+ tech2g+ "',TECH_3G='"+ tech3g+ "',TECH_4G='"+ tech4g+ "',TECH_5G='"+ tech5g+ "'  ,AREA_NAME='" + salesarea +"',ADDRESS='" + kynprov +"',CLUSTER_NAME='" + sitecluster + "',COMBINATION_TECHNOLOGY='"+ vartech +"' where WARE_ID='"+ wareid +"'");
       
    update_bundlerevenue.executeUpdate();
    update_bundlerevenue.close();
      }  catch (Exception e) {
          System.out.println(e.toString());
          System.out.println(" Error: CHECK_WAREHOUSE_DATA_INFO update to Oracle CHECK_WAREHOUSE_DATA_INFO");
      }
   
    }
   
    private static String localgetseqNbr(int n1) throws SQLException {
        String min = null ;

    Statement stmt2 = null;
    Statement stmttype = null;
    Statement stmtdetail = null;  
         
             
           String SeqName = null;
        switch(n1)
        {
        case 0:
    SeqName = "WAREHOUSE_SEQ";
    break;

        }
          String query2 = "select "+SeqName+".nextval as nbr from dual";      
              stmttype = conalm.createStatement();
              ResultSet rs2 = stmttype.executeQuery(query2);
             
              while (rs2.next()) {
               min= rs2.getString("nbr");    
              }
              rs2.close();

              stmttype.close();

    return min;

      }
   
   
}