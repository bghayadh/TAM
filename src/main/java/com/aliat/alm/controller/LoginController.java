package com.aliat.alm.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.aliat.alm.services.LoginServices;

@Controller
public class LoginController {
	LoginServices loginServices = new LoginServices();

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String ALM(Locale locale, Model model, HttpServletResponse responce, HttpServletRequest httpRequest) {
		// logger.info("Welcome home! The client locale is {}.", locale);
		HttpSession httpSession = httpRequest.getSession(false);
		responce.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		responce.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		responce.setDateHeader("Expires", 0); //

		if (httpSession != null) {
			System.out.println("session not null");
			if (httpSession.getAttribute("userName") != null) {
				return "redirect:/Dashboard";
			} else {
				System.out.println("no user");
				return "Login";
			}
		} else {
			System.out.println("no user");
			return "Login";
		}
	}

	//@PostMapping("/loginAuth")
	@RequestMapping(value = "/loginAuth", method = RequestMethod.POST)
	public String LoginAuthentication(@RequestParam(value = "usernameAuth", required=true, defaultValue="") String username,
			@RequestParam(value = "PasswordAuth", required=true, defaultValue="") String password, HttpServletResponse responce, HttpServletRequest httpRequest,
			Model model) {
		HttpSession httpSession = httpRequest.getSession(false);
		responce.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		responce.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		responce.setDateHeader("Expires", 0); //

		if (httpSession != null) {
			System.out.println("session not null");
			if (httpSession.getAttribute("userName") != null) {
				System.out.println("user login");
				return "redirect:/Dashboard";
			} else {
				System.out.println("Welcome to login Auth");
				String navigate = loginServices.validate(username, password, responce, httpRequest, model);

				System.out.println("use " + username + " pa" + password);
				return navigate;

			}
		} else {
			System.out.println("session null");			
			return "redirect:/";
		}

	}
	@RequestMapping("/logout")
	public String logout(HttpServletResponse responce, HttpServletRequest httpRequest,
			Model model) {
		ModelAndView view = new ModelAndView();
		 view.setViewName("Login");
		try {
			return LoginServices.logout(httpRequest, responce, model);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/";
		}
	}

}
