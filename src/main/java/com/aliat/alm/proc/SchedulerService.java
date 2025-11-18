package com.aliat.alm.proc;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Service;

import com.aliat.alm.models.ProcessOperation;
import com.aliat.alm.utils.CronExpressionAdapter;

@Service
public class SchedulerService {

	private static final String JOB_GROUP = "TELECOM";

	@Resource(name = "quartzScheduler")
	private Scheduler scheduler;

	@SuppressWarnings("unchecked")
	public void scheduleJob(ProcessOperation operation, String procStatus) throws Exception {

		String operationId = operation.getID();
		String operationName = operation.getOperationName();

		System.out.println("--------------------------------------------------");
		System.out.println("Scheduling operation: " + operationName + " [ID=" + operationId + "]");

		// Resolve job and trigger keys
		JobKey jobKey = new JobKey(operationId, JOB_GROUP);
		TriggerKey triggerKey = new TriggerKey(operationId + "Trigger", JOB_GROUP);

		// Get the operation class
		Class<? extends Job> operationClass = (Class<? extends Job>) Class.forName(operation.getClassName());

		System.out.println("Operation class: " + operationClass.getName());
		System.out.println("Cron expression (client): " + operation.getCronExpression());

		// Convert cron to Quartz format
		String cronExpr = CronExpressionAdapter.toQuartzFormat(operation.getCronExpression());
		System.out.println("Converted cron expression (Quartz): " + cronExpr);

		// If job exists, remove it first (to reschedule cleanly)
		if (scheduler.checkExists(jobKey)) {
			System.out.println("Existing job found, deleting before rescheduling: " + jobKey);
			scheduler.deleteJob(jobKey);
		}

		// Only schedule if both operation and process are enabled
		boolean enabled = StringUtils.equalsIgnoreCase(operation.getStatus(), "Enabled")
				&& StringUtils.equalsIgnoreCase(procStatus, "Enabled");

		if (enabled) {
			JobDetail jobDetail = JobBuilder.newJob(operationClass).withIdentity(jobKey).build();

			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
					.withSchedule(CronScheduleBuilder.cronSchedule(cronExpr)).build();

			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();

			System.out.println("✅ Scheduled job: " + jobKey + " with cron: " + cronExpr);
		} else {
			// Delete job if exists but operation is disabled
			if (scheduler.checkExists(jobKey)) {
				scheduler.deleteJob(jobKey);
				System.out.println("🟡 Job disabled and removed: " + jobKey);
			} else {
				System.out.println("🟡 Job disabled, nothing to schedule or remove: " + jobKey);
			}
		}

		System.out.println("--------------------------------------------------");
	}

	public void deleteJob(String opID) throws Exception {
		JobKey jobKey = new JobKey(opID, JOB_GROUP);
		if (scheduler.checkExists(jobKey)) {
			scheduler.deleteJob(jobKey);
			System.out.println("🗑️ Deleted job with ID: " + opID);
		} else {
			System.out.println("⚠️ No job found to delete with ID: " + opID);
		}
	}

	public void deleteJobs(List<String> opIDs) {
		for (String opID : opIDs) {
			try {
				deleteJob(opID);
			} catch (Exception e) {
				System.out.println("⚠️ Could not delete job: " + opID + " - " + e.getMessage());
			}
		}
	}

	// List all the operations, this is for debugging
	public void listAllJobs() throws Exception {
		System.out.println("===== Listing all scheduled jobs =====");

		for (String groupName : scheduler.getJobGroupNames()) {
			for (JobKey jobKey : scheduler
					.getJobKeys(org.quartz.impl.matchers.GroupMatcher.jobGroupEquals(groupName))) {

				JobDetail jobDetail = scheduler.getJobDetail(jobKey);
				if (jobDetail == null)
					continue;

				// Job class that will execute
				String jobClassName = jobDetail.getJobClass().getName();

				// Get all triggers for this job
				for (org.quartz.Trigger trigger : scheduler.getTriggersOfJob(jobKey)) {
					String triggerType = trigger.getClass().getSimpleName();

					// Only print cron expression for CronTriggers
					String cronExpr = "";
					if (trigger instanceof CronTrigger) {
						cronExpr = ((CronTrigger) trigger).getCronExpression();
					}

					System.out.println("--------------------------------------------------");
					System.out.println("Job ID:      " + jobKey.getName());
					System.out.println("Group:       " + jobKey.getGroup());
					System.out.println("Class:       " + jobClassName);
					System.out.println("Trigger:     " + trigger.getKey().getName() + " (" + triggerType + ")");
					System.out.println("Cron:        " + (cronExpr.isEmpty() ? "N/A" : cronExpr));
					System.out.println("Next Fire:   " + trigger.getNextFireTime());
					System.out.println("Previous:    " + trigger.getPreviousFireTime());
				}
			}
		}

		System.out.println("======================================");
	}
}