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
	private String str = "";
	private static ObjectMapper mapper = new ObjectMapper();

	private static final Logger logger = LoggerFactory.getLogger(CommScopeScan.class);

	@Autowired
	CommScopeService commscopeService;
	
	@Autowired
	SnglCmCntrl snglCmCntrl;

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
				cntrls_login = session
						.createNativeQuery(
								"select controller_id, ip_address, user_name, password, serial_numb from controller")
						.list();
				for (Object[] cntrl_login : cntrls_login) {
					snglCmCntrl.login((String) cntrl_login[0], (String) cntrl_login[1], (String) cntrl_login[2],
							(String) cntrl_login[3], 900, (String) cntrl_login[4], session);
				}
				session.flush();
				str = "update distribution_board_mapping a set a.fp_equipment_type = 'Distribution Board', a.fp_equipment_id = "
					+"(select distinct db_id from panel_kit b where b.kit_serial_num = a.far_near_kit_serial_num), "
					+"a.fp_equipment_name = (select db_name from distribution_board where db_id = "
					+ "(select distinct db_id from panel_kit b where b.kit_serial_num = a.far_near_kit_serial_num))";
				
				session.createNativeQuery(str).executeUpdate();

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
