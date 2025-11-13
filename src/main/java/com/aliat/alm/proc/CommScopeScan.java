package com.aliat.alm.proc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

@Service("com.aliat.alm.proc.CommScopeScan")
public class CommScopeScan implements Job, ExecutableOperation {

	private List<Object[]> cntrls_login = new ArrayList<Object[]>();
	private Map<String, Object> cntrl_info = new LinkedHashMap<>();
	private static ObjectMapper mapper = new ObjectMapper();

	private static final Logger logger = LoggerFactory.getLogger(CommScopeScan.class);

	@Autowired
	CommScopeService commscopeService;

	@Override
	public void execute(JobExecutionContext context) {
		// This is called automatically by Quartz
		execute("auto"); // call the same logic, maybe with different params
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Object... params) {

		Session session = AlmDbSession.getInstance().getSession();
		if (session != null && session.isOpen()) {
			Transaction tx = session.beginTransaction();
			try {
				cntrls_login = session.createNativeQuery("select ip_address, user_name, password from controller")
						.list();
				System.out.println("controllers_IP size is " + cntrls_login.size());
				System.out.println("The controllers_IP is " + mapper.writeValueAsString(cntrls_login));
				for (Object[] cntrl_login : cntrls_login) {
					cntrl_info = commscopeService.loginAPI((String) cntrl_login[0], (String) cntrl_login[1],
							(String) cntrl_login[2], 900);
					System.out
							.println("Obtained Information for the panel is " + mapper.writeValueAsString(cntrl_info));
				}

				// This is for manual or dynamic execution
				String processName = (String) params[0];
				System.out.println(
						"Running Process Name: " + processName + " with cron expression: " + (String) params[1]);
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
