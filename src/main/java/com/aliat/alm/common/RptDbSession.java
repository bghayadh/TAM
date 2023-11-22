package com.aliat.alm.common;

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
    private RptDbSession() {
        try {
        	builder = new StandardServiceRegistryBuilder().configure("/almrpt.cfg.xml").build();
        	sessionFactory= new MetadataSources(builder).buildMetadata().buildSessionFactory();
             
            
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("Error in creating session with Database");
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
        	e.printStackTrace();
            System.out.println("Error in opening session with Database");
        }
        return null;
    }
}
