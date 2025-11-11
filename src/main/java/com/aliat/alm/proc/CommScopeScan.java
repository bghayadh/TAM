package com.aliat.alm.proc;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class CommScopeScan implements Job, ExecutableOperation {
    @Override
    public void execute(JobExecutionContext context) {
        // This is called automatically by Quartz
        execute("auto");  // call the same logic, maybe with different params
    }

    @Override
    public void execute(Object... params) {
        // This is for manual or dynamic execution
        String processName = (String) params[0];
        System.out.println("Running Process Name: " + processName + " with cron expression: " + (String) params[1]);
        performSync();
    }

    private void performSync() {
        System.out.println("Performing sync logic...");
        // ... telecom network sync code here
    }
}
