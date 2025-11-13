package com.aliat.alm.common;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Service;

@Service
public class TamDbSession {

	private final SessionFactory sessionFactory;
	private static final Logger logger = Logger.getLogger(TamDbSession.class.getName());

	public TamDbSession() {
		SessionFactory tempFactory = null;
		try {
			StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
			tempFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error initializing Hibernate SessionFactory", e);
		}
		this.sessionFactory = tempFactory;
	}

	public Session getSession() {
		try {
			return sessionFactory.openSession();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error opening Hibernate session", e);
			return null;
		}
	}
}