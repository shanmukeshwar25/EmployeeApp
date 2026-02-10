package com.employees.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmployeeLogger {
	private static final Logger log =
	        LoggerFactory.getLogger(EmployeeLogger.class);

	public static Logger getLog() {
		return log;
	}

}
