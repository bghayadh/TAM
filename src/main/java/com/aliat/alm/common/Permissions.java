package com.aliat.alm.common;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class Permissions {

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
	public  <T> List<Object[]> getUserPermsWithSession(Session session,HttpServletRequest request) {
		
		HttpSession httpSession = request.getSession(false);
		Object username = httpSession.getAttribute("userName");
		List <Object[]> permList = null;
		String roleQuery = "select rolename from UserRole where username =:param1";
		Query roleResult = session.createQuery(roleQuery);
		roleResult.setParameter("param1", username);			
		//Object role = roleResult.uniqueResult();
		@SuppressWarnings("unchecked")
		List <String> roles = roleResult.list();
		
		String permQuery = "select screen, viewType, roleLevel, readPerm, writePerm, addPerm, delPerm,"+ 
							" savePerm, statusPerm, actionPerm, downloadPerm,exportPerm,secondLevelPerm,firstLevelPerm from RolePermission where role IN (:param2)";
		Query permResult = session.createQuery(permQuery);
		permResult.setParameterList("param2", roles);
		 permList = permResult.list();
		
	return permList;

	}
	public  <T> void setPerms(Model model, List<Object[]> permList, String screen, String viewType) {
		
		Integer read = 0; Integer write = 0; Integer add = 0; Integer delete = 0;
		Integer save = 0; Integer status = 0; Integer action = 0; Integer download = 0;
		Integer export = 0; Integer secondlvl = 0; Integer firstlvl = 0;

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

			}
		}
		if(read > 1) {read = 1;} if(write > 1) {write = 1;} if(add > 1) {add = 1;}
		if(delete > 1) {delete = 1;} if(save > 1) {save = 1;} if(status > 1) {status = 1;}
		if(action > 1) {action = 1;} if(download > 1) {download = 1;}
		if(export > 1) {export = 1;} if(secondlvl > 1) {secondlvl = 1;}
		if(firstlvl > 1) {firstlvl = 1;}
		model.addAttribute("read"+viewType, read); 	model.addAttribute("write"+viewType, write);
		model.addAttribute("add"+viewType, add); 	model.addAttribute("del"+viewType, delete);
		model.addAttribute("save"+viewType, save); 	model.addAttribute("status"+viewType, status);
		model.addAttribute("action"+viewType, action); 	model.addAttribute("down"+viewType, download);
		model.addAttribute("export"+viewType, export); 	model.addAttribute("secondlvl"+viewType, secondlvl);
		model.addAttribute("firstlvl"+viewType, firstlvl);
	}
}
