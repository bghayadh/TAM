package com.aliat.alm.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aliat.alm.services.LoginServices;

@Controller
public class ContractController {
private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/Contract", method = RequestMethod.GET)
	public String Contracts(Locale locale, Model model, HttpServletRequest request,HttpServletResponse response) {
		//logger.info("Welcome home! The client locale is {}.", locale);
		if(LoginServices.checkSession(request, response).equals("redirect:/")) {
			return LoginServices.checkSession(request, response);
		}else {
		return "Contract";
		}
	}
}
