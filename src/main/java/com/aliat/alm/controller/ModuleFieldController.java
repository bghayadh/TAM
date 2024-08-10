package com.aliat.alm.controller;

import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.FloatType;
import org.hibernate.type.StringType;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Form;
import com.aliat.alm.common.Notify;
import com.aliat.alm.models.ArNode;
import com.aliat.alm.models.ArPartNumber;
import com.aliat.alm.models.ArSerialNumber;
import com.aliat.alm.models.ArSite;
import com.aliat.alm.models.AssetRegistry;
import com.aliat.alm.models.ModuleField;
import com.aliat.alm.models.ModuleScreen;
import com.aliat.alm.models.PurchaseOrderItem;
import com.aliat.alm.models.PurchaseRequest;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class ModuleFieldController {
	@Autowired
	Form form;
	@Autowired
	ALMSessions almsessions;
	
	@Autowired
	Notify notifications;

	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private static Query query = null;
	private static String queryString = null;
	private static final Logger logger = LoggerFactory.getLogger(ModuleFieldController.class);

	@RequestMapping(value = "/ModuleFieldListView", method = RequestMethod.GET)
	public String Activity(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
		List<ModuleScreen> ModuleScreenList = new ArrayList<ModuleScreen>();
		
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				tx = session.beginTransaction();
				notifications.headerNotifications(session, model);
				try {
					
					ModuleScreenList = session.createQuery(
							"select t.id,t.screenName, t.screenTable  from ModuleScreen t order by t.screenName ")
							.list();
					model.addAttribute("ListGridTable", mapper.writeValueAsString(ModuleScreenList));
			
				
				} catch (Exception e) {
					logger.info(
							"Error on the level of purchase order list view with a message : " + e + "\n" + e.getMessage());
					model.addAttribute("ListGridTable", "");
				} finally {
					if (session != null && session.isOpen()) {
						tx.commit();
						session.close();
						
					}
				}
			}
			return "ModuleFieldListView";
		}
	
	
	@RequestMapping(value = "/getTableFields", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getTableFields(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	    Map<String, Object> rtn = new LinkedHashMap<>();
	    String loginStatus = LoginServices.checkSession(request, response);
	    
	    if (loginStatus.equals("redirect:/")) {
	        rtn.put("Login", loginStatus);
	        return rtn;
	    }
	    
	    String TableName;
	    String ScreenId = request.getParameter("id");
	    Session session = AlmDbSession.getInstance().getSession();
	    
	    if (session != null && session.isOpen()) {
	        Transaction tx = null;
	        try {
	            tx = session.beginTransaction();
	            notifications.headerNotifications(session, model);
	            
	            TableName = (String) session.createSQLQuery(
	                    "SELECT SCREEN_TABLE FROM MODULE_SCREEN WHERE ID = :param1")
	                    .setParameter("param1", ScreenId)
	                    .uniqueResult();
	            
	            if (TableName != "" || TableName != null ) {
	                // When TableName is found
	                List<ModuleField> moduleFieldList = session.createQuery(
	                        "SELECT t.id, t.id, t.screenName, t.screenTable, t.fieldName, " +
	                        "TO_CHAR(t.creationDate, 'YYYY-MM-DD HH24:MI:SS'), " +
	                        "TO_CHAR(t.lastModificationDate, 'YYYY-MM-DD HH24:MI:SS') " +
	                        "FROM ModuleField t WHERE t.screenTable = :param1 ORDER BY t.lastModificationDate DESC")
	                        .setParameter("param1", TableName)
	                        .list();
	                if (moduleFieldList != null && !moduleFieldList.isEmpty()) {
	                rtn.put("ListGridTable", moduleFieldList);}
	            } else {
	               
	                TableName = TableName.toUpperCase();
	                List<String> fieldNames = session.createSQLQuery(
	                        "SELECT COLUMN_NAME FROM ALL_TAB_COLUMNS WHERE TABLE_NAME = :param1 ORDER BY COLUMN_NAME")
	                        .setParameter("param1", TableName)
	                        .list();
	                
	                rtn.put("fieldNames", fieldNames);
	            }
	        } catch (Exception e) {
	            logger.error("Error retrieving table fields with a message: " + e.getMessage(), e);
	        } finally {
	            if (tx != null) {
	                tx.commit();
	            }
	            if (session != null && session.isOpen()) {
	                session.close();
	            }
	        }
	    }
	    return rtn;
	}
	
	
	
	
	
	@RequestMapping(value = "/initializeFields", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> initializeFields(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
	    Map<String, Object> rtn = new LinkedHashMap<>();
	    String loginStatus = LoginServices.checkSession(request, response);
	    
	    if ("redirect:/".equals(loginStatus)) {
	        rtn.put("Login", loginStatus);
	        return rtn;
	    }

	    String TableName;
	    String ScreenId = request.getParameter("Screenid");
	    String ScreenName = request.getParameter("screenName");
	    String initializeWithIndex = request.getParameter("initializeWithIndex");
	    Session session = AlmDbSession.getInstance().getSession();
	    System.out.println("Initialize with Index: " + initializeWithIndex);
	    Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);

	    if (session != null && session.isOpen()) {
	        Transaction tx = null;
	        try {
	            tx = session.beginTransaction();
	            notifications.headerNotifications(session, model);
	            
	            TableName = (String) session.createSQLQuery(
	                    "SELECT SCREEN_TABLE FROM MODULE_SCREEN WHERE ID = :param1")
	                    .setParameter("param1", ScreenId)
	                    .uniqueResult();

	            if (TableName != null && !TableName.isEmpty()) {
	                TableName = TableName.toUpperCase();
	                if ("false".equals(initializeWithIndex)) {
	                    // Delete existing fields
	                    Query deleteQuery = session.createNativeQuery("DELETE FROM MODULE_FIELDS WHERE SCREEN_TABLE = :param1")
	                            .setParameter("param1", TableName);
	                    deleteQuery.executeUpdate();

	                    // Get database columns
	                    List<String> fieldNames = session.createSQLQuery(
	                            "SELECT COLUMN_NAME FROM ALL_TAB_COLUMNS WHERE TABLE_NAME = :param1 ORDER BY COLUMN_NAME")
	                            .setParameter("param1", TableName)
	                            .list();

	                    // Generate IDs for new fields
	                    String[] ids = new String[fieldNames.size()];
	                  
	                    for (int i = 0; i < fieldNames.size(); i++) {
	                        String moduleFieldId = "Module_Field_" + year + "_" +
	                                Integer.parseInt(session.createNativeQuery("SELECT MODULE_FIELDS FROM SEQ_TABLE").uniqueResult().toString());
	                        ids[i] = moduleFieldId;
	                        session.createNativeQuery("UPDATE SEQ_TABLE SET MODULE_FIELDS = MODULE_FIELDS + 1").executeUpdate();
	                        session.createNativeQuery("COMMIT").executeUpdate();
	                    }

	                    // Create and save ModuleField objects
	                    for (int i = 0; i < fieldNames.size(); i++) {
	                        ModuleField moduleField = new ModuleField();
	                        moduleField.setId(ids[i]);
	                        moduleField.setScreenName(ScreenName);
	                        moduleField.setScreenTable(TableName);
	                        moduleField.setFieldName(fieldNames.get(i));
	                      
	                        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
	                        moduleField.setCreationDate(currentTimestamp);
	                        moduleField.setLastModificationDate(currentTimestamp);
	                        session.saveOrUpdate(moduleField);
	                    }
	                }
	                else {
	                    // 1. Retrieve all ModuleField records for the given TableName
	                    List<ModuleField> oldData = session.createQuery(
	                            "FROM ModuleField t WHERE t.screenTable = :param1")
	                            .setParameter("param1", TableName)
	                            .list();

	                

	                    // 2. Retrieve all column names for TableName
	                    List<String> columnNamesList = session.createSQLQuery(
	                            "SELECT COLUMN_NAME FROM ALL_TAB_COLUMNS WHERE TABLE_NAME = :param1 ORDER BY COLUMN_NAME")
	                            .setParameter("param1", TableName)
	                            .list();
	                    String[] columnNames = columnNamesList.toArray(new String[0]);

	                  
	                    // 3. Create new list 
	                    List<ModuleField> newData = new ArrayList<>();
	                    Set<String> existingFieldNames = new HashSet<>();

	                  for (ModuleField field : oldData) {
	                     String fieldName = field.getFieldName();
	                     // Add the field name to the Set
	                     existingFieldNames.add(fieldName);
	                 }
	                    // 4. Add matching old data to newData
	                    for (ModuleField field : oldData) {
	                        if (Arrays.asList(columnNames).contains(field.getFieldName())) {
	                            newData.add(field);
	                        }
	                    }

	                    // 5. Add new ModuleField objects for missing columns
	                    for (String columnName : columnNames) {
	                        if (!existingFieldNames.contains(columnName)) {
	                            ModuleField newField = new ModuleField();
	                            newField.setId("Module_Field_" + year + "_" +
	                                Integer.parseInt(session.createNativeQuery("SELECT MODULE_FIELDS FROM SEQ_TABLE").uniqueResult().toString()));
	                            session.createNativeQuery("UPDATE SEQ_TABLE SET MODULE_FIELDS = MODULE_FIELDS + 1").executeUpdate();
		                        session.createNativeQuery("COMMIT").executeUpdate();// Generate new ID
	                            newField.setScreenName(ScreenName);
	                            newField.setScreenTable(TableName);
	                            newField.setFieldName(columnName);
	                              Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
	                            newField.setCreationDate(currentTimestamp);
	                            newField.setLastModificationDate(currentTimestamp);
	                            newData.add(newField);
	                        }
	                    }

	                   

	                    // 6. Delete old ModuleField records for the given TableName
	                    Query deleteQuery = session.createNativeQuery("DELETE FROM MODULE_FIELDS WHERE SCREEN_TABLE = :param1")
	                            .setParameter("param1", TableName);
	                    deleteQuery.executeUpdate();
	                
	                    // 7. Save new ModuleField objects to the database
	                    String[] id = new String[newData.size()];
	                   for(int i=0; i<newData.size();i++) {
	                	   id[i]="Module_Field_" + year + "_" +
	                                Integer.parseInt(session.createNativeQuery("SELECT MODULE_FIELDS FROM SEQ_TABLE").uniqueResult().toString());
	                	   session.createNativeQuery("UPDATE SEQ_TABLE SET MODULE_FIELDS = MODULE_FIELDS + 1").executeUpdate();
	                        session.createNativeQuery("COMMIT").executeUpdate();
	                	  
	                   }
	                   
	                   for(int i=0; i<newData.size();i++) {
	                	   ModuleField newField = new ModuleField();
	 	                  
	                            newField.setId(id[i]);
	                            newField.setScreenName(ScreenName);
	                            newField.setScreenTable(TableName);
	                            newField.setFieldName(newData.get(i).getFieldName());
	                            newField.setFieldIndex(newData.get(i).getFieldIndex()); 
	                            newField.setFieldValues(newData.get(i).getFieldValues()); 
	                            newField.setFieldLevel(newData.get(i).getFieldLevel()); // Set default or calculated level
	                            newField.setReadOnly(newData.get(i).getReadOnly()); // Set default or calculated readonly
	                            newField.setFieldType(newData.get(i).getFieldType()); // Set default or calculated type
	                            newField.setCreationDate(newData.get(i).getCreationDate());
	                            newField.setLastModificationDate(newData.get(i).getLastModificationDate());
	                            session.saveOrUpdate(newField);
	                        
	                    }
	                   


	                    
	                }
	                List<ModuleField> moduleFieldList = session.createQuery(
	                        "SELECT t.id, t.id, t.screenName, t.screenTable, t.fieldName, " +
	                                "TO_CHAR(t.creationDate, 'YYYY-MM-DD HH24:MI:SS'), " +
	                                "TO_CHAR(t.lastModificationDate, 'YYYY-MM-DD HH24:MI:SS') " +
	                                "FROM ModuleField t WHERE t.screenTable = :param1 ORDER BY t.lastModificationDate DESC")
	                        .setParameter("param1", TableName)
	                        .list();
	                rtn.put("ListGridTable", moduleFieldList);
	            }
	            tx.commit(); // Commit transaction only if no exceptions occurred
	        } catch (Exception e) {
	            if (tx != null) {
	                tx.rollback(); // Rollback transaction in case of an exception
	            }
	            logger.error("Error retrieving table fields with a message: " + e.getMessage(), e);
	        } finally {
	            if (session != null && session.isOpen()) {
	                session.close();
	            }
	        }
	    }
	    return rtn;
	}

	
	
	@RequestMapping(value = "/ModuleFieldFormView", method = RequestMethod.GET)
	public String ModuleFieldFormView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		DateFormat formatterTime = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		
		Date date= new Date();
		String result [] =new String[4];
		
		String moduleFieldId = request.getParameter("ID"); 
		String screenId = request.getParameter("screenId");
		String navAction = request.getParameter("NavAction"); 
		String screenName = request.getParameter("screenName"); 
		String itemCategoryDetails = "";
		int SelectedIndex = 0;
		
		AssetRegistry assetreg; 
		ModuleField ModuleFieldInfo = new ModuleField();
		
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			List<ModuleField> ModuleFieldList = new ArrayList<ModuleField>();
			List<String> missingColumns =new ArrayList<String>();
			
			try {
				if(screenId != null) {
					
					String TableName = (String) session.createSQLQuery(
			                    "SELECT SCREEN_TABLE FROM MODULE_SCREEN WHERE ID = :param1")
			                    .setParameter("param1", screenId)
			                    .uniqueResult();

					String sql = "SELECT c.COLUMN_NAME " +
				             "FROM ALL_TAB_COLUMNS c " +
				             "LEFT JOIN MODULE_FIELDS m ON c.COLUMN_NAME = m.FIELD_NAME AND m.SCREEN_TABLE = :param1 " +
				             "WHERE c.TABLE_NAME = :param1 " +
				             "AND m.FIELD_NAME IS NULL " +
				             "ORDER BY c.COLUMN_NAME";

				missingColumns = session.createSQLQuery(sql)
				    .setParameter("param1", TableName)
				    .list();
 
					
						System.out.println(missingColumns);
					
					model.addAttribute("status","AddNew");
					model.addAttribute("SelectedIndex","addNew");
					model.addAttribute("moduleScreenCount","addNew");
					model.addAttribute("missingColumns",mapper.writeValueAsString(missingColumns) );
				    model.addAttribute("screenName", screenName);
		            model.addAttribute("screenTable",TableName);
		               
					return "ModuleFieldFormView"; 
				}
				
				
				
				
		       if (moduleFieldId == null) { 
					
					model.addAttribute("status","AddNew");
					model.addAttribute("SelectedIndex","addNew");
					model.addAttribute("moduleScreenCount","addNew");
					model.addAttribute("missingColumns",mapper.writeValueAsString(missingColumns) );
					return "ModuleFieldFormView"; 
					
					}
		
				else {
					result = form.NavigationNP(session,"MODULE_Fields","ID",moduleFieldId,"LAST_MODIFICATION_DATE",navAction);		
					
					SelectedIndex= Integer.parseInt(result[1]);
					System.out.println(SelectedIndex);
					moduleFieldId=result[2];

					model.addAttribute("SelectedIndex", SelectedIndex);
					model.addAttribute("moduleScreenCount", Integer.parseInt(result[0]));
				
					ModuleFieldInfo = (ModuleField) session.get(ModuleField.class, moduleFieldId);
					
					  model.addAttribute("id", ModuleFieldInfo.getId());
		                model.addAttribute("screenName", ModuleFieldInfo.getScreenName());
		                model.addAttribute("screenTable", ModuleFieldInfo.getScreenTable());
		                model.addAttribute("fieldName", ModuleFieldInfo.getFieldName());
		                model.addAttribute("fieldValues", ModuleFieldInfo.getFieldValues());
		                model.addAttribute("fieldLevel", ModuleFieldInfo.getFieldLevel());
		                model.addAttribute("fieldIndex", ModuleFieldInfo.getFieldIndex());

		                model.addAttribute("fieldType", ModuleFieldInfo.getFieldType());
		                if(ModuleFieldInfo.getCreationDate() != null) {
		                	date = ModuleFieldInfo.getCreationDate();
		                }
		                model.addAttribute("creationDate",formatterTime.format(date).toString() );
		                if(ModuleFieldInfo.getLastModificationDate() != null) {
		                	date = ModuleFieldInfo.getLastModificationDate();
		                }
		                model.addAttribute("lastModificationDate",formatterTime.format(date).toString());
		                model.addAttribute("missingColumns",mapper.writeValueAsString(missingColumns) );
				}
					
							
			} catch (Exception e) {

				logger.info("Error in Module Screen FormView with a message: "+ e);
				e.printStackTrace();
				
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
		return "ModuleFieldFormView";
	}
	@RequestMapping(value = "/DeleteModuleFieldListView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> DeleteModuleFieldListView(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		String[] idList = request.getParameterValues("ID[]");
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				query = session.createQuery("delete ModuleField where ID IN (:param1)");
				query.setParameterList("param1", idList);
				query.executeUpdate();

				
			} catch (Exception e) {
				logger.info("Error in deleting on the level of ModuleField with a message : " + e + "\n"
						+ e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
					
				}
			}
		}

		return rtn;

	}
	
	
	// getting All AR using autocomplete in navigation
	@RequestMapping(value = "/GetAllModuleField", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetAllModuleField(Locale locale, Model model, HttpServletRequest request, 
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", "redirect:/");
			return rtn;
		}
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			
		try {
				String requestValue = request.getParameter("requestValue");
				query = session.createNativeQuery("select ID, SCREEN_NAME, FIELD_NAME from MODULE_FIELDS where LOWER(ID) like LOWER(:param1) "
						+ "or LOWER(SCREEN_NAME) like LOWER(:param1) or LOWER(FIELD_NAME) like LOWER(:param1) ORDER BY LAST_MODIFICATION_DATE DESC");
				query.setString("param1", "%"+requestValue+"%");
		
				query.setFirstResult(0);
				query.setMaxResults(40);
				rtn.put("listAR", query.list());
	
			}catch(Exception e) {
				
				logger.info(
						"Error while getting Module Screen using autocomplete with error message: "+ e);
				e.printStackTrace();
				
			}
			finally {
			      if (session != null && session.isOpen()) {
			    	  tx.commit();
				      session.close();
			      }
			 }
		}
		return rtn;
	}
	@RequestMapping(value = "/ModuleFieldDelForm", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> ModuleFieldDelForm(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
				
		String idForm = request.getParameter("moduleFieldId");
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			            tx = session.beginTransaction();
			            try {
			               query = session.createNativeQuery("Delete MODULE_FIELDS where ID = '"+ idForm +"'");
			               query.executeUpdate();


			            } catch (Exception e) {
			                             logger.info("could not connect to DB in Module Field Form delete method ");
			            }
			
			            finally {
			                            if (session != null && session.isOpen()) {
			                                            tx.commit();
			                                            session.close();
			                            }
			            }
					} 

		rtn.put("Test", "DeleteDone");
		return rtn;
	}	




