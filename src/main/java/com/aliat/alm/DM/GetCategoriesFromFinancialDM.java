package com.aliat.alm.DM;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


import org.apache.commons.lang3.StringUtils;


public class GetCategoriesFromFinancialDM {

	public static void main(String[] args) {

        String str = null;
	    String pass=null;
	    String strpwd = null;
	    String struser = null;
	     
	    Statement stmnt = null;
	    String selectQuery = null;
	    ResultSet getseqRes = null;
	    ResultSet getResult = null;
	    
        try{

        	pass=System.getProperty("user.dir")+"\\target\\classes\\hibernate.cfg.xml";
		    File file = new File(pass);
		    FileInputStream fis = null;
			BufferedInputStream bis = null;
			DataInputStream dis = null;
			     
			fis = new FileInputStream(file);
		
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);
			String sVerifyText="jdbc:oracle:thin:";
			String spasswrd="hibernate.connection.password";
			String susename="hibernate.connection.username";
			while (dis.available() != 0) {
				String data = dis.readLine();

              if (data.contains(sVerifyText)) {	
                  int index = data.indexOf('>');
                  str=data.substring(index+1, data.length());
                  index = str.indexOf('<');
                  str=str.substring(0, index);
              }
              if (data.contains(spasswrd)) {	
                  int index = data.indexOf('>');
                  strpwd=data.substring(index+1, data.length());
                  index = strpwd.indexOf('<');
                  strpwd=strpwd.substring(0, index);
              }
              if (data.contains(susename)) {	
                  int index = data.indexOf('>');
                  struser=data.substring(index+1, data.length());
                  index = struser.indexOf('<');
                  struser=struser.substring(0, index);
              }          

			}
        	
	    	DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
			Connection con = DriverManager.getConnection (str,struser,strpwd);
	         
	        
	       
	            
			selectQuery = "select TREE_SEQ.nextval as nbr from dual";  
            stmnt = con.createStatement();
            getseqRes = stmnt.executeQuery(selectQuery);
            
            String CatIDL1 = null;
            while (getseqRes.next()) {
            	CatIDL1= getseqRes.getString("nbr"); 
			}
            getseqRes.close();
			stmnt.close();
	      
			
			
			String getRootCat = "SELECT t.title, t.lft, t.rgt FROM item_cat_tree t where t.id ='0' ";  
            stmnt = con.createStatement();
            ResultSet getRootCatRes = stmnt.executeQuery(getRootCat);
            
            int lftNodeRoot = 0;
            int rgtNodeRoot = 0;
            int rgtRoot = 0;
            int leftOfLastChild = 0;
            while(getRootCatRes.next()){
            	
                rgtRoot = Integer.parseInt(getRootCatRes.getNString("rgt"));
        		leftOfLastChild = rgtRoot - 1;
        		lftNodeRoot = leftOfLastChild + 1;  // lft of new cat
        		rgtNodeRoot = leftOfLastChild + 2;
            }	
            getRootCatRes.close();
            stmnt.close();
            
            PreparedStatement updtStmnt, InsertStmnt;
            ResultSet result;
            
            updtStmnt = con.prepareStatement("update item_cat_tree t SET t.rgt = (t.rgt+2) WHERE t.rgt > '"+leftOfLastChild+"'");
            updtStmnt.executeUpdate();
            updtStmnt.close();
			
            updtStmnt = con.prepareStatement("update item_cat_tree t SET t.lft = (t.lft+2) WHERE t.lft > '"+leftOfLastChild+"'");
            updtStmnt.executeUpdate();
            updtStmnt.close();
				
			InsertStmnt = con.prepareStatement("insert into item_cat_tree (ID,PARENT,TITLE,LFT,RGT,CODE,PARENTCODE,CREATION_DATE,LAST_MODIFIED_DATE,LVL,PARENTID,CAT1) "
					+ "values ('"+CatIDL1+"', 'All Categories', 'General','"+lftNodeRoot+"','"+rgtNodeRoot+"','GN','GN', sysdate,sysdate,'1','0','GN' ) ");
        	System.out.println("Insert C1 : insert into item_cat_tree (ID,PARENT,TITLE,LFT,RGT,CODE,PARENTCODE,CREATION_DATE,LAST_MODIFIED_DATE,LVL,PARENTID,CAT1) "
        			+ "values ('"+CatIDL1+"', 'All Categories', 'General','"+lftNodeRoot+"','"+rgtNodeRoot+"','GN','GN', sysdate,sysdate,'','1','0' ) ");
        	InsertStmnt.executeUpdate();
        	InsertStmnt.close();
        	
        	
        	selectQuery = "select TREE_SEQ.nextval as nbr from dual";  
        	stmnt = con.createStatement();
        	getseqRes = stmnt.executeQuery(selectQuery);
            
            String CatIDL2 = null;
            while (getseqRes.next()) {
            	CatIDL2= getseqRes.getString("nbr"); 
			}
            getseqRes.close();
			stmnt.close();
        	
        	selectQuery= "SELECT t.title, t.lft, t.rgt FROM item_cat_tree t where t.id ='"+CatIDL1+"' ";  
        	stmnt = con.createStatement();
        	result= stmnt.executeQuery(selectQuery);
            
            while(result.next()){
            	
            	int rgtL1 = Integer.parseInt(result.getNString("rgt"));
        		int leftOfLastChildL1 = rgtL1 - 1;
        		int lftNodeL2 = leftOfLastChildL1 + 1;  // lft of new cat
        		int rgtNodeL2 = leftOfLastChildL1 + 2;
        		
        		updtStmnt= con.prepareStatement("update item_cat_tree t SET t.rgt = (t.rgt+2) WHERE t.rgt > '"+leftOfLastChildL1+"'");
        		updtStmnt.executeUpdate();
        		updtStmnt.close();
    			
        		updtStmnt= con.prepareStatement("update item_cat_tree t SET t.lft = (t.lft+2) WHERE t.lft > '"+leftOfLastChildL1+"'");
        		updtStmnt.executeUpdate();
        		updtStmnt.close();
            	
            	InsertStmnt= con.prepareStatement("insert into item_cat_tree (ID,PARENT,TITLE,LFT,RGT,CODE,PARENTCODE,CREATION_DATE,LAST_MODIFIED_DATE,LVL,PARENTID,CAT1,CAT2) "
            			+ "values ('"+CatIDL2+"', 'General', 'Network','"+lftNodeL2+"','"+rgtNodeL2+"','NW','GN-NW', sysdate,sysdate,'2','"+CatIDL1+"','GN','NW' ) ");
                System.out.println("Insert C2 : insert into item_cat_tree (ID,PARENT,TITLE,LFT,RGT,CODE,PARENTCODE,CREATION_DATE,LAST_MODIFIED_DATE,LVL,PARENTID,CAT1,CAT2) "
                		+ "values ('"+CatIDL2+"', 'General', 'Network','"+lftNodeL2+"','"+rgtNodeL2+"','NW','GN-NW', sysdate,sysdate,'2','"+CatIDL1+"','GN','NW' ) ");
                InsertStmnt.executeUpdate();
                InsertStmnt.close();
            }
            result.close();
            stmnt.close();
            
            selectQuery = "select TREE_SEQ.nextval as nbr from dual";  
            stmnt = con.createStatement();
            getseqRes = stmnt.executeQuery(selectQuery);
            
            String CatIDL3 = null;
            while (getseqRes.next()) {
            	CatIDL3= getseqRes.getString("nbr"); 
			}
            getseqRes.close();
			stmnt.close();
        	
        	selectQuery= "SELECT t.title, t.lft, t.rgt FROM item_cat_tree t where t.id ='"+CatIDL2+"' ";  
        	stmnt = con.createStatement();
        	result= stmnt.executeQuery(selectQuery);
            
            while(result.next()){
            	
            	int rgtL2 = Integer.parseInt(result.getNString("rgt"));
        		int leftOfLastChildL2 = rgtL2 - 1;
        		int lftNodeL3 = leftOfLastChildL2 + 1;  // lft of new cat
        		int rgtNodeL3 = leftOfLastChildL2 + 2;
        		
        		updtStmnt= con.prepareStatement("update item_cat_tree t SET t.rgt = (t.rgt+2) WHERE t.rgt > '"+leftOfLastChildL2+"'");
        		updtStmnt.executeUpdate();
        		updtStmnt.close();
    			
        		updtStmnt= con.prepareStatement("update item_cat_tree t SET t.lft = (t.lft+2) WHERE t.lft > '"+leftOfLastChildL2+"'");
        		updtStmnt.executeUpdate();
        		updtStmnt.close();
            	
            	InsertStmnt= con.prepareStatement("insert into item_cat_tree (ID,PARENT,TITLE,LFT,RGT,CODE,PARENTCODE,CREATION_DATE,LAST_MODIFIED_DATE,LVL,PARENTID,CAT1,CAT2,CAT3)"
            			+ " values ('"+CatIDL3+"', 'Network', 'Hardware','"+lftNodeL3+"','"+rgtNodeL3+"','HW','GN-NW-HW', sysdate,sysdate,'3','"+CatIDL2+"','GN','NW','HW' ) ");
                System.out.println("Insert C2 : insert into item_cat_tree (ID,PARENT,TITLE,LFT,RGT,CODE,PARENTCODE,CREATION_DATE,LAST_MODIFIED_DATE,LVL,PARENTID,CAT1,CAT2) "
                		+ "values ('"+CatIDL3+"', 'Network', 'Hardware','"+lftNodeL3+"','"+rgtNodeL3+"','HW','GN-NW-HW', sysdate,sysdate,'3','"+CatIDL2+"','GN','NW','HW' ) ");
                InsertStmnt.executeUpdate();
                InsertStmnt.close();
            }
            result.close();
            stmnt.close();
            
            String CatIDL4 = null;
            for(int i=0; i<3; i++)
            {
            	selectQuery = "select TREE_SEQ.nextval as nbr from dual";  
            	stmnt = con.createStatement();
            	getseqRes = stmnt.executeQuery(selectQuery);
                
                while (getseqRes.next()) {
                	CatIDL4= getseqRes.getString("nbr"); 
    			}
                getseqRes.close();
    			stmnt.close();
    			
            	String catName="";
            	String catCode="";
            	String parentCode = "";
            	
            	switch(i)
            	{
            	case 0:
            		catName = "IT Asset";
            		catCode = "IT";
            		parentCode = "GN-NW-HW-IT";
            		break;
            	case 1:
            		catName = "Passive Asset";
            		catCode = "PS";
            		parentCode = "GN-NW-HW-PS";
            		break;
            	case 2:
            		catName = "Inventory Asset";
            		parentCode = "GN-NW-HW-INV";
            		catCode = "INV";
            		break;
            	}
            	
            	selectQuery= "SELECT t.title, t.lft, t.rgt FROM item_cat_tree t where t.id ='"+CatIDL3+"' ";  
            	stmnt = con.createStatement();
            	result= stmnt.executeQuery(selectQuery);
                
                while(result.next()){
                	
                	int rgtL3 = Integer.parseInt(result.getNString("rgt"));
            		int leftOfLastChildL3 = rgtL3 - 1;
            		int lftNodeL4 = leftOfLastChildL3 + 1;  // lft of new cat
            		int rgtNodeL4 = leftOfLastChildL3 + 2;
            		
            		updtStmnt= con.prepareStatement("update item_cat_tree t SET t.rgt = (t.rgt+2) WHERE t.rgt > '"+leftOfLastChildL3+"'");
            		updtStmnt.executeUpdate();
            		updtStmnt.close();
        			
            		updtStmnt= con.prepareStatement("update item_cat_tree t SET t.lft = (t.lft+2) WHERE t.lft > '"+leftOfLastChildL3+"'");
            		updtStmnt.executeUpdate();
            		updtStmnt.close();
                	
                	InsertStmnt= con.prepareStatement("insert into item_cat_tree (ID,PARENT,TITLE,LFT,RGT,CODE,PARENTCODE,CREATION_DATE,LAST_MODIFIED_DATE,LVL,PARENTID,CAT1,CAT2,CAT3,CAT4) "
                			+ "values ('"+CatIDL4+"', 'Hardware', '"+catName+"','"+lftNodeL4+"','"+rgtNodeL4+"','"+catCode+"','"+parentCode+"', sysdate,sysdate,'4','"+CatIDL3+"','GN','NW','HW','"+catCode+"' ) ");
                    System.out.println("Insert C2 : insert into item_cat_tree (ID,PARENT,TITLE,LFT,RGT,CODE,PARENTCODE,CREATION_DATE,LAST_MODIFIED_DATE,LVL,PARENTID,CAT1,CAT2,CAT3,CAT4)"
                    		+ " values ('"+CatIDL4+"', 'Hardware', '"+catName+"','"+lftNodeL4+"','"+rgtNodeL4+"','"+catCode+"','"+parentCode+"', sysdate,sysdate,'4','"+CatIDL3+"','GN','NW','HW','"+catCode+"' ) ");
                    InsertStmnt.executeUpdate();
                    InsertStmnt.close();
                }
                result.close();
                stmnt.close();
            }
            
            
        	
            
            selectQuery = "select distinct FA_CATEGORY, L1, L2, L3, L4 from DM_FINANCIAL";  
            stmnt = con.createStatement();
            ResultSet rs1 = stmnt.executeQuery(selectQuery);
 
		while (rs1.next()) {
			
			
			
			String[] CategoryCode = null;
			String code0 = null;
			String code1 = null;
			String code2 = null;
			String code3 = null;
			if(rs1.getString("fa_category") != null)
			{
				CategoryCode = rs1.getString("fa_category").split("-");
				code0 = CategoryCode[0];
				code1 = CategoryCode[1];
				code2 = CategoryCode[2];
				code3 = CategoryCode[3];
			
			
                
                String ItemCatID = null ;
                String ItemCatID1 = null ;
                String ItemCatID2 = null ;

            String CatLevel1 = rs1.getString("L1");
            String CatLevel2 = rs1.getString("L2");
            String CatLevel3 = rs1.getString("L3");
            String CatLevel4 = rs1.getString("L4");
            if(StringUtils.equalsIgnoreCase(rs1.getString("L1"),"Active Hardware"))
            {
            	CatLevel1 = "Network";
            	CatLevel2 = "Radio Access network";
            	CatLevel3 = "Antenna";
            	CatLevel4 = "Active Hardware";
            	
            	code0 = "NW";
				code1 = "RAN";
				code2 = "ANT";
				code3 = "AH";
            }
            selectQuery = "select count(*) as category from item_cat_tree where LOWER(title)=LOWER('" + CatLevel1 +"') and parent='All Categories' and lvl ='1'";  
			stmnt = con.createStatement();
            ResultSet rs3 = stmnt.executeQuery(selectQuery);
            
            int countL1 = 0;
            while(rs3.next()){
        		
            	countL1 = rs3.getInt("category");
             
            }
            rs3.close();
            stmnt.close();
    		
             if(countL1 == 0)
             {
            	selectQuery = "select TREE_SEQ.nextval as nbr from dual";  
         		stmnt = con.createStatement();
         		getseqRes = stmnt.executeQuery(selectQuery);
                
                while (getseqRes.next()) {
                	ItemCatID= getseqRes.getString("nbr"); 
    			}
                getseqRes.close();
    			stmnt.close();
         		
	          
         		updtStmnt= con.prepareStatement("update item_cat_tree t SET t.rgt = (t.rgt+2) WHERE t.rgt > '"+leftOfLastChild+"'");
         		updtStmnt.executeUpdate();
         		updtStmnt.close();
    			
         		updtStmnt= con.prepareStatement("update item_cat_tree t SET t.lft = (t.lft+2) WHERE t.lft > '"+leftOfLastChild+"'");
         		updtStmnt.executeUpdate();
         		updtStmnt.close();
      
         		
    			InsertStmnt = con.prepareStatement("insert into item_cat_tree (ID,PARENT,TITLE,LFT,RGT,CODE,PARENTCODE,CREATION_DATE,LAST_MODIFIED_DATE,LVL,PARENTID,CAT1) "
    					+ "values ('"+ItemCatID+"', 'All Categories', '"+rs1.getNString("L1")+"','"+lftNodeRoot+"','"+rgtNodeRoot+"','"+code0+"','"+code0+"', sysdate,sysdate,'1','0','"+code0+"' ) ");
            	System.out.println("Insert C1 : insert into item_cat_tree (ID,PARENT,TITLE,LFT,RGT,CODE,PARENTCODE,CREATION_DATE,LAST_MODIFIED_DATE,LVL,PARENTID,CAT1,CAT2,CAT3,CAT4)"
            			+ " values ('"+ItemCatID+"', 'All Categories', '"+rs1.getNString("L1")+"','"+lftNodeRoot+"','"+rgtNodeRoot+"','"+code0+"','"+code0+"', sysdate,sysdate,'1','0','"+code0+"' ) ");
            	InsertStmnt.executeUpdate();
            	InsertStmnt.close();
	            
             }
             else
             {
            	selectQuery = "SELECT id from item_cat_tree where LOWER(title)=LOWER('" +CatLevel1 +"') and parent='All Categories' and lvl ='1'";  
            	stmnt = con.createStatement();
 	            getResult = stmnt.executeQuery(selectQuery);
 	           while (getResult.next()) {
 	        	   
 	        	  ItemCatID = getResult.getString("id");
 	           }
 	          getResult.close();
 	          stmnt.close();
             }
			  
            
            selectQuery = "select count(*) as category from item_cat_tree where LOWER(title)=LOWER('" + CatLevel2 +"') and LOWER(parent) = LOWER('"+CatLevel1+"') and lvl ='2'";  
            stmnt = con.createStatement();
            ResultSet rs9 = stmnt.executeQuery(selectQuery);
            
            while(rs9.next()){
            	
            	 int countL2 = rs9.getInt("category");
	                if(countL2 == 0)
	                {
	                	selectQuery = "select TREE_SEQ.nextval as nbr from dual";  
	                	stmnt = con.createStatement();
	                	getseqRes = stmnt.executeQuery(selectQuery);
	                    
	                    while (getseqRes.next()) {
	                    	ItemCatID1= getseqRes.getString("nbr"); 
	        			}
	                    getseqRes.close();
	        			stmnt.close();
	        			
	        			selectQuery = "SELECT t.title, t.lft, t.rgt FROM item_cat_tree t where t.id ='"+ItemCatID+"' ";  
	                	stmnt = con.createStatement();
	    	            ResultSet rs6 = stmnt.executeQuery(selectQuery);
	    	            
	    	            while(rs6.next()){
	    	            	
	    	            	int rgt1 = Integer.parseInt(rs6.getNString("rgt"));
	    	        		int leftOfLastChild1 = rgt1 - 1;
	    	        		int lftNode1 = leftOfLastChild1 + 1;  // lft of new cat
	    	        		int rgtNode1 = leftOfLastChild1 + 2;
	    	        		
	    	        		PreparedStatement updtmt5 = con.prepareStatement("update item_cat_tree t SET t.rgt = (t.rgt+2) WHERE t.rgt > '"+leftOfLastChild1+"'");
	    	    			updtmt5.executeUpdate();
	    	    			updtmt5.close();
	    	    			
	    	    			PreparedStatement updtmt6 = con.prepareStatement("update item_cat_tree t SET t.lft = (t.lft+2) WHERE t.lft > '"+leftOfLastChild1+"'");
	    	    			updtmt6.executeUpdate();
	    	    			updtmt6.close();
	    	      
	    	         		
	    	            	PreparedStatement stmt1 = con.prepareStatement("insert into item_cat_tree (ID,PARENT,TITLE,LFT,RGT,CODE,PARENTCODE,CREATION_DATE,LAST_MODIFIED_DATE,LVL,PARENTID,CAT1,CAT2) "
	    	            			+ "values ('"+ItemCatID1+"', '"+rs1.getNString("L1")+"', '"+rs1.getNString("L2")+"','"+lftNode1+"','"+rgtNode1+"','"+code1+"','"+code0+"-"+code1+"', sysdate,sysdate,'2','"+ItemCatID+"','"+code0+"','"+code1+"' ) ");
	    		            System.out.println("Insert C2 : insert into item_cat_tree (ID,PARENT,TITLE,LFT,RGT,CODE,PARENTCODE,CREATION_DATE,LAST_MODIFIED_DATE,LVL,PARENTID,CAT1,CAT2) "
	    		            		+ "values ('"+ItemCatID1+"', '"+rs1.getNString("L1")+"', '"+rs1.getNString("L2")+"','"+lftNode1+"','"+rgtNode1+"','"+code1+"','"+code0+"-"+code1+"', sysdate,sysdate,'2','"+ItemCatID+"','"+code0+"','"+code1+"' ) ");
	    	            	stmt1.executeUpdate();
	    			        stmt1.close();
	    	            }
	    	            rs6.close();
	    	            stmnt.close();
	                }
	                else
	                {
	                	selectQuery = "SELECT id from item_cat_tree where LOWER(title)=LOWER('" + CatLevel2 +"') and LOWER(parent) = LOWER('"+CatLevel1+"') and lvl ='2'";  
	                	stmnt = con.createStatement();
	                	getResult = stmnt.executeQuery(selectQuery);
	     	           while (getResult.next()) {
	     	        	   
	     	        	  ItemCatID1 = getResult.getString("id");
	     	           }
	     	          getResult.close();
	     	          stmnt.close();
	                }
	                
	                
	                selectQuery = "select count(*) as category from item_cat_tree where LOWER(title)=LOWER('" + CatLevel3 +"') and LOWER(parent) = LOWER('"+CatLevel2+"') and lvl ='3'";  
	                stmnt = con.createStatement();
		            ResultSet rs7 = stmnt.executeQuery(selectQuery);
		            
		            while(rs7.next()){
		                int countL3 = rs7.getInt("category");
		                if(countL3 == 0)
		                {
		                	selectQuery = "select TREE_SEQ.nextval as nbr from dual";  
		                	stmnt = con.createStatement();
		                	getseqRes = stmnt.executeQuery(selectQuery);
		                    
		                    while (getseqRes.next()) {
		                    	ItemCatID2= getseqRes.getString("nbr"); 
		        			}
		                    getseqRes.close();
		        			stmnt.close();
		        			
		                	selectQuery = "SELECT t.title, t.lft, t.rgt FROM item_cat_tree t where t.id ='"+ItemCatID1+"' ";  
		    	            stmnt = con.createStatement();
		    	            result = stmnt.executeQuery(selectQuery);
		    	            
		    	            while(result.next()){
		    	            	
		    	            	int rgt2 = Integer.parseInt(result.getNString("rgt"));
		    	        		int leftOfLastChild2 = rgt2 - 1;
		    	        		int lftNode2 = leftOfLastChild2 + 1;  // lft of new cat
		    	        		int rgtNode2 = leftOfLastChild2 + 2;
		    	        		
		    	        		PreparedStatement updtmt7 = con.prepareStatement("update item_cat_tree t SET t.rgt = (t.rgt+2) WHERE t.rgt > '"+leftOfLastChild2+"'");
		    	    			updtmt7.executeUpdate();
		    	    			updtmt7.close();
		    	    			
		    	    			PreparedStatement updtmt8 = con.prepareStatement("update item_cat_tree t SET t.lft = (t.lft+2) WHERE t.lft > '"+leftOfLastChild2+"'");
		    	    			updtmt8.executeUpdate();
		    	    			updtmt8.close();
		    	      
		    	         		
		    	            	PreparedStatement stmt10 = con.prepareStatement("insert into item_cat_tree (ID,PARENT,TITLE,LFT,RGT,CODE,PARENTCODE,CREATION_DATE,LAST_MODIFIED_DATE,LVL,PARENTID,CAT1,CAT2,CAT3) "
		    	            			+ "values ('"+ItemCatID2+"', '"+rs1.getNString("L2")+"', '"+rs1.getNString("L3")+"','"+lftNode2+"','"+rgtNode2+"','"+code2+"','"+code0+"-"+code1+"-"+code2+"', sysdate,sysdate,'3','"+ItemCatID1+"','"+code0+"','"+code1+"','"+code2+"' ) ");
		    		            System.out.println("Insert C3 : insert into item_cat_tree (ID,PARENT,TITLE,LFT,RGT,CODE,PARENTCODE,CREATION_DATE,LAST_MODIFIED_DATE,LVL,PARENTID,CAT1,CAT2,CAT3) "
		    		            		+ "values ('"+ItemCatID2+"', '"+rs1.getNString("L2")+"', '"+rs1.getNString("L3")+"','"+lftNode2+"','"+rgtNode2+"','"+code2+"','"+code0+"-"+code1+"-"+code2+"', sysdate,sysdate,'3','"+ItemCatID1+"','"+code0+"','"+code1+"','"+code2+"' ) ");
		    	            	stmt10.executeUpdate();
		    			        stmt10.close();
		    	            }
		    	            result.close();
		    	            stmnt.close();
		    	            
		                }
		                else
		                {
		                	selectQuery = "SELECT id from item_cat_tree where LOWER(title)=LOWER('" + CatLevel3 +"') and LOWER(parent) = LOWER('"+CatLevel2+"') and lvl ='3'";  
		                	stmnt = con.createStatement();
		                	getResult = stmnt.executeQuery(selectQuery);
		     	           while (getResult.next()) {
		     	        	   
		     	        	  ItemCatID2 = getResult.getString("id");
		     	           }
		     	          getResult.close();
		     	          stmnt.close();
		                }
		                
		                selectQuery = "select count(*) as category from item_cat_tree where LOWER(title)=LOWER('" + CatLevel4 +"') and LOWER(parent) = LOWER('"+CatLevel3+"') and lvl ='4'";  
		                stmnt = con.createStatement();
		                getResult = stmnt.executeQuery(selectQuery);
    		            
    		            while(getResult.next()){
    		                int countL4 = getResult.getInt("category");
    		                String ItemCatID3 = null;
    		                if(countL4 == 0)
    		                {
    		                	selectQuery = "select TREE_SEQ.nextval as nbr from dual";  
    		                	stmnt = con.createStatement();
    		                	getseqRes = stmnt.executeQuery(selectQuery);
    		                    
    		                    while (getseqRes.next()) {
    		                    	ItemCatID3= getseqRes.getString("nbr"); 
    		        			}
    		                    getseqRes.close();
    		        			stmnt.close();
    		        			
    		                	selectQuery = "SELECT t.title, t.lft, t.rgt FROM item_cat_tree t where t.id ='"+ItemCatID2+"' ";  
    		                	stmnt = con.createStatement();
    		    	            ResultSet rs12 = stmnt.executeQuery(selectQuery);
    		    	            
    		    	            while(rs12.next()){
    		    	            	
    		    	            	int rgt3 = Integer.parseInt(rs12.getNString("rgt"));
    		    	        		int leftOfLastChild3 = rgt3 - 1;
    		    	        		int lftNode3 = leftOfLastChild3 + 1;  // lft of new cat
    		    	        		int rgtNode3 = leftOfLastChild3 + 2;
    		    	        		
    		    	        		PreparedStatement updtmt9 = con.prepareStatement("update item_cat_tree t SET t.rgt = (t.rgt+2) WHERE t.rgt > '"+leftOfLastChild3+"'");
    		    	    			updtmt9.executeUpdate();
    		    	    			updtmt9.close();
    		    	    			
    		    	    			PreparedStatement updtmt10 = con.prepareStatement("update item_cat_tree t SET t.lft = (t.lft+2) WHERE t.lft > '"+leftOfLastChild3+"'");
    		    	    			updtmt10.executeUpdate();
    		    	    			updtmt10.close();
    		    	      
    		    	         		
    		    	            	PreparedStatement stmt14 = con.prepareStatement("insert into item_cat_tree (ID,PARENT,TITLE,LFT,RGT,CODE,PARENTCODE,CREATION_DATE,LAST_MODIFIED_DATE,LVL,PARENTID,CAT1,CAT2,CAT3,CAT4) "
    		    	            			+ "values ('"+ItemCatID3+"', '"+rs1.getNString("L3")+"', '"+rs1.getNString("L4")+"','"+lftNode3+"','"+rgtNode3+"','"+code3+"','"+code0+"-"+code1+"-"+code2+"-"+code3+"', sysdate,sysdate,'4','"+ItemCatID2+"','"+code0+"','"+code1+"','"+code2+"','"+code3+"' ) ");
    		    		            System.out.println("Insert C4 : insert into item_cat_tree (ID,PARENT,TITLE,LFT,RGT,CODE,PARENTCODE,CREATION_DATE,LAST_MODIFIED_DATE,LVL,PARENTID,CAT1,CAT2,CAT3,CAT4) "
    		    		            		+ "values ('"+ItemCatID3+"', '"+rs1.getNString("L3")+"', '"+rs1.getNString("L4")+"','"+lftNode3+"','"+rgtNode3+"','"+code3+"','"+code0+"-"+code1+"-"+code2+"-"+code3+"', sysdate,sysdate,'4','"+ItemCatID2+"','"+code0+"','"+code1+"','"+code2+"','"+code3+"' ) ");
    		    	            	stmt14.executeUpdate();
    		    			        stmt14.close();
    		    	            }
    		                }
    		            }
    		            getResult.close();
    		            stmnt.close();
		            }
		            rs7.close();
		            stmnt.close();  	
            }
            rs9.close();
            stmnt.close();
       
			         ///////////////////////////////////
			        
			        

		}
			
		}
		rs1.close();

		stmnt.close();
			
			con.close();
			System.out.println("Get Categories From FinancialDM  COMPLETED");
	        }   
        		
      
	        catch(Exception e){
	  	      System.err.println(e);
	  	      e.printStackTrace();
	  	   }
          
		}
}
