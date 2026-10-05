package com.aliat.alm.config;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.aliat.alm.scheduler.GdbExportCleanupJob;

@Configuration
public class QuartzConfig {

    private final AutowiringSpringBeanJobFactory jobFactory;

    public QuartzConfig(AutowiringSpringBeanJobFactory jobFactory) {
        this.jobFactory = jobFactory;
    }

    @Bean
    public JobDetailFactoryBean gdbExportCleanupJobDetail() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(GdbExportCleanupJob.class);
        factory.setDurability(true); // job survives even with no trigger attached yet
        factory.setName("gdbExportCleanupJob");
        return factory;
    }

    @Bean
    public SimpleTriggerFactoryBean gdbExportCleanupTrigger(JobDetail gdbExportCleanupJobDetail) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(gdbExportCleanupJobDetail);
        trigger.setRepeatInterval(10 * 60 * 1000); // every 10 minutes
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        trigger.setName("gdbExportCleanupTrigger");
        return trigger;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(
            JobDetail gdbExportCleanupJobDetail,
            org.quartz.Trigger gdbExportCleanupTrigger) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);
        factory.setJobDetails(gdbExportCleanupJobDetail);
        factory.setTriggers(gdbExportCleanupTrigger);
        return factory;
    }
}