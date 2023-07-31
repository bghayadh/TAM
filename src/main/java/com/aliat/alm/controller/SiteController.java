package com.aliat.alm.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.aliat.alm.common.ALMSessions;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class SiteController {
	
	@Autowired
	ALMSessions almsessions;
	@RequestMapping(value = "/Sitelistview", method = RequestMethod.GET)
	public String SiteListView(Locale locale, Model model, HttpServletResponse httpResponse,
			HttpServletRequest httpRequest) throws JsonGenerationException, JsonMappingException, IOException {
		HttpSession httpSession = httpRequest.getSession(false);
		httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		httpResponse.setDateHeader("Expires", 0); //

		if (httpSession != null) {
			if (httpSession.getAttribute("userName") != null) {
				System.out.println("user login");
			} else {
				return "redirect:/";
			}
		} else {
			return "redirect:/";
		}
		System.out.println("site list view");
		List<Object[]> role = new ArrayList<Object[]>();
		try {
			Session session = almsessions.getSession();
			Transaction tx = session.beginTransaction();

			role = session.createQuery(
					"select 1, t.wareID, t.wareSiteID, t.warehouseName, t.wcreationDate, t.wlastModifieddate from Warehouse t")
					.list();
			Date date;
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			List<Object[]> finalList = new ArrayList<Object[]>();
			if (role != null) {
				for (Object[] obj : role) {
//					if(obj[2] == null) {
//						obj[2] ="NO SITE ID!";
//					}
					date = (java.util.Date) obj[4];
					obj[4] = formatter.format(date).toString();
					date = (java.util.Date) obj[5];
					obj[5] = formatter.format(date).toString();
					finalList.add(obj);
				}
			}
			tx.commit();
			session.close();
			ObjectMapper mapper = new ObjectMapper();
			model.addAttribute("siteList", mapper.writeValueAsString(finalList));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return "Network/SiteListView";
	}
	@RequestMapping(value = "/Siteformview", method = RequestMethod.GET)
	public String SiteFormView(Locale locale, Model model, HttpServletResponse httpResponse,
			HttpServletRequest httpRequest) throws JsonGenerationException, JsonMappingException, IOException {
		HttpSession httpSession = httpRequest.getSession(false);
		httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		httpResponse.setDateHeader("Expires", 0); //

		if (httpSession != null) {
			if (httpSession.getAttribute("userName") != null) {
				System.out.println("user login");
			} else {
				return "redirect:/";
			}
		} else {
			return "redirect:/";
		}
		return "Network/SiteFormView";
	}
	@RequestMapping(value = "/Nodeformview", method = RequestMethod.GET)
	public String NodeFormView(Locale locale, Model model, HttpServletResponse httpResponse,
			HttpServletRequest httpRequest) throws JsonGenerationException, JsonMappingException, IOException {
		HttpSession httpSession = httpRequest.getSession(false);
		httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		httpResponse.setDateHeader("Expires", 0); //

		if (httpSession != null) {
			if (httpSession.getAttribute("userName") != null) {
				System.out.println("user login");
			} else {
				return "redirect:/";
			}
		} else {
			return "redirect:/";
		}
		return "Network/NodeFormView";
	}
	@RequestMapping(value = "/Cellformview", method = RequestMethod.GET)
	public String CellFormView(Locale locale, Model model, HttpServletResponse httpResponse,
			HttpServletRequest httpRequest) throws JsonGenerationException, JsonMappingException, IOException {
		HttpSession httpSession = httpRequest.getSession(false);
		httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		httpResponse.setDateHeader("Expires", 0); //

		if (httpSession != null) {
			if (httpSession.getAttribute("userName") != null) {
				System.out.println("user login");
			} else {
				return "redirect:/";
			}
		} else {
			return "redirect:/";
		}
		return "Network/CellFormView";
	}
}
