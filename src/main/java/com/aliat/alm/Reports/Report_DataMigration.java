package com.aliat.alm.Reports;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.aliat.alm.Reports.Get_Warehouse_WIN;
import com.aliat.alm.Reports.Get_PAYG_Revenue;
import com.aliat.alm.Reports.Get_Warehouse;



@Controller

public class Report_DataMigration {
	
	private static final Logger logger = LoggerFactory.getLogger(Report_DataMigration.class);
	
	
	@RequestMapping(value = "/sitemigration", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> sitemigration(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> rtn = new LinkedHashMap<>();
//		Get_Warehouse_WIN myClass = new Get_Warehouse_WIN();
		Get_Warehouse myClass = new Get_Warehouse();
		myClass.main(null);
		rtn.put("Result", "Script is Done");
		return rtn;
	}
	
	
	
	@RequestMapping(value = "/prepaidpaygrevenuemigration", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> prepaidpaygrevenuemigration(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> rtn = new LinkedHashMap<>();
//	
		Get_PAYG_Revenue myClass = new Get_PAYG_Revenue();
		myClass.main(null);
		rtn.put("Result", "Script is Done");
		return rtn;
	}

}
