package com.aliat.alm.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.aliat.alm.services.LoginServices;

import org.springframework.http.HttpStatus;


@Controller
public class showdatecontroller {
	@RequestMapping(value = "/showdate", method = RequestMethod.GET, headers="Accept=*/*",consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public  Map<String, Object> getTime(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		//public String getTime(Locale locale, Model model, HttpServletRequest request) {	
		Map<String, Object> rtn = new LinkedHashMap<>();
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			rtn.put("Login", LoginServices.checkSession(request, response));
			return rtn;
		}
		System.out.println("Just reached getTime controller");
		System.out.println("The variable is " + request.getParameter("Test"));
 
        //Random rand = new Random();
        //float r = rand.nextFloat() * 100;
        //String result = "<br>Next Random # is <b>" + r + "</b>. Generated on <b>" + new Date().toString() + "</b>";
		String result = "Date Generated on " + new Date().toString() ;
		
		System.out.println("The result is " + result);
        //System.out.println("Debug Message from CrunchifySpringAjaxJQuery Controller.." + new Date().toString());
		
		rtn.put("Bassam", result);
        return rtn;
		//return result;
    }
}
