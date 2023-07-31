package com.aliat.alm.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("scheduling")
public class ScheduledTasks {
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	public void taskScheduling() throws InterruptedException {
		try {
			// write your method for XML Parsing.....
			
			System.out.println("Auto Importer XML Parsing Task Method Execute");
			log.info("Start AutoImporter Xml Parsing Task secheduling Time---------->"+ dateFormat.format(new Date()));
		} catch (Exception e) {
			e.getMessage();
			log.info("Exception throw for AutoImporter Xml Parsing Task secheduling---------->"+ dateFormat.format(new Date()));
		}

	}

}
