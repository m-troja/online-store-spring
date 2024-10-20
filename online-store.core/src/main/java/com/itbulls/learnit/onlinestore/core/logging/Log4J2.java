package com.itbulls.learnit.onlinestore.core.logging;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Log4J2 {
	
	private static final Logger LOGGER = LogManager.getLogger(Log4J2.class);

	public static void main(String[] args) {
		LOGGER.debug("Debug log message");
		LOGGER.info("Info log message");
		LOGGER.error("Error log message");
	}
}
