package com.aliat.alm.proc;

import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.models.ProcessOperation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aliat.alm.models.ProcessOperation;

@Service
public class SchedulerInitializer {

	private static Session session = null;
	private static Transaction tx = null;
	private static ObjectMapper mapper = new ObjectMapper();
	private String str = null;

	@SuppressWarnings("rawtypes")
	private static Query query = null;

	private final SchedulerService schedulerService;
	private static final Logger logger = LoggerFactory.getLogger(SchedulerInitializer.class);

	@Autowired
	public SchedulerInitializer(SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void initializeScheduledJobs() {

		session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			tx = session.beginTransaction();
			try {

				System.out.println("Initializing scheduled jobs from database...");

				// List<ProcessOperation> operations = operationDao.findAllEnabled();
				List<ProcessOperation> operations = (List<ProcessOperation>) session
						.createQuery("from ProcessOperation").list();

				for (ProcessOperation operation : operations) {
					try {
						schedulerService.scheduleJob(operation);
						System.out.println("✅ Scheduled job: " + operation.getOperationName() + " ["
								+ operation.getCronExpression() + "]");
					} catch (Exception e) {
						System.err.println(
								"❌ Failed to schedule job " + operation.getOperationName() + ": " + e.getMessage());
					}
				}
			} catch (Exception e) {
				logger.info("Error in saving CommScope Process  with a message : " + e + "\n\" + e.getMessage()", e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
	}
}