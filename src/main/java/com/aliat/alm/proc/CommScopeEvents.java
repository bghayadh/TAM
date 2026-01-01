package com.aliat.alm.proc;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliat.alm.common.AlmDbSession;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("com.aliat.alm.proc.CommScopeEvents")
public class CommScopeEvents implements Job, ExecutableOperation {

	private List<Object[]> cntrls_login = new ArrayList<Object[]>();
	private String str = "";
	private static ObjectMapper mapper = new ObjectMapper();

	private static final Logger logger = LoggerFactory.getLogger(CommScopeEvents.class);

	@Autowired
	CommScopeService commscopeService;

	@Autowired
	SnglCmCntrlEvent snglCmCntrlEvent;

	@Override
	public void execute(JobExecutionContext context) {
		// This is called automatically by Quartz
		execute("auto"); // call the same logic, maybe with different params
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Object... params) {
		System.out.println("Welcome to CommScopeEvents");
		Object[] latestEvent = null;
		Session session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			Transaction tx = session.beginTransaction();
			try {
				cntrls_login = session
						.createNativeQuery(
								"select controller_id, ip_address, user_name, password, serial_numb from controller")
						.list();
				for (Object[] cntrl_login : cntrls_login) {
					str = "SELECT event_id, event_timestamp FROM (SELECT event_id, event_timestamp,"
							+ " ROW_NUMBER() OVER (PARTITION BY controller_id ORDER BY event_timestamp DESC) AS rn"
							+ " FROM ipatch_event WHERE controller_id = :cntrl_Id) WHERE rn = 1";
					System.out.println("(String) cntrl_login[4] is " + (String) cntrl_login[4]);
					List<Object[]> result = (List<Object[]>) session.createNativeQuery(str)
							.setParameter("cntrl_Id", (String) cntrl_login[4]).getResultList();

					if (!result.isEmpty()) {
						latestEvent = result.get(0);
					}
					snglCmCntrlEvent.login((String) cntrl_login[0], (String) cntrl_login[1], (String) cntrl_login[2],
							(String) cntrl_login[3], 900, (String) cntrl_login[4], latestEvent, session);
				}
				session.flush();

				// This is for manual or dynamic execution
				String processName = null;
				String cronExpr = null;

				if (params != null) {
					if (params.length > 0) {
						processName = String.valueOf(params[0]);
					}
					if (params.length > 1) {
						cronExpr = String.valueOf(params[1]);
					}
				}

				System.out.println("Running Process Name: " + processName
						+ (cronExpr != null ? " with cron expression: " + cronExpr : ""));

				performSync();
			} catch (Exception e) {
				logger.info(
						"Error in getting ip addresses, username and password of controller commscope with a message : "
								+ e + "\n\" + e.getMessage()",
						e);
			} finally {
				if (session != null && session.isOpen()) {
					tx.commit();
					session.close();
				}
			}
		}
	}

	private void performSync() {
		System.out.println("Performing sync logic...");
		// ... telecom network sync code here
	}
}