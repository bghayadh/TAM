package com.aliat.alm.DM;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DMmoveIT2ITEM {
	
	public static void main(String[] args) {

        // move DM DM_APPLIANCE,DM_DEVICEINSTACK ,DM_FIREWALL to ITEM
        try{

			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
	         Connection con = DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:alm","alm","alm");
	         
	         Statement stmt2 = null;
	         Statement stmttype = null;
	         Statement stmtdetail = null;    
	            

                    //move DM_APPLIANCE to ITEM table
					PreparedStatement stmt = con.prepareStatement("insert into Item (select distinct * from DM_APPLIANCE )");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("1-IT- DM_APPLIANCE moved to Item");
			        
			        //move DM_DEVICEINSTACK to ITEM table 
			        stmt = con.prepareStatement("insert into Item (select distinct * from DM_DEVICEINSTACK )");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("2-IT- DM_DEVICEINSTACK moved to Item");
			        
			        //move DM_FIREWALL to ITEM table
			        stmt = con.prepareStatement("insert into Item (select distinct * from DM_FIREWALL )");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("3-IT- DM_FIREWALL moved to Item");
			        
			        //move DM_Layer3Switch to ITEM table
			        stmt = con.prepareStatement("insert into Item (select distinct * from DM_Layer3Switch )");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("4-IT- DM_Layer3Switch moved to Item");

			        //move DM_NetworkInfrastructure to ITEM table
			        stmt = con.prepareStatement("insert into Item (select distinct  *  from DM_NetworkInfrastructure  where ITEM_CAT_CODE is not null and rownum =1)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("5-IT- DM_NetworkInfrastructure moved to Item");
			        
			        //move DM_SANSwitchSwitch to ITEM table
			        stmt = con.prepareStatement("insert into Item (select distinct * from DM_SANSwitchSwitch )");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("6-IT- DM_SANSwitchSwitch moved to Item");

			       //move DM_SwitchSANSwitch to ITEM table
			        stmt = con.prepareStatement("insert into Item (select distinct  *  from DM_SwitchSANSwitch  where ITEM_CAT_CODE is not null and rownum =1)");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("7-IT- DM_SwitchSANSwitch moved to Item");
			        
			        //PASSIVE   move DM_Esim to ITEM table
			        stmt = con.prepareStatement("insert into Item (select distinct  *  from DM_Esim  )");
				    stmt.executeUpdate();
			        stmt.close();
			        System.out.println("1-PASSIVE- DM_Esim moved to Item");
			        
			      //PASSIVE   move DMFixedFiberNetworkDARKFIBER to ITEM table
			        stmt = con.prepareStatement("insert into Item (select distinct *  from DM_FixedFiberNetworkDARKFIBER where  item_code ='FFN-IT-OPR-SSY-CBS' )");
				    stmt.executeUpdate();
			        stmt.close();
			        
			        stmt = con.prepareStatement("insert into Item (select distinct *  from DM_FixedFiberNetworkDARKFIBER where  item_code ='DMFixedFiberNetworkDARKFIBER' and rownum=1 )");
				    stmt.executeUpdate();
			        stmt.close();
			        
			        stmt = con.prepareStatement("insert into Item (select distinct *  from DM_FixedFiberNetworkDARKFIBER where  item_code ='FFN-NW-TRM-OPT-FIB' and rownum=1 )");
				    stmt.executeUpdate();
			        stmt.close();
			        
			        System.out.println("2-PASSIVE- DMFixedFiberNetworkDARKFIBER moved to Item");
			        
			      //PASSIVE   move DM_OfficeEquipment to ITEM table
			        stmt = con.prepareStatement("insert into Item (select distinct * from DM_OfficeEquipment where item_model is null )");
				    stmt.executeUpdate();
			        stmt.close();
			        
			        stmt = con.prepareStatement("insert into Item (select distinct * from DM_OfficeEquipment where item_code ='OEQ-Office equipment_Shredder-Passive-Data'  )");
				    stmt.executeUpdate();
			        stmt.close();
			        
			        System.out.println("3-PASSIVE- DM_OfficeEquipment moved to Item");
	
			con.close();
			
	        }   
	        catch(Exception e){
	  	      System.err.println(e);
	  	      e.printStackTrace();
	  	   }
          
		}

}
