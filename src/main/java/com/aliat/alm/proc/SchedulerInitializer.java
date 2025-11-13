package com.aliat.alm.proc;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aliat.alm.common.AlmDbSession;
import com.aliat.alm.models.ProcessOperation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aliat.alm.models.ProcessOperation;

@Service
public class SchedulerInitializer {

	private static ObjectMapper mapper = new ObjectMapper();

	private final SchedulerService schedulerService;
	private static final Logger logger = LoggerFactory.getLogger(SchedulerInitializer.class);

	@Autowired
	public SchedulerInitializer(SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void initializeScheduledJobs() {

		Session session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			Transaction tx = session.beginTransaction();
			try {
				System.out.println("Initializing scheduled jobs from database...");
				List<ProcessOperation> operations = (List<ProcessOperation>) session
						.createQuery("from ProcessOperation").list();

				List<Object[]> rows = session.createNativeQuery("SELECT LINK_NAME, STATUS FROM Process").list();
				Map<String, String> mapProcStatus = new LinkedHashMap<>();
				for (Object[] row : rows) {
					mapProcStatus.put((String) row[0], (String) row[1]);
				}

				// List<str[]> listProcStatus = session.createNativeQuery("select LINK_NAME,
				// STATUS from Process").list();
				for (ProcessOperation operation : operations) {
					try {
						schedulerService.scheduleJob(operation, mapProcStatus.get(operation.getLinkName()));
						System.out.println("✅ Scheduled job: " + operation.getOperationName() + " ["
								+ operation.getCronExpression() + "]");
					} catch (Exception e) {
						System.err.println(
								"❌ Failed to schedule job " + operation.getOperationName() + ": " + e.getMessage());
					}
				}
				schedulerService.listAllJobs();
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