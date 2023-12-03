package com.aliat.alm.controller;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.common.Notify;
import com.aliat.alm.services.LoginServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class SupplierAssetReports {

	@Autowired
	Notify notifications;

	private Session session = null;

	@SuppressWarnings("rawtypes")
	Query query;
	Object[] result;

	private static final Logger logger = LoggerFactory.getLogger(NodeAssetReportController.class);
	private static ObjectMapper mapper = new ObjectMapper();
	@RequestMapping(value = "/SupplierAssetReport", method = RequestMethod.GET)
	public String SupplierAssetReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (LoginServices.checkSession(request, response).equals("redirect:/")) {
			return "redirect:/";
		}
			session = AlmDbSession.getInstance().getSession();
			if (session != null && session.isOpen()) {

				
				try {
					notifications.headerNotifications(session, model);
					String str = "SELECT supplierID,supplierName,COALESCE(SUM(initialCost),0),COALESCE(SUM(netCost),0),COALESCE(SUM(depreciationCost),0) FROM (" + 
							" SELECT DISTINCT (A.SUPPLIER_ID) as supplierID, A.SUPPLIER_NAME as supplierName,b.INITIALCOST as initialCost,b.NETCOST as netCost,b.ACCUMULDEPRECAMNT as depreciationCost" + 
							" FROM SUPPLIER A LEFT JOIN FIXED_ASSET_REGISTRY B ON A.SUPPLIER_ID = B.SUPPLIER_ID " + 
							" WHERE B.CREATED_DATE >=  trunc(SYSDATE - INTERVAL '1' YEAR) AND B.created_date < (trunc(sysdate))+ 1)" + 
							" Group BY supplierID,supplierName";
					
					model.addAttribute("supplierAssetList", mapper.writeValueAsString(session.createNativeQuery(str).list()));
					
				} catch (Exception e) {
					logger.info("Error in creating session with the DataBase " + e.getMessage());
				} finally {
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			}
		
		return "Reports/SupplierAssetReport";
	}
	
	@RequestMapping(value = "/GenerateGridSupplierAssetReport", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GenerateGridSupplierAssetReport(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> rtn = new LinkedHashMap<>();
		
		String start_Date = request.getParameter("startDate");
		String end_Date = request.getParameter("endDate");
		String ignoreDate = request.getParameter("ignoreDate");
		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			try {
				String str = "SELECT supplierID,supplierName,COALESCE(SUM(initialCost),0),COALESCE(SUM(netCost),0),COALESCE(SUM(depreciationCost),0) FROM (" + 
						" SELECT DISTINCT (A.SUPPLIER_ID) as supplierID, A.SUPPLIER_NAME as supplierName,b.INITIALCOST as initialCost,b.NETCOST as netCost,b.ACCUMULDEPRECAMNT as depreciationCost" + 
						" FROM SUPPLIER A LEFT JOIN FIXED_ASSET_REGISTRY B ON A.SUPPLIER_ID = B.SUPPLIER_ID ";
						
				
				if (StringUtils.equalsIgnoreCase(ignoreDate, "false")) {
					str = str + " WHERE B.CREATED_DATE between TO_DATE('" + start_Date
							+ "','MM/DD/YYYY HH:MI AM') and TO_DATE('" + end_Date + "','MM/DD/YYYY HH:MI AM')";
				}
				str = str + " )Group BY supplierID,supplierName";
				rtn.put("SupplierAssetReportGrid", session.createNativeQuery(str).list());
			} catch (Exception e) {
				rtn.put("SupplierAssetReportGrid", "");
				logger.info("Error in creating session with the DataBase " + e.getMessage());
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
			
		}
		
		return rtn;
	}
}
