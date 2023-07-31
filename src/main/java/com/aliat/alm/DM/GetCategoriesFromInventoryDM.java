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



public class GetCategoriesFromInventoryDM {

	
	public static void main(String[] args) {

		
        String str = null;
	     String pass=null;
	     String strpwd = null;
	     String struser = null;
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
		//System.out.println(dis.readLine());
		String data = dis.readLine();

              if (data.contains(sVerifyText)) {	
                  int index = data.indexOf('>');
                  str=data.substring(index+1, data.length());
                  index = str.indexOf('<');
                  str=str.substring(0, index);
                  // System.out.println(str);  
              }
              if (data.contains(spasswrd)) {	
                  int index = data.indexOf('>');
                  strpwd=data.substring(index+1, data.length());
                  index = strpwd.indexOf('<');
                  strpwd=strpwd.substring(0, index);
                  //System.out.println(strpwd);  
              }
              if (data.contains(susename)) {	
                  int index = data.indexOf('>');
                  struser=data.substring(index+1, data.length());
                  index = struser.indexOf('<');
                  struser=struser.substring(0, index);
                  //System.out.println(struser);  
              }          

		}
        	
		    	DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
		         //Connection con = DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:alm","alm","alm");
				Connection con = DriverManager.getConnection (str,struser,strpwd);
	         
	         Statement stmt2 = null;
	         Statement stmt3 = null;
	         Statement stmttype = null;
	         Statement stmt5 = null;
	         Statement stmt6 = null;
	         Statement stmt7 = null;
	         Statement stmt8 = null;
	         Statement stmt9 = null;
	         Statement stmt11 = null;
	         Statement stmt12 = null;
	         Statement stmt16 = null;
	         Statement stmt15 = null;
	         
	        
			
            String query1 = "select distinct FA_CATEGORY, L1, L2, L3, L4 from DM_INVENTORY where FA_CATEGORY NOT IN (SELECT FA_CATEGORY FROM DM_FINANCIAL)";  
            stmttype = con.createStatement();
            ResultSet rs1 = stmttype.executeQuery(query1);
 
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

			String query3 = "select count(*) as category from item_cat_tree where LOWER(title)=LOWER('" + rs1.getString("L1") +"') and parent='All Categories' and lvl ='1'";  
            stmt3 = con.createStatement();
            ResultSet rs3 = stmt3.executeQuery(query3);
            
