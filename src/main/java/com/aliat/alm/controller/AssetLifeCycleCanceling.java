package com.aliat.alm.controller;


import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssetLifeCycleCanceling extends Thread {

	private Session session = null;
	private Transaction tx = null;
	private String queryString = null;
	private Query query = null;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	String formView;
	String pRqID;
	String pOID;
	String grID;

	public AssetLifeCycleCanceling(String formView, String prID, String poID, String grID) {
		this.formView = formView;
		this.pRqID = prID;
		this.pOID = poID;
		this.grID = grID;
	}

	@Override
	public void run() {

		Configuration cfg = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(cfg.getProperties());
		SessionFactory sf = cfg.buildSessionFactory(builder.build());
		session = sf.openSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {
				if (StringUtils.equalsIgnoreCase(formView, "PR")) {

					queryString = "delete AssetLifeCycle t where t.voucherNB =:param1";
					query = session.createQuery(queryString);
					query.setParameter("param1", pRqID);
					query.executeUpdate();
					
				} else if (StringUtils.equalsIgnoreCase(formView, "PO")) {
					queryString = "delete AssetLifeCycle t where t.voucherNB =:param1 ";
					query = session.createQuery(queryString);
					query.setParameter("param1", pOID);
					query.executeUpdate();

					if (!pRqID.isEmpty()) {

						queryString = "update PurchaseRequestItem set prqPoStatus = :param_1  where PRQId = :param_2";
						query = session.createQuery(queryString);
						query.setString("param_1", "0");
						query.setString("param_2", pRqID);
						query.executeUpdate();
					}
				} else if (StringUtils.equalsIgnoreCase(formView, "GR")) {

					if (pOID.isEmpty()) {
						if (!pRqID.isEmpty()) {
							queryString = "update PurchaseRequestItem set prqPoStatus = :param_1  where PRQId = :param_2";
							query = session.createQuery(queryString);
							query.setString("param_1", "0");
							query.setString("param_2", pRqID);
							query.executeUpdate();
						}
					} else {
						queryString = "update PurchaseOrderItem set poItemStatus = :param_1  where POId = :param_2";
						query = session.createQuery(queryString);
						query.setString("param_1", "0");
						query.setString("param_2", pOID);
						query.executeUpdate();
						if (!pRqID.isEmpty()) {
							queryString = "update PurchaseRequestItem set prqPoStatus = :param_1  where PRQId = :param_2";
							query = session.createQuery(queryString);
							query.setString("param_1", "0");
							query.setString("param_2", pRqID);
							query.executeUpdate();
						}
					}
					queryString = "delete AssetLifeCycle t where t.voucherNB =:param1 ";
					query = session.createQuery(queryString);
					query.setString("param1", grID);
					query.executeUpdate();

				}
			} catch (Exception e) {
				logger.info("Error in the canceling of asset life cycle on the level of : " + formView
						+ " with a message : " + e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
	}

}
