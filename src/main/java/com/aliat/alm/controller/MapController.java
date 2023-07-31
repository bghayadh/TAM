package com.aliat.alm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.ALMSessions;
import com.aliat.alm.services.LoginServices;

@Controller
public class MapController {

	@Autowired
	ALMSessions almsessions;
	@RequestMapping("/getMapsLocations")
	@ResponseBody
	public Map<String, Object> getMapsLocations(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			map.put("Login", LoginServices.checkSession(request, response));
			return map;
		}
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();

		Query q = session.createQuery("select wareID,warehouseName,wareLong,wareLat from Warehouse");
		List<Object[]> mapList = q.list();

		tx.commit();
		session.close();
		map.put("maps", mapList);
		return map;
	}
	
	@RequestMapping("/getMarkerInfo")
	@ResponseBody
	public Map<String, Object> getMarkerInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			map.put("Login", LoginServices.checkSession(request, response));
			return map;
		}
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();

		Query q = session.createQuery("select wareID,warehouseName,wareLong,wareLat from Warehouse where wareID =:markerId");
		if(request.getParameter("markerId")!= null) {
			q.setParameter("markerId", request.getParameter("markerId"));
			List<Object[]> mapList = q.list();
			map.put("markerInfo", mapList);
		}
		

		tx.commit();
		session.close();
		
		return map;
	}

	@RequestMapping(value = "/GisPage", method = RequestMethod.GET)
	public String Networks(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		// logger.info("Welcome home! The client locale is {}.", locale);
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		} else {
			return "Network/gis2";
		}
	}

	@RequestMapping("/getSearchMapsLocations")
	@ResponseBody
	public Map<String, Object> getSearchMapsLocations(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("clicked..");
		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			map.put("Login", LoginServices.checkSession(request, response));
			return map;
		}
		Session session = almsessions.getSession();
		Transaction tx = session.beginTransaction();

		String from = request.getParameter("from");
		String id = request.getParameter("id");
		System.out.println(from);
		System.out.println(id);
		if (from.equals("warehouse") && id != null) {
			System.out.println("map warehouse");
			// select from warehouse
			Query q = session
					.createQuery("select wareID,warehouseName,wareLong,wareLat from Warehouse where wareID =:mId");
			q.setParameter("mId", id);
			Object[] obj = (Object[])q.uniqueResult();			
			map.put("from", "warehouse");
			map.put("maps", obj);
			return map;
		}else {
			if(from.equals("supplier") && id != null) {
				Query q = session.createSQLQuery(
						"select FIXED_ASSET_REGISTRY.SUPPLIER_ID,FIXED_ASSET_REGISTRY.SUPPLIER_NAME,FIXED_ASSET_REGISTRY.SITE_ID,WAREHOUSE.LONGITUDE,WAREHOUSE.LATITUDE from FIXED_ASSET_REGISTRY INNER JOIN WAREHOUSE on FIXED_ASSET_REGISTRY.SITE_ID = WAREHOUSE.SITE_ID where FIXED_ASSET_REGISTRY.SUPPLIER_ID = '"+id+"'");
				List<Object[]> mapList = q.list();
				System.out.println(mapList.size()+" list size");
				map.put("from", "supplier");
				map.put("maps", mapList);
				return map;
			}else {
				if(from.equals("item") && id !=null) {
					Query q = session.createSQLQuery(
							"select FIXED_ASSET_REGISTRY.ITEM_CODE,FIXED_ASSET_REGISTRY.ITEM_NAME,FIXED_ASSET_REGISTRY.SUPPLIER_NAME,FIXED_ASSET_REGISTRY.SITE_ID,WAREHOUSE.LONGITUDE,WAREHOUSE.LATITUDE from FIXED_ASSET_REGISTRY INNER JOIN WAREHOUSE on FIXED_ASSET_REGISTRY.SITE_ID = WAREHOUSE.SITE_ID where FIXED_ASSET_REGISTRY.ITEM_CODE = '"+id+"'");
					List<Object[]> mapList = q.list();
					System.out.println(mapList.size()+" list size");
					map.put("from", "item");
					map.put("maps", mapList);
					return map;
				}
			}
			
		}
		

		Query q = session.createSQLQuery(
				"select FIXED_ASSET_REGISTRY.SUPPLIER_ID,FIXED_ASSET_REGISTRY.SUPPLIER_NAME,FIXED_ASSET_REGISTRY.SITE_ID,WAREHOUSE.LONGITUDE,WAREHOUSE.LATITUDE from FIXED_ASSET_REGISTRY INNER JOIN WAREHOUSE on FIXED_ASSET_REGISTRY.SITE_ID = WAREHOUSE.SITE_ID");
		List<Object[]> mapList = q.list();
		tx.commit();
		session.close();
		map.put("maps", mapList);
		return map;
	}

}