            while(rs3.next()){
        		
             int countL1 = rs3.getInt("category");
             if(countL1 == 0)
             {
         		 
         		String query2 = "select TREE_SEQ.nextval as nbr from dual";  
                stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery(query2);
                
                while (rs2.next()) {
                	ItemCatID= rs2.getString("nbr"); 
    			}
    			rs2.close();
    			stmt2.close();

        		String query5 = "SELECT t.title, t.lft, t.rgt FROM item_cat_tree t where t.id ='0' ";  
	            stmt5 = con.createStatement();
	            ResultSet rs5 = stmt5.executeQuery(query5);
	            while(rs5.next()){
	            	
	            	int rgt = Integer.parseInt(rs5.getNString("rgt"));
	        		int leftOfLastChild = rgt - 1;
	        		int lftNode = leftOfLastChild + 1;  // lft of new cat
	        		int rgtNode = leftOfLastChild + 2;
	        		
	        		
	        		PreparedStatement updtmt3 = con.prepareStatement("update item_cat_tree t SET t.rgt = (t.rgt+2) WHERE t.rgt > '"+leftOfLastChild+"'");
	    			updtmt3.executeUpdate();
	    			updtmt3.close();
	    			
	    			PreparedStatement updtmt4 = con.prepareStatement("update item_cat_tree t SET t.lft = (t.lft+2) WHERE t.lft > '"+leftOfLastChild+"'");
	    			updtmt4.executeUpdate();
	    			updtmt4.close();
	      
	         		
	            	PreparedStatement stmt = con.prepareStatement("insert into item_cat_tree (ID,PARENT,TITLE,LFT,RGT,CODE,PARENTCODE,CREATION_DATE,LAST_MODIFIED_DATE,LVL,PARENTID,CAT1)  "
	            			+ "values ('"+ItemCatID+"', 'All Categories', '"+rs1.getNString("L1")+"','"+lftNode+"','"+rgtNode+"','"+code0+"','"+code0+"', sysdate,sysdate,'1','0','"+code0+"' ) ");
		            System.out.println("Insert C1 : insert into item_cat_tree (ID,PARENT,TITLE,LFT,RGT,CODE,PARENTCODE,CREATION_DATE,LAST_MODIFIED_DATE,LVL,PARENTID,CAT1) "
		            		+ "values ('"+ItemCatID+"', 'All Categories', '"+rs1.getNString("L1")+"','"+lftNode+"','"+rgtNode+"','"+code0+"','"+code0+"', sysdate,sysdate,'1','0','"+code0+"' ) ");
	            	stmt.executeUpdate();
			        stmt.close();
	            }
             }
             else
             {
            	String query16 = "SELECT id from item_cat_tree where LOWER(title)=LOWER('" + rs1.getString("L1") +"') and parent='All Categories' and lvl ='1'";  
 	            stmt16 = con.createStatement();
 	            ResultSet rs16 = stmt16.executeQuery(query16);
 	           while (rs16.next()) {
 	        	   
 	        	  ItemCatID = rs16.getString("id");
 	           }
 	           rs16.close();
			   stmt16.close();
             }
			  
            
            String query9 = "select count(*) as category from item_cat_tree where LOWER(title)=LOWER('" + rs1.getString("L2") +"') and LOWER(parent) = LOWER('"+rs1.getString("L1")+"') and lvl ='2'";  
            stmt9 = con.createStatement();
            ResultSet rs9 = stmt9.executeQuery(query9);
            
            while(rs9.next()){
            	
            	 int countL2 = rs9.getInt("category");
	                if(countL2 == 0)
	                {
	                
	                	String query2 = "select TREE_SEQ.nextval as nbr from dual";  
	                    stmt2 = con.createStatement();
	                    ResultSet rs2 = stmt2.executeQuery(query2);
	                    
	                    while (rs2.next()) {
	                    	ItemCatID1= rs2.getString("nbr"); 
	        			}
	        			rs2.close();
	        			stmt2.close();
	        			
	                	String query6 = "SELECT t.title, t.lft, t.rgt FROM item_cat_tree t where t.id ='"+ItemCatID+"' ";  
	    	            stmt6 = con.createStatement();
	    	            ResultSet rs6 = stmt6.executeQuery(query6);
	    	            
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
	    		            System.out.println("Insert C2 : insert into item_cat_tree (ID,PARENT,TITLE,LFT,RGT,CODE,PARENTCODE,CREATION_DATE,LAST_MODIFIED_DATE,LVL,PARENTID,CAT1,CAT2)"
	    		            		+ " values ('"+ItemCatID1+"', '"+rs1.getNString("L1")+"', '"+rs1.getNString("L2")+"','"+lftNode1+"','"+rgtNode1+"','"+code1+"','"+code0+"-"+code1+"', sysdate,sysdate,'2','"+ItemCatID+"','"+code0+"','"+code1+"' ) ");
	    	            	stmt1.executeUpdate();
	    			        stmt1.close();
	    	            }
	    	            rs6.close();
						stmt6.close();
	                }
	                else
	                {
	                	String query15 = "SELECT id from item_cat_tree where LOWER(title)=LOWER('" + rs1.getString("L2") +"') and LOWER(parent) = LOWER('"+rs1.getString("L1")+"') and lvl ='2'";  
	     	            stmt15 = con.createStatement();
	     	            ResultSet rs15 = stmt15.executeQuery(query15);
	     	           while (rs15.next()) {
	     	        	   
	     	        	  ItemCatID1 = rs15.getString("id");
	     	           }
	     	           rs15.close();
	    			   stmt15.close();
	                }
	                
	                
	                String query7 = "select count(*) as category from item_cat_tree where LOWER(title)=LOWER('" + rs1.getString("L3") +"') and LOWER(parent) = LOWER('"+rs1.getString("L2")+"') and lvl ='3'";  
		            stmt7 = con.createStatement();
		            ResultSet rs7 = stmt7.executeQuery(query7);
		            
		            while(rs7.next()){
		                int countL3 = rs7.getInt("category");
		                if(countL3 == 0)
		                {
		                    String query2 = "select TREE_SEQ.nextval as nbr from dual";  
		                    stmt2 = con.createStatement();
		                    ResultSet rs2 = stmt2.executeQuery(query2);
		                    
		                    while (rs2.next()) {
		                    	ItemCatID2= rs2.getString("nbr"); 
		        			}
		        			rs2.close();
		        			stmt2.close();
		        			
		                	String query8 = "SELECT t.title, t.lft, t.rgt FROM item_cat_tree t where t.id ='"+ItemCatID1+"' ";  
		    	            stmt8 = con.createStatement();
		    	            ResultSet rs8 = stmt8.executeQuery(query8);
		    	            
		    	            while(rs8.next()){
		    	            	
		    	            	int rgt2 = Integer.parseInt(rs8.getNString("rgt"));
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
		    	            
		                }
		                else
		                {
		                	String query15 = "SELECT id from item_cat_tree where LOWER(title)=LOWER('" + rs1.getString("L3") +"') and LOWER(parent) = LOWER('"+rs1.getString("L2")+"') and lvl ='3'";  
		     	            stmt15 = con.createStatement();
		     	            ResultSet rs15 = stmt15.executeQuery(query15);
		     	           while (rs15.next()) {
		     	        	   
		     	        	  ItemCatID2 = rs15.getString("id");
		     	           }
		     	           rs15.close();
		    			   stmt15.close();
		                }
		                
		                String query11 = "select count(*) as category from item_cat_tree where LOWER(title)=LOWER('" + rs1.getString("L4") +"') and LOWER(parent) = LOWER('"+rs1.getString("L3")+"') and lvl ='4'";  
    		            stmt11 = con.createStatement();
    		            ResultSet rs11 = stmt11.executeQuery(query11);
    		            
    		            while(rs11.next()){
    		                int countL4 = rs11.getInt("category");
    		                String ItemCatID3 = null;
    		                if(countL4 == 0)
    		                {
    		                	String query2 = "select TREE_SEQ.nextval as nbr from dual";  
    		                    stmt2 = con.createStatement();
    		                    ResultSet rs2 = stmt2.executeQuery(query2);
    		                    
    		                    while (rs2.next()) {
    		                    	ItemCatID3= rs2.getString("nbr"); 
    		        			}
    		        			rs2.close();
    		        			stmt2.close();
    		        			
    		                	String query12 = "SELECT t.title, t.lft, t.rgt FROM item_cat_tree t where t.id ='"+ItemCatID2+"' ";  
    		    	            stmt12 = con.createStatement();
    		    	            ResultSet rs12 = stmt12.executeQuery(query12);
    		    	            
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
    		            rs11.close();
		    			stmt11.close();
		            }
		            rs7.close();
	    			stmt7.close();  	
            }
            rs9.close();
			stmt9.close();
        }
        rs3.close();
		stmt3.close();
			         ///////////////////////////////////
			        
			        

		}
			
		}
		rs1.close();

		stmttype.close();
			
			con.close();
			System.out.println("Get Categories From InventoryDM  COMPLETED");
	        }   
        		
      
	        catch(Exception e){
	  	      System.err.println(e);
	  	      e.printStackTrace();
	  	   }
          
		}
}
