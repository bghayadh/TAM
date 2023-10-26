package com.aliat.alm.services;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import com.aliat.alm.models.User;
import com.aliat.alm.securepassword.EncryptDecryptPassword;

public class LoginServices {
	private static final String DECRYPT_KEY = "alm";

	public String validate(String userName, String password, HttpServletResponse response, HttpServletRequest request,
			Model model) {

		/*
		 * When using hibernate 5 , the code of opening a session is different
		 * 
		 * Configuration cfg = new Configuration().configure();
		 * StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
		 * .applySettings(cfg.getProperties()); SessionFactory sf =
		 * cfg.buildSessionFactory(builder.build()); Session session = sf.openSession();
		 * Transaction tx = session.beginTransaction();
		 */

		StandardServiceRegistry builder = new StandardServiceRegistryBuilder().configure().build();
		SessionFactory sf = new MetadataSources(builder).buildMetadata().buildSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		try {

			User user = new User();
			@SuppressWarnings("rawtypes")
			Query q = session.createQuery("from User where USER_NAME =:username");
			// Query q = session.createSQLQuery("select * from USERS_TABLE where USER_NAME=
			// '"+userName+"'");
			q.setParameter("username", userName);
			user = (User) q.uniqueResult();

			if (user != null) {
				System.out.println("enc pass " + user.getUserpass());
				if (password.equals(EncryptDecryptPassword.decrypt(user.getUserpass(), DECRYPT_KEY))) {
					String userFullName = user.getUserName();

					HttpSession httpSession = request.getSession();

					String id = httpSession.getId();
					System.out.println(id);
					httpSession.setAttribute("userFullName", userFullName);
					httpSession.setAttribute("userName", userName);

					response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
					response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
					response.setDateHeader("Expires", 0); // Proxies.

					return "redirect:/Dashboard";

				} else {
					model.addAttribute("Message", "Invalid Password!");
					return "redirect:/";
				}

			} else {
				// System.out.println("invalid UserName");
				model.addAttribute("Message", "Invalid Username!");
				return "redirect:/";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/";
		} finally {
			tx.commit();
			session.close();
			session.getSessionFactory().close();
		}
	}

	public static String logout(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException, IOException {
		ModelAndView view = new ModelAndView();
		view.setViewName("Login");
		HttpSession httpSession = request.getSession(false);
		try {
			if (httpSession != null) {
				if (httpSession.getAttribute("userName") != null) {
					httpSession.removeAttribute("userName");
					httpSession.removeAttribute("userFullName");
					httpSession.invalidate();

					return "redirect:/";
				} else {
					httpSession.invalidate();
					return "redirect:/";
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/";
		}
		return "redirect:/";

	}

	public static String checkSession(HttpServletRequest request, HttpServletResponse response) {

		HttpSession httpSession = request.getSession(false);
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); //

		if (httpSession != null) {
			System.out.println("session not null");
			if (httpSession.getAttribute("userName") != null) {
				System.out.println("user login");
				return "";
			} else {
				return "redirect:/";

			}
		} else {
			return "redirect:/";
		}
	}
}