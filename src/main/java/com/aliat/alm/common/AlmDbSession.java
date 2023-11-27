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

import com.aliat.mobile.restapi.Mobile_SIM_Reg;

@Service
public class AlmDbSession {
	private static AlmDbSession instance = null;
    private SessionFactory sessionFactory;
    private static StandardServiceRegistry builder = null;
    private static StringWriter sw;
	private static String exceptionAsString;
	private static final Logger logger = Logger.getLogger(AlmDbSession.class.getName());
    private AlmDbSession() {
        try {
             builder = new StandardServiceRegistryBuilder().configure().build();
             sessionFactory = new MetadataSources(builder).buildMetadata().buildSessionFactory();
             
            
        } catch (Exception e) {
        	sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			exceptionAsString = sw.toString();
			logger.finest("Error in AlmDbSession due to \n " + exceptionAsString);
			logger.info("Error in AlmDbSession due to \n " + exceptionAsString);
        }
    }

    public static AlmDbSession getInstance() {
        if (instance == null) {
            instance = new AlmDbSession();
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
		logger.finest("Error in Alm getSession due to \n " + exceptionAsString);
		logger.info("Error in Alm getSession due to \n " + exceptionAsString);
        }
        return null;
    }
}
