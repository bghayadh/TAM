package com.aliat.alm.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class Permissions {
	private static ObjectMapper mapper = new ObjectMapper();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  <T> List<Object[]> getUserPerms(HttpServletRequest request) {
		
		HttpSession httpSession = request.getSession(false);
		Object username = httpSession.getAttribute("userName");
		
		Configuration cfg = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(cfg.getProperties());
		SessionFactory sf = cfg.buildSessionFactory(builder.build());
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		String roleQuery = "select rolename from UserRole where username =:param1";
		
		Query roleResult = session.createQuery(roleQuery);
		roleResult.setParameter("param1", username);			
		//Object role = roleResult.uniqueResult();
		List <String> roles = roleResult.list();
		
		String permQuery = "select screen, viewType, roleLevel, readPerm, writePerm, addPerm, delPerm,"+ 
							" savePerm, statusPerm, actionPerm, downloadPerm from RolePermission where role IN (:param2)";
		Query permResult = session.createQuery(permQuery);
		permResult.setParameterList("param2", roles);
		List <Object[]> permList = permResult.list();
		
		tx.commit();
		session.close();
		
	return permList;

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  <T> List<Object[]> getUserPermsWithSession(Session session,HttpServletRequest request) {
		
		HttpSession httpSession = request.getSession(false);
		Object username = httpSession.getAttribute("userName");
		List <Object[]> permList = null;
		String roleQuery = "select rolename from UserRole where username =:param1";
		Query roleResult = session.createQuery(roleQuery);
		roleResult.setParameter("param1", username);			
		//Object role = roleResult.uniqueResult();
		
		List <String> roles = roleResult.list();
		
		String permQuery = "select screen, viewType, roleLevel, readPerm, writePerm, addPerm, delPerm,"+ 
				" savePerm, statusPerm, actionPerm, downloadPerm,exportPerm,secondLevelPerm,firstLevelPerm," +
				" searchPopupPerm, findConnectedPerm, projectsPerm, approveRejectPerm" +
				" from RolePermission where role IN (:param2)";
		
		Query permResult = session.createQuery(permQuery);
		permResult.setParameterList("param2", roles);
		permList = permResult.list();
		 		
	return permList;

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  <T> List<Object[]> getUserPrem(EntityManager entityManager,HttpServletRequest request) {
		
		HttpSession httpSession = request.getSession(false);
		Object username = httpSession.getAttribute("userName");
		List <Object[]> permList = null;
		String roleQuery = "select rolename from UserRole where username =:param1";
		Query roleResult = (Query) entityManager.createQuery(roleQuery);
		roleResult.setParameter("param1", username);			
		//Object role = roleResult.uniqueResult();		
		List <String> roles = roleResult.list();		
		
		String permQuery = "select screen, viewType, roleLevel, readPerm, writePerm, addPerm, delPerm,"+ 
				" savePerm, statusPerm, actionPerm, downloadPerm,exportPerm,secondLevelPerm,firstLevelPerm," +
				" searchPopupPerm, findConnectedPerm, projectsPerm, approveRejectPerm" +
				" from RolePermission where role IN (:param2)";

		
		Query permResult = (Query) entityManager.createQuery(permQuery);
		permResult.setParameterList("param2", roles);
		 permList = permResult.list();
	return permList;

	}
	@SuppressWarnings("rawtypes")
	public  <T> void setPerms(Model model, List<Object[]> permList, String screen, String viewType) {
		
		Integer read = 0; Integer write = 0; Integer add = 0; Integer delete = 0;
		Integer save = 0; Integer status = 0; Integer action = 0; Integer download = 0;
		Integer export = 0; Integer secondlvl = 0; Integer firstlvl = 0; Integer srchPopup = 0;
		Integer findConnected = 0; Integer projects = 0; Integer approveReject=0;

		for (int i = 0; i<permList.size(); i++) {
			List x = Arrays.asList(permList.get(i));
			if(x.contains(screen) && x.contains(viewType)) {
				Object[] prList = permList.get(i);
				read += Integer.parseInt(String.valueOf(prList[3]));
				write += Integer.parseInt(String.valueOf(prList[4]));
				add += Integer.parseInt(String.valueOf(prList[5]));
				delete += Integer.parseInt(String.valueOf(prList[6]));
				save += Integer.parseInt(String.valueOf(prList[7]));
				status += Integer.parseInt(String.valueOf(prList[8]));
				action += Integer.parseInt(String.valueOf(prList[9]));
				download += Integer.parseInt(String.valueOf(prList[10]));
				export += Integer.parseInt(String.valueOf(prList[11]));
				secondlvl += Integer.parseInt(String.valueOf(prList[12]));
				firstlvl += Integer.parseInt(String.valueOf(prList[13]));				
				srchPopup+= Integer.parseInt(String.valueOf(prList[14]));
				findConnected+= Integer.parseInt(String.valueOf(prList[15]));
				projects+= Integer.parseInt(String.valueOf(prList[16]));
				approveReject+= Integer.parseInt(String.valueOf(prList[17]));
				
			}
		}
		if(read > 1) {read = 1;} if(write > 1) {write = 1;} if(add > 1) {add = 1;}
		if(delete > 1) {delete = 1;} if(save > 1) {save = 1;} if(status > 1) {status = 1;}
		if(action > 1) {action = 1;} if(download > 1) {download = 1;}
		if(export > 1) {export = 1;} if(secondlvl > 1) {secondlvl = 1;}
		if(firstlvl > 1) {firstlvl = 1;} if(srchPopup > 1) {srchPopup = 1;}
		if(findConnected > 1) {findConnected = 1;} if(projects > 1) {projects = 1;}
		if(approveReject > 1) {approveReject = 1;}
		model.addAttribute("read"+viewType, read); 	model.addAttribute("write"+viewType, write);
		model.addAttribute("add"+viewType, add); 	model.addAttribute("del"+viewType, delete);
		model.addAttribute("save"+viewType, save); 	model.addAttribute("status"+viewType, status);
		model.addAttribute("action"+viewType, action); 	model.addAttribute("down"+viewType, download);
		model.addAttribute("export"+viewType, export); 	model.addAttribute("secondlvl"+viewType, secondlvl);
		model.addAttribute("firstlvl"+viewType, firstlvl); model.addAttribute("srchPopup"+viewType, srchPopup);
		model.addAttribute("findConnected"+viewType, findConnected); model.addAttribute("projects"+viewType, projects);		
		model.addAttribute("approveReject"+viewType, approveReject); 
	}
	
	 @SuppressWarnings("rawtypes")
	    public void checkAndAddExceptions(Model model, String readPerm, String writeperm, Session session, String Type,HttpServletRequest request) throws JsonProcessingException {
		 HttpSession httpSession = request.getSession(false);
			Object username = httpSession.getAttribute("userName");
			List <Object[]> permList = null;
			String roleQuery = "select rolename from UserRole where username =:param1";
			Query roleResult = session.createQuery(roleQuery);
			roleResult.setParameter("param1", username);			
		
			List <String> roles = roleResult.list();
		
	        if ("1".equals(readPerm) && "0".equals(writeperm) || "0".equals(readPerm) && "0".equals(writeperm)) {
	        	
	        	List<Object[]> exceptionWrite = session.createNativeQuery(
	        		    "SELECT FIELD_NAME, FIELD_VALUE FROM ROLE_PERMISSION_EXCEPTION "
	        		    + "WHERE SCREEN_NAME=:param1 AND ACTION='Write' AND ROLE IN :param2")
	        		    .setParameter("param1", Type).setParameter("param2", roles)
	        		    .getResultList();
	        	
	        	if("Physical Layer Manhole".equals(Type)) {
	        	model.addAttribute("exceptionManWriteList", exceptionWrite);
	 	           
				String exceptionManWriteList = mapper.writeValueAsString(exceptionWrite);
	            model.addAttribute("exceptionManWriteList1", exceptionManWriteList);
	              model.addAttribute("writeExceptionMan", "1"); 
	        }
	        	
	        	else if("Physical Layer Handhole".equals(Type)) {
	        		model.addAttribute("exceptionHandWriteList", exceptionWrite);
	      		       
					String exceptionHandWriteList = mapper.writeValueAsString(exceptionWrite);
			        model.addAttribute("exceptionHandWriteList1", exceptionHandWriteList);
			        model.addAttribute("writeExceptionHand", "1"); 
		        }
	        	
                   else if("Physical Layer DB".equals(Type)) {
                	model.addAttribute("exceptionDBWriteList", exceptionWrite);
        	 	       
					String exceptionDBWriteList = mapper.writeValueAsString(exceptionWrite);
					 
				      model.addAttribute("exceptionDBWriteList1", exceptionDBWriteList);
				      model.addAttribute("writeExceptionDB", "1"); 
					    
		        }
	        	
                   else if("Physical Layer Fiber".equals(Type)) {
                	   model.addAttribute("exceptionFiberWriteList", exceptionWrite);
            	 	   
   					String exceptionFiberWriteList = mapper.writeValueAsString(exceptionWrite);
   					 
   					  model.addAttribute("exceptionFiberWriteList1", exceptionFiberWriteList);
   					 model.addAttribute("writeExceptionFiber", "1"); 
   		        }
	        }
	        if ("1".equals(readPerm) && "1".equals(writeperm) || "0".equals(readPerm) && "0".equals(writeperm)) {
	        	List<Object[]> exceptionRead = session.createNativeQuery(
		                "SELECT FIELD_NAME, FIELD_VALUE FROM ROLE_PERMISSION_EXCEPTION "
		                + "WHERE SCREEN_NAME=:param1 AND ACTION='Read'  AND ROLE IN :param2")
	        			  .setParameter("param1", Type).setParameter("param2", roles)
	        			  .getResultList();
		            
		            if(!exceptionRead.isEmpty()) {
		            	if("Physical Layer Manhole".equals(Type)) {
				     model.addAttribute("exceptionManReadList", exceptionRead);
				     model.addAttribute("readExceptionMan", "1"); 
		             
	                
	                if("1".equals(readPerm) && "1".equals(writeperm)) {
	                	
	                	StringBuilder queryBuilder = new StringBuilder("SELECT MANHOLE_ID FROM manhole WHERE ");
	                    List<Object> parameters = new ArrayList<>();

	                    for (int i = 0; i < exceptionRead.size(); i++) {
	                        Object[] row = exceptionRead.get(i);
	                        String fieldName = (String) row[0];
	                        String fieldValue = (String) row[1];

	                        // Add to the query
	                        if (i > 0) {
	                            queryBuilder.append(" OR ");
	                        }
	                        queryBuilder.append(fieldName).append(" = ?").append(i);

	                         parameters.add(fieldValue);
	                    }

	                    Query query = session.createNativeQuery(queryBuilder.toString());
	                    for (int i = 0; i < parameters.size(); i++) {
	                        query.setParameter(i, parameters.get(i));
	                    }

	                     List<String> onlyReadExcep = query.getResultList();
	                    model.addAttribute("onlyReadManExcep", onlyReadExcep); 
	                }
	                }
		            	else  	if("Physical Layer Handhole".equals(Type)) {
						     model.addAttribute("exceptionHandReadList", exceptionRead);
						     model.addAttribute("readExceptionHand", "1"); 
					             
				                  if("1".equals(readPerm) && "1".equals(writeperm)) {
				                	
				                	StringBuilder queryBuilder = new StringBuilder("SELECT HANDHOLE_ID FROM HANDHOLE WHERE ");
				                    List<Object> parameters = new ArrayList<>();

				                    for (int i = 0; i < exceptionRead.size(); i++) {
				                        Object[] row = exceptionRead.get(i);
				                        String fieldName = (String) row[0];
				                        String fieldValue = (String) row[1];

				                        // Add to the query
				                        if (i > 0) {
				                            queryBuilder.append(" OR ");
				                        }
				                        queryBuilder.append(fieldName).append(" = ?").append(i);

				                         parameters.add(fieldValue);
				                    }

				                    Query query = session.createNativeQuery(queryBuilder.toString());
				                    for (int i = 0; i < parameters.size(); i++) {
				                        query.setParameter(i, parameters.get(i));
				                    }

				                     List<String> onlyReadExcep = query.getResultList();
				                    model.addAttribute("onlyReadHandExcep", onlyReadExcep); 
				                }
				                }
		            	
		             	else  if("Physical Layer DB".equals(Type)) {
						     model.addAttribute("exceptionDBReadList", exceptionRead);
						     model.addAttribute("readExceptionDB", "1"); 
					           
				           
				                if("1".equals(readPerm) && "1".equals(writeperm)) {
				                	
				                	StringBuilder queryBuilder = new StringBuilder("SELECT DB_ID FROM DISTRIBUTION_BOARD WHERE ");
				                    List<Object> parameters = new ArrayList<>();

				                    for (int i = 0; i < exceptionRead.size(); i++) {
				                        Object[] row = exceptionRead.get(i);
				                        String fieldName = (String) row[0];
				                        String fieldValue = (String) row[1];

				                        // Add to the query
				                        if (i > 0) {
				                            queryBuilder.append(" OR ");
				                        }
				                        queryBuilder.append(fieldName).append(" = ?").append(i);

				                         parameters.add(fieldValue);
				                    }

				                    Query query = session.createNativeQuery(queryBuilder.toString());
				                    for (int i = 0; i < parameters.size(); i++) {
				                        query.setParameter(i, parameters.get(i));
				                    }

				                     List<String> onlyReadExcep = query.getResultList();
				                    model.addAttribute("onlyReadDBExcep", onlyReadExcep); 
				                }
				                }
		            	
		            	
		            	
		            	else  if("Physical Layer Fiber".equals(Type)) {
						     model.addAttribute("exceptionFiberReadList", exceptionRead);
					           model.addAttribute("readExceptionFiber", "1"); 
				                   
				                if("1".equals(readPerm) && "1".equals(writeperm)) {
				                	
				                	StringBuilder queryBuilder = new StringBuilder("SELECT FIBER_CABLE_ID FROM FIBER_CABLES WHERE ");
				                    List<Object> parameters = new ArrayList<>();

				                    for (int i = 0; i < exceptionRead.size(); i++) {
				                        Object[] row = exceptionRead.get(i);
				                        String fieldName = (String) row[0];
				                        String fieldValue = (String) row[1];

				                        // Add to the query
				                        if (i > 0) {
				                            queryBuilder.append(" OR ");
				                        }
				                        queryBuilder.append(fieldName).append(" = ?").append(i);

				                         parameters.add(fieldValue);
				                    }

				                    Query query = session.createNativeQuery(queryBuilder.toString());
				                    for (int i = 0; i < parameters.size(); i++) {
				                        query.setParameter(i, parameters.get(i));
				                    }

				                     List<String> onlyReadExcep = query.getResultList();
				                    model.addAttribute("onlyReadFiberExcep", onlyReadExcep); 
				                }
				                }
		            }
	          
	            }
	        
	        else {
	        	
	        	 model.addAttribute("readExceptionMan", "0");
	        	 model.addAttribute("readExceptionHand", "0");
	        	 model.addAttribute("readExceptionDB", "0");
	        	 model.addAttribute("readExceptionFiber", "0");
	        	
	        }
	        }    
	       
	        
	        }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	    

