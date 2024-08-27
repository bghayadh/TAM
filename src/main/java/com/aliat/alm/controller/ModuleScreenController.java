package com.aliat.alm.controller;

import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import com.aliat.alm.models.ModuleScreen;
import com.aliat.alm.models.PurchaseOrderItem;
import com.aliat.alm.models.PurchaseRequest;
import com.aliat.alm.services.ItemParameters;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class ModuleScreenController {
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
	private static final Logger logger = LoggerFactory.getLogger(ModuleScreenController.class);

	@RequestMapping(value = "/ModuleScreenListView", method = RequestMethod.GET)
	public String ModuleScreenListView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

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
							"select t.id,t.id,t.screenName, t.screenTable, TO_CHAR(t.creationDate, 'YYYY-MM-DD HH24:MI:SS'),"
							+ "TO_CHAR(t.lastModificationDate, 'YYYY-MM-DD HH24:MI:SS')  from ModuleScreen t order by t.lastModificationDate DESC ")
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
			return "ModuleScreenListView";
		}
	
	
	
	@RequestMapping(value = "/DeleteModuleScreenListView", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> DeleteModuleScreenListView(Locale locale, Model model, HttpServletRequest request,
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
				query = session.createQuery("delete ModuleScreen where ID IN (:param1)");
				query.setParameterList("param1", idList);
				query.executeUpdate();

				
			} catch (Exception e) {
				logger.info("Error in deleting on the level of ModuleScreen with a message : " + e + "\n"
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
	
	
	
	@RequestMapping(value = "/ModuleScreenFormView", method = RequestMethod.GET)
	public String ModuleScreenFormView(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		DateFormat formatterTime = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		
		Date date= new Date();
		String result [] =new String[4];
		
		String ScreenId = request.getParameter("ID"); 
		String navAction = request.getParameter("NavAction"); 
		String itemCategoryDetails = "";
		int SelectedIndex = 0;
		
		AssetRegistry assetreg; 
		ModuleScreen ModuleScreenInfo = new ModuleScreen();
		
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {

			tx = session.beginTransaction();
			notifications.headerNotifications(session, model);
			
			try {
				String sql = "SELECT table_name FROM user_tables ORDER BY table_name";
                NativeQuery<String> query = session.createNativeQuery(sql);
 
		        // Execute the query and get results
		        List<String> resultList = query.getResultList();
		        model.addAttribute("TablesList",resultList);
				if (ScreenId == null) { 
					
					model.addAttribute("status","AddNew");
					model.addAttribute("SelectedIndex","addNew");
					model.addAttribute("moduleScreenCount","addNew");
					
					return "ModuleScreenFormView"; 
					
					}
		
				else {
					result = form.NavigationNP(session,"MODULE_SCREEN","ID",ScreenId,"LAST_MODIFICATION_DATE",navAction);		
					
					SelectedIndex= Integer.parseInt(result[1]);
					System.out.println(SelectedIndex);
					ScreenId=result[2];

					model.addAttribute("SelectedIndex", SelectedIndex);
					model.addAttribute("moduleScreenCount", Integer.parseInt(result[0]));
				
					ModuleScreenInfo = (ModuleScreen) session.get(ModuleScreen.class, ScreenId);
					
					  model.addAttribute("id", ModuleScreenInfo.getId());
		                model.addAttribute("screenName", ModuleScreenInfo.getScreenName());
		                model.addAttribute("screenTable", ModuleScreenInfo.getScreenTable());
		                if(ModuleScreenInfo.getCreationDate() != null) {
		                	date = ModuleScreenInfo.getCreationDate();
		                }
		                model.addAttribute("creationDate",formatterTime.format(date).toString() );
		                if(ModuleScreenInfo.getLastModificationDate() != null) {
		                	date = ModuleScreenInfo.getLastModificationDate();
		                }
		                model.addAttribute("lastModificationDate",formatterTime.format(date).toString());
		                    
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
		return "ModuleScreenFormView";
	}
	
	
	
	@RequestMapping(value = "/ModuleScreenFormSave", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> ModuleScreenFormSave(Locale locale, Model model, HttpServletRequest request, @ModelAttribute ItemParameters itemParameters, HttpServletResponse response) {
	    Map<String, Object> rtn = new LinkedHashMap<>();
	    String loginStatus = LoginServices.checkSession(request, response);
	    
	    if (loginStatus.equals("redirect:/")) {
	        rtn.put("Login", loginStatus);
	        return rtn;
	    }
	    
	    Session session = null;
	    Transaction tx = null;
	    
	    try {
	        Calendar calendar = new GregorianCalendar();
	        calendar.setTime(new Date());
	        int year = calendar.get(Calendar.YEAR);
	        DateFormat formatterTime = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
	        
	        Timestamp CreationDate, lastModifiedDate;
	        Date date;
	        String moduleScreenId = request.getParameter("moduleScreenId");
	        String ScreenName = request.getParameter("screenName");
	        String screenTable = request.getParameter("screenTable");
	        String createdDate = request.getParameter("createdate");
	        
	        session = AlmDbSession.getInstance().getSession();
	        if (session != null && session.isOpen()) {
	            tx = session.beginTransaction();
	            
	            // Check if a row with the same ScreenName and ScreenTable already exists
	            Long count = (Long) session.createQuery("SELECT COUNT(*) FROM ModuleScreen WHERE screenName = :screenName ")
	                    .setParameter("screenName", ScreenName)
	            
	                    .uniqueResult();
	            
	            if (count > 0) {
	                // If a row exists, do not save and return a message
	                rtn.put("error", "A record with the same Screen Name and Screen Table already exists.");
	                return rtn;
	            }
	            
	            // Create or update the ModuleScreen record
	            ModuleScreen moduleScreen = new ModuleScreen();
	            
	            if (StringUtils.isBlank(moduleScreenId)) {
	                synchronized (this) {
	                    moduleScreenId = "Module_Screen_" + year + "_" + Integer.parseInt(session.createNativeQuery("SELECT MODULE_SCREEN FROM SEQ_TABLE").uniqueResult().toString());    
	                    session.createNativeQuery("UPDATE SEQ_TABLE SET MODULE_SCREEN = MODULE_SCREEN + 1 ").executeUpdate();
	                    session.createNativeQuery("commit").executeUpdate();
	                }
	            }
	            
	            if (StringUtils.isBlank(createdDate)) {
	                date = new Timestamp(System.currentTimeMillis());
	                CreationDate = new Timestamp(date.getTime());
	            } else {
	                date = formatterTime.parse(createdDate);
	                CreationDate = new Timestamp(date.getTime());
	            }
	            
	            date = new Timestamp(System.currentTimeMillis());
	            lastModifiedDate = new Timestamp(date.getTime());
	            
	            moduleScreen.setId(moduleScreenId);
	            moduleScreen.setScreenName(ScreenName);
	            moduleScreen.setScreenTable(screenTable);
	            moduleScreen.setCreationDate(CreationDate);
	            moduleScreen.setLastModificationDate(lastModifiedDate);
	            
	            session.saveOrUpdate(moduleScreen);
	            
	            tx.commit();
	            rtn.put("ID", moduleScreenId);
	            rtn.put("lastModifiedDate", formatterTime.format(new Timestamp(System.currentTimeMillis())).toString());
	        }
	    } catch (Exception e) {
	        logger.error("Error in saving Module Screen with a message: " + e.getMessage(), e);
	        rtn.put("error", "An error occurred while saving the record.");
	    } finally {
	        if (tx != null && tx.isActive()) {
	            tx.rollback();
	        }
	        if (session != null && session.isOpen()) {
	            session.close();
	        }
	    }
	    
	    return rtn;
	}

	
	// getting All AR using autocomplete in navigation
		@RequestMapping(value = "/GetAllModuleScreen", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> GetAllModuleScreen(Locale locale, Model model, HttpServletRequest request, 
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
					query = session.createNativeQuery("select ID, SCREEN_NAME, SCREEN_TABLE from MODULE_SCREEN where LOWER(ID) like LOWER(:param1) "
							+ "or LOWER(SCREEN_NAME) like LOWER(:param1) or LOWER(SCREEN_TABLE) like LOWER(:param1) ORDER BY LAST_MODIFICATION_DATE DESC");
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
		
		@RequestMapping(value = "/ModuleScreenDelForm", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> ModuleScreenDelForm(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
			Map<String, Object> rtn = new LinkedHashMap<>();
			
			if(LoginServices.checkSession(request, response).equals("redirect:/")) {
				rtn.put("Login", LoginServices.checkSession(request, response));
				return rtn;
			}
					
			String idForm = request.getParameter("moduleScreenId");
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {
				            tx = session.beginTransaction();
				            try {
				               query = session.createNativeQuery("Delete MODULE_SCREEN where ID = '"+ idForm +"'");
				               query.executeUpdate();


				            } catch (Exception e) {
				                             logger.info("could not connect to DB in Module Screen Form delete method ");
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
		
	}


