package com.aliat.alm.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SystemSettingsController {

	
	@RequestMapping(value = "/systemSettings", method = RequestMethod.GET)
	public String antennaDetails(Locale locale, Model model) {
		//logger.info("Welcome home! The client locale is {}.", locale);

		
			return "systemSettings";
	}
	
	
}
