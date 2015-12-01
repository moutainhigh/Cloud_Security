package com.cn.ctbri.logger;

import org.apache.log4j.Logger;



/*
 * Description : Cloud Security Platform Logger
 * Author: niujf
 * Date : 2015/11/18
 * Copyright: CTBRI
 */
public class CSPLoggerAuditorAd implements ICSPLogger {

	protected Logger logger = Logger.getLogger("AUDITOR");
	
	public boolean initLogger() {

		return false;
	}

	public void debug(Object message) {
		logger.debug(message);

	}

	public void debug(Object message, Throwable t) {
		logger.debug(message, t);

	}

	public void error(Object message) {
		logger.error(message);

	}

	public void error(Object message, Throwable t) {
		logger.error(message,t);

	}

	public void fatal(Object message) {
		logger.fatal(message);
	}

	public void fatal(Object message, Throwable t) {
		logger.fatal(message, t);
	}

	public void info(Object message) {
		logger.info(message);

	}

	public void info(Object message, Throwable t) {
		logger.info(message, t);

	}

	public void warn(Object message) {
		logger.warn(message);

	}

	public void warn(Object message, Throwable t) {
		logger.warn(message, t);

	}

	public void trace(Object message) {
		logger.trace(message);
	}

	public void trace(Object message, Throwable t) {
		logger.trace(message, t);
	}

}
