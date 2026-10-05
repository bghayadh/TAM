package com.aliat.alm.scheduler;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.aliat.alm.models.ExportEntry;
import com.aliat.alm.gdb.GdbExportRegistry;
import com.aliat.alm.gdb.GdbFileUtils;

public class GdbExportCleanupJob implements Job {

    private static final Logger logger = Logger.getLogger(GdbExportCleanupJob.class.getName());
    private static final long MAX_AGE_MILLIS = 30 * 60 * 1000; // 30 minutes

    @Autowired
    private GdbExportRegistry gdbExportRegistry;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        long now = System.currentTimeMillis();

        gdbExportRegistry.getAllEntries().forEach((token, entry) -> {
            if ((now - entry.getCreatedAt()) > MAX_AGE_MILLIS) {
                try {
                    File file = new File(entry.getFilePath());
                    GdbFileUtils.deleteRecursively(file.getParentFile());
                    gdbExportRegistry.removeExport(token);
                    logger.log(Level.INFO, "Swept expired GDB export: " + token);
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Failed to clean up expired export " + token, e);
                }
            }
        });
    }
}