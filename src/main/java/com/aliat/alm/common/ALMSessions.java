package com.aliat.alm.common;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

@Service
public class ALMSessions {
	
	public  <T> Session getSession() {
		try {

		Configuration cfg = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
		SessionFactory sf = cfg.buildSessionFactory(builder.build());
		Session session = sf.openSession();
		
		
		return session;
		} catch (Exception e) {
		System.out.println("Error in creating session with Database");
		}

		return null;

		}
	
	


public  <T> Session getSessionRPT() {

try {

	Configuration  cfg = new Configuration().configure("/almrpt.cfg.xml");
	StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
	SessionFactory sf = cfg.buildSessionFactory(builder.build());
	Session session = sf.openSession();

return session;
} catch (Exception e) {
System.out.println("Error in creating session with Database RPT");
}

return null;

}
	
	public String TestSession() {
		System.out.println("hehehehehhe");
		String result = "Test Completed Successfully";
		return result;

		}

}