@RequestMapping(value = "/ModuleFieldFormSave", method = RequestMethod.GET)
@ResponseBody
public Map<String, Object> ModuleFieldFormSave(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> rtn = new LinkedHashMap<>();

    // Check session validity
    String sessionStatus = LoginServices.checkSession(request, response);
    if ("redirect:/".equals(sessionStatus)) {
        rtn.put("Login", sessionStatus);
        return rtn;
    }

    Session session = null;
    Transaction tx = null;

    try {
         Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        DateFormat formatterTime = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        ObjectMapper objectMapper = new ObjectMapper();

         String moduleFieldId = request.getParameter("moduleFieldId");
        String createdDate = request.getParameter("createdate");
        String valuesJson = request.getParameter("values");

        System.out.println("Values received: " + valuesJson);

        session = AlmDbSession.getInstance().getSession();
        if (session != null && session.isOpen()) {
            tx = session.beginTransaction();

            ModuleField moduleField = new ModuleField();

            Timestamp creationDate;
            if (createdDate == null || createdDate.trim().isEmpty()) {
                creationDate = new Timestamp(System.currentTimeMillis());
            } else {
                Date parsedDate = formatterTime.parse(createdDate);
                creationDate = new Timestamp(parsedDate.getTime());
            }
            Timestamp lastModifiedDate = new Timestamp(System.currentTimeMillis());

             if (moduleFieldId == null || moduleFieldId.trim().isEmpty()) {
                synchronized (this) {
                    moduleFieldId = "Module_Field_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT MODULE_FIELDS FROM SEQ_TABLE").uniqueResult().toString());
                    Query query = session.createNativeQuery("UPDATE SEQ_TABLE SET MODULE_FIELDS = MODULE_FIELDS + 1");
                    query.executeUpdate();
                    session.createNativeQuery("commit").executeUpdate();
                }
            }

              List<String> valuesList;
            try {
                 List<String> rawValues = objectMapper.readValue(valuesJson, new TypeReference<List<String>>() {});

                valuesList = new ArrayList<>();

                for (String item : rawValues) {
                    if (item.startsWith("[") && item.endsWith("]")) {
                        item = item.substring(1, item.length() - 1).replace("\"", "");
                        String[] nestedValues = item.split(",");
                        for (String value : nestedValues) {
                            valuesList.add(value.trim());
                        }
                    } else {
                         valuesList.add(item.trim());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                rtn.put("Error", "Error processing values.");
                return rtn;
            }

             String cleanedValuesJson = objectMapper.writeValueAsString(valuesList);

            moduleField.setId(moduleFieldId);
            moduleField.setScreenName(request.getParameter("screenName"));
            moduleField.setScreenTable(request.getParameter("screenTable"));
            moduleField.setFieldIndex(request.getParameter("fieldIndex"));
            moduleField.setFieldName(request.getParameter("fieldNameValue"));
            moduleField.setFieldLevel(request.getParameter("fieldLevel"));
            moduleField.setFieldType(request.getParameter("fieldType"));
            moduleField.setFieldValues(cleanedValuesJson); 

            moduleField.setCreationDate(creationDate);
            moduleField.setLastModificationDate(lastModifiedDate);

             session.saveOrUpdate(moduleField);

             tx.commit();
            session.close();

            rtn.put("ID", moduleFieldId);
            rtn.put("lastModifiedDate", formatterTime.format(lastModifiedDate));
        }
    } catch (Exception e) {
        // Handle exception and rollback transaction if necessary
        if (tx != null) tx.rollback();
        logger.error("Error in saving Module Screen with a message: ", e);
        rtn.put("Error", "Error occurred while saving the module field.");
    } finally {
        // Ensure the session is closed properly
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    return rtn;
}
	
	}


