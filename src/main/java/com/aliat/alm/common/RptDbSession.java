package com.aliat.alm.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Service;

@Service
public class RptDbSession {
	private static RptDbSession instance = null;
    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry builder = null;
    private static StringWriter sw;
   	private static String exceptionAsString;
   	private static final Logger logger = Logger.getLogger(RptDbSession.class.getName());
    private RptDbSession() {
        try {
        	builder = new StandardServiceRegistryBuilder().configure("/almrpt.cfg.xml").build();
        	sessionFactory= new MetadataSources(builder).buildMetadata().buildSessionFactory();
             
            
        } catch (Exception e) {
        	sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in RptDbSession due to \n " + exceptionAsString);
			logger.info("Error in RptDbSession due to \n " + exceptionAsString);
        }
    }

    public static RptDbSession getInstance() {
        if (instance == null) {
            instance = new RptDbSession();
        }
        return instance;
    }

    public Session getSession() {
        try {
            return sessionFactory.openSession();
        } catch (Exception e) {
        	sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in RPT getSession due to \n " + exceptionAsString);
			logger.info("Error in RPT getSession due to \n " + exceptionAsString);
        }
        return null;
    }
}
