package com.aliat.alm.common;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

@Service
public class ALMSessions {
	
	public  <T> Session getSession() {
		try {

		/*	When using hibernate 5 , the code of opening a session is different  

		 * 
		 * Configuration cfg = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
		SessionFactory sf = cfg.buildSessionFactory(builder.build());
		Session session = sf.openSession();		
		return session;*/
			
			StandardServiceRegistry builder = new StandardServiceRegistryBuilder().configure().build();
			SessionFactory sf = new MetadataSources(builder).buildMetadata().buildSessionFactory();
			Session session = sf.openSession();
			return session;
		
		} catch (Exception e) {
		System.out.println("Error in creating session with Database");
		}

		return null;

		}
	
	


public  <T> Session getSessionRPT() {

try {

/*	Configuration  cfg = new Configuration().configure("/almrpt.cfg.xml");
	StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
	SessionFactory sf = cfg.buildSessionFactory(builder.build());
	Session session = sf.openSession();
	return session;
	*/
	StandardServiceRegistry builder = new StandardServiceRegistryBuilder().configure("/almrpt.cfg.xml").build();
	SessionFactory sf = new MetadataSources(builder).buildMetadata().buildSessionFactory();
	Session session = sf.openSession();
	return session;
	
} catch (Exception e) {
System.out.println("Error in creating session with Database RPT");
}

return null;

}
	
	public String TestSession() {
		//System.out.println("hehehehehhe");
		String result = "Test Completed Successfully";
		return result;

		}

}
