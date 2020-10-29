package com.success.programs;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4JTest {
	private static Logger logger = Logger.getLogger(Log4JTest.class);
//	private static final String LOG_PROPERTIES_FILE = "/opt/tomcat/conf/log4j.properties";
	private static final String LOG_PROPERTIES_FILE = "C:/Tamil/GIT_Repository/bankruptcy/system/opt/tomcat8/conf/log4j.properties";
	
//	private static final String LOG_PROPERTIES_FILE = "/opt/tomcat/conf/log4j.properties";
	static {
		Properties logProperties = new Properties();
		try {
			// load our log4j properties / configuration file
			logProperties.load(new FileInputStream(LOG_PROPERTIES_FILE));
			PropertyConfigurator.configure(logProperties);
		} catch (IOException e) {
			logger.error("Unable to load logging property " + LOG_PROPERTIES_FILE);
		}
	}

	public static void main(String[] args) {
		logger.info("Testing");
	}

}
