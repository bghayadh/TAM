package com.aliat.alm.proc;

import javax.annotation.Resource;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;
import com.aliat.alm.models.ProcessOperation;

@Service
public class SchedulerService {

	@Resource(name = "quartzScheduler")
	private Scheduler scheduler;

	@SuppressWarnings("unchecked")
	public void scheduleJob(ProcessOperation operation) throws Exception {
		Class<? extends Job> operationClass = (Class<? extends Job>) Class.forName(operation.getClassName());

		JobDetail jobDetail = JobBuilder.newJob(operationClass).withIdentity(operation.getOperationName(), "TELECOM")
				.build();

		CronTrigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(operation.getOperationName() + "Trigger", "TELECOM")
				.withSchedule(CronScheduleBuilder.cronSchedule(operation.getCronExpression())).build();

		if (scheduler.checkExists(jobDetail.getKey())) {
			scheduler.deleteJob(jobDetail.getKey());
		}

		scheduler.scheduleJob(jobDetail, trigger);
		scheduler.start();
	}

	public void deleteJob(String name) throws Exception {
		scheduler.deleteJob(new JobKey(name, "TELECOM"));
	}
}