package com.aliat.alm.dbservices;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aliat.alm.models.WorkOrderDT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aliat.alm.models.WorkOrder;



@Repository
public class WorkOrderDBservice {

	public  <T> Session getSession() {

		try {

			Configuration cfg = new Configuration().configure();

			StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
					.applySettings(cfg.getProperties());

			SessionFactory sf = cfg.buildSessionFactory(builder.build());

			Session session = sf.openSession();
			return session;
		} catch (Exception e) {
			
		}

		return null;
	}
	
	
public List<WorkOrderDT> getWokOrderDt() {
		
		Session session=getSession();
		Query q = session.createQuery("select 1, a.workOrdId as ID, a.fromWare  || ':'|| a.warehouseSourceName || ':'|| siteIdSource , a.toWare || ':'|| a.warehouseNameDest || ':'|| siteIdDest , TO_CHAR(a.executionDate, 'YYYY-MM-DD HH24:MI:SS') as execDate,TO_CHAR(a.woLastModifieddate, 'YYYY-MM-DD HH24:MI:SS') as LastModifieddate, a.purpose as purp "
				+ " from WorkOrder a"
				);
		
		
		
		
//		q.setResultTransformer(Transformers.aliasToBean(CellReport4G.class));				
		
		@SuppressWarnings("unchecked")
		List<WorkOrderDT> list_WorkOrderDt = (List<WorkOrderDT>) q.list();
		
		return list_WorkOrderDt;
	}


}
	
	
	

