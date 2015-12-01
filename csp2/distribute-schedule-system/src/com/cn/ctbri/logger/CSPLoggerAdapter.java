package com.cn.ctbri.logger;
import org.apache.log4j.PropertyConfigurator;


/*
 * Description : Cloud Security Platform Logger
 * Author: niujf
 * Date : 2015/11/18
 * Copyright: CTBRI
 */
public class CSPLoggerAdapter {

	protected static CSPLoggerDebuggerAd   debugLoggerAd = new CSPLoggerDebuggerAd();
	protected static CSPLoggerLoggerAd       logLoggerAd = new CSPLoggerLoggerAd();
	protected static CSPLoggerMonitorAd  monitorLoggerAd = new CSPLoggerMonitorAd();
	protected static CSPLoggerAuditorAd    auditLoggerAd = new CSPLoggerAuditorAd();
	
	
	/*
	 * Notice : 如果配置了数据库相关Appender，需保障优先加载相应的数据库连接JAR包
	 */
	static {
		try {
			// From Jar package position
			String file = System.getProperty("user.dir") + "/conf/"
					+ CSPLoggerConstant.FILE_LOGGER_CONFIG;
			if (new java.io.File(file).exists()) {
				PropertyConfigurator.configure(file);
			}
		} catch (Exception e) {

		}
		
		debugLoggerAd.initLogger();
		logLoggerAd.initLogger();
		monitorLoggerAd.initLogger();
		auditLoggerAd.initLogger();

	}
	
	/*
	 * Params : Type Set, Log level , Message
	 * Multiple Adapter Type : Use '|' Split
	 */
	public static void log(String adTypes, int level, Object message) {
		if ( adTypes == null || "".equals(adTypes)) return ;
		
		String[] adTypesSet = adTypes.split(CSPLoggerConstant.SPLIT_SYMBOL_ADAPTER_TYPE);
		
		for( int i=0; i<adTypesSet.length; i++ ) {
			int nAdType = 0;
			
			try {
				nAdType = Integer.parseInt(adTypesSet[i]);
			} catch (Exception e) {				
			}
			
			CSPLoggerAdapter.log(nAdType,level,message);
		}
	}
	
	/*
	 * Params : Type Set, Log level , Message
	 * Multiple Adapter Type : Use '|' Split
	 */
	public static void log(String adTypes, int level, Object message, Throwable t) {
		if ( adTypes == null || "".equals(adTypes)) return ;
		
		String[] adTypesSet = adTypes.split(CSPLoggerConstant.SPLIT_SYMBOL_ADAPTER_TYPE);
		
		for( int i=0; i<adTypesSet.length; i++ ) {
			int nAdType = 0;
			
			try {
				nAdType = Integer.parseInt(adTypesSet[i]);
			} catch (Exception e) {				
			}
			
			CSPLoggerAdapter.log(nAdType,level,message,t);
		}
	}

	
	/*
	 * Params : Type Set, Log level , Message
	 * Multiple Adapter Type : Use '|' Split
	 */
	public static void log(int adType, int level, Object message) {
		switch( adType ) {
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER:
			CSPLoggerAdapter.logForDebugger(level,message);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER:
			CSPLoggerAdapter.logForLogger(level, message);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_AUDITOR:
			CSPLoggerAdapter.logForAuditor(level, message);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_MONITOR:
			CSPLoggerAdapter.logForMonitor(level, message);
			break;
		default:
			break;
		}

	}
	
	/*
	 * Params : Type Set, Log level , Message
	 * Multiple Adapter Type : Use '|' Split
	 */
	public static void log(int adType, int level, Object message, Throwable t) {
		switch( adType ) {
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER:
			CSPLoggerAdapter.logForDebugger(level,message,t);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER:
			CSPLoggerAdapter.logForLogger(level, message,t);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_AUDITOR:
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_MONITOR:
			break;
		default:
			break;
		}

	}
	
	/*
	 * For Debugger
	 */
	public static void logForDebugger(int level, Object message) {
		switch( level ) {
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_DEBUG:
			CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER,message);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_ERROR:
			CSPLoggerAdapter.error(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER,message);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_FATAL:
			CSPLoggerAdapter.fatal(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER,message);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_INFO:
			CSPLoggerAdapter.info(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER,message);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_WARN:
			CSPLoggerAdapter.warn(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER,message);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_TRACE:
			CSPLoggerAdapter.trace(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER,message);
			break;
		default:
			break;
		}
		
	}
	
	/*
	 * For Debugger
	 */
	public static void logForDebugger(int level, Object message, Throwable t) {
		switch( level ) {
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_DEBUG:
			CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER,message,t);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_ERROR:
			CSPLoggerAdapter.error(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER,message,t);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_FATAL:
			CSPLoggerAdapter.fatal(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER,message,t);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_INFO:
			CSPLoggerAdapter.info(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER,message,t);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_WARN:
			CSPLoggerAdapter.warn(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER,message,t);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_TRACE:
			CSPLoggerAdapter.trace(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER,message,t);
			break;
		default:
			break;
		}
		
	}	
	
	/*
	 * For Logger
	 */
	public static void logForLogger(int level, Object message) {
		switch( level ) {
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_DEBUG:
			CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER,message);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_ERROR:
			CSPLoggerAdapter.error(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER,message);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_FATAL:
			CSPLoggerAdapter.fatal(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER,message);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_INFO:
			CSPLoggerAdapter.info(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER,message);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_WARN:
			CSPLoggerAdapter.warn(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER,message);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_TRACE:
			CSPLoggerAdapter.trace(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER,message);
			break;
		default:
			break;
		}
		
	}
	
	
	/*
	 * For Logger
	 */
	public static void logForLogger(int level, Object message, Throwable t) {
		switch( level ) {
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_DEBUG:
			CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER,message,t);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_ERROR:
			CSPLoggerAdapter.error(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER,message,t);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_FATAL:
			CSPLoggerAdapter.fatal(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER,message,t);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_INFO:
			CSPLoggerAdapter.info(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER,message,t);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_WARN:
			CSPLoggerAdapter.warn(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER,message,t);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_TRACE:
			CSPLoggerAdapter.trace(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER,message,t);
			break;
		default:
			break;
		}
		
	}
	
	/*
	 * For Auditor
	 */
	public static void logForAuditor(int level, Object message) {
		switch( level ) {
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_DEBUG:
			CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_AUDITOR,message);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_ERROR:
			CSPLoggerAdapter.error(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_AUDITOR,message);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_FATAL:
			CSPLoggerAdapter.fatal(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_AUDITOR,message);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_INFO:
			CSPLoggerAdapter.info(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_AUDITOR,message);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_WARN:
			CSPLoggerAdapter.warn(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_AUDITOR,message);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_TRACE:
			CSPLoggerAdapter.trace(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_AUDITOR,message);
			break;
		default:
			break;
		}
		
	}
	
	
	/*
	 * For Monitor
	 */
	public static void logForMonitor(int level, Object message) {
		switch( level ) {
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_DEBUG:
			CSPLoggerAdapter.debug(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_MONITOR,message);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_ERROR:
			CSPLoggerAdapter.error(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_MONITOR,message);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_FATAL:
			CSPLoggerAdapter.fatal(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_MONITOR,message);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_INFO:
			CSPLoggerAdapter.info(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_MONITOR,message);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_WARN:
			CSPLoggerAdapter.warn(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_MONITOR,message);
			break;
		case CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_TRACE:
			CSPLoggerAdapter.trace(CSPLoggerConstant.TYPE_LOGGER_ADAPTER_MONITOR,message);
			break;
		default:
			break;
		}
		
	}
	
	/*
	 * Adapter: DEBUG
	 * Valid Level : DEBUG
	 */
	public static void debug(int adType,Object message) {
		switch( adType ) {
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER:
			debugLoggerAd.debug(message);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER:
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_AUDITOR:
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_MONITOR:
			break;
		default:
			break;
		}
		
	}
	
	/*
	 * Adapter: DEBUG
	 * Valid Level : DEBUG
	 */
	public static void debug(int adType,Object message, Throwable t) {
		switch( adType ) {
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER:
			debugLoggerAd.debug(message,t);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER:
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_AUDITOR:
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_MONITOR:
			break;
		default:
			break;
		}

	}
	
	/*
	 * Adapter: DEBUG, LOG, AUDIT, MONITOR 
	 * Default Adapter : LOG
	 * Valid Level : ERROR
	 */
	public static void error(int adType,Object message) {
		switch( adType ) {
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER:
			debugLoggerAd.error(message);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER:
			logLoggerAd.error(message);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_AUDITOR:
			auditLoggerAd.error(message);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_MONITOR:
			monitorLoggerAd.error(message);
			break;
		default:
			logLoggerAd.error(message);
			break;
		}

	}
	
	/*
	 * Adapter: DEBUG, LOG 
	 * Default Adapter : DEBUG
	 * Valid Level : ERROR
	 */
	public static void error(int adType,Object message, Throwable t) {
		switch( adType ) {
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER:
			debugLoggerAd.error(message,t);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER:
			logLoggerAd.error(message,t);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_AUDITOR:
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_MONITOR:
			break;
		default:
			debugLoggerAd.error(message);
			break;
		}
	}

	
	/*
	 * Adapter: DEBUG, LOG, AUDIT, MONITOR 
	 * Default Adapter : LOG
	 * Valid Level : FATAL
	 */
	public static void fatal(int adType,Object message) {
		switch( adType ) {
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER:
			debugLoggerAd.error(message);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER:
			logLoggerAd.error(message);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_AUDITOR:
			auditLoggerAd.error(message);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_MONITOR:
			monitorLoggerAd.error(message);
			break;
		default:
			logLoggerAd.error(message);
			break;
		}

	}
	
	/*
	 * Adapter: DEBUG, LOG 
	 * Default Adapter : DEBUG
	 * Valid Level : FATAL
	 */
	public static void fatal(int adType,Object message, Throwable t) {
		switch( adType ) {
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER:
			debugLoggerAd.error(message,t);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER:
			logLoggerAd.error(message,t);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_AUDITOR:
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_MONITOR:
			break;
		default:
			debugLoggerAd.error(message);
			break;
		}
	}
	
	
	
	/*
	 * Adapter: DEBUG, LOG, AUDIT, MONITOR 
	 * Default Adapter : LOG
	 * Valid Level : INFO
	 */
	public static void info(int adType,Object message) {
		switch( adType ) {
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER:
			debugLoggerAd.error(message);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER:
			logLoggerAd.error(message);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_AUDITOR:
			auditLoggerAd.error(message);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_MONITOR:
			monitorLoggerAd.error(message);
			break;
		default:
			logLoggerAd.error(message);
			break;
		}

	}
	
	/*
	 * Adapter: DEBUG, LOG 
	 * Default Adapter : DEBUG
	 * Valid Level : INFO
	 */
	public static void info(int adType,Object message, Throwable t) {
		switch( adType ) {
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER:
			debugLoggerAd.error(message,t);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER:
			logLoggerAd.error(message,t);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_AUDITOR:
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_MONITOR:
			break;
		default:
			debugLoggerAd.error(message);
			break;
		}
	}
	
	
	/*
	 * Adapter: DEBUG, LOG, AUDIT, MONITOR 
	 * Default Adapter : LOG
	 * Valid Level : WARN
	 */
	public static void warn(int adType,Object message) {
		switch( adType ) {
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER:
			debugLoggerAd.error(message);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER:
			logLoggerAd.error(message);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_AUDITOR:
			auditLoggerAd.error(message);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_MONITOR:
			monitorLoggerAd.error(message);
			break;
		default:
			logLoggerAd.error(message);
			break;
		}

	}
	
	/*
	 * Adapter: DEBUG, LOG 
	 * Default Adapter : DEBUG
	 * Valid Level : WARN
	 */
	public static void warn(int adType,Object message, Throwable t) {
		switch( adType ) {
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER:
			debugLoggerAd.error(message,t);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER:
			logLoggerAd.error(message,t);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_AUDITOR:
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_MONITOR:
			break;
		default:
			debugLoggerAd.error(message);
			break;
		}
	}
	
	
	/*
	 * Adapter: DEBUG, LOG, MONITOR 
	 * Default Adapter : LOG
	 * Valid Level : TRACE
	 */
	public static void trace(int adType,Object message) {
		switch( adType ) {
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER:
			debugLoggerAd.error(message);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER:
			logLoggerAd.error(message);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_AUDITOR:
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_MONITOR:
			monitorLoggerAd.error(message);
			break;
		default:
			logLoggerAd.error(message);
			break;
		}

	}
	
	/*
	 * Adapter: DEBUG, LOG 
	 * Default Adapter : DEBUG
	 * Valid Level : TRACE
	 */
	public static void trace(int adType,Object message, Throwable t) {
		switch( adType ) {
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER:
			debugLoggerAd.error(message,t);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER:
			logLoggerAd.error(message,t);
			break;
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_AUDITOR:
		case CSPLoggerConstant.TYPE_LOGGER_ADAPTER_MONITOR:
			break;
		default:
			debugLoggerAd.error(message);
			break;
		}
	}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String adTypes = CSPLoggerConstant.TYPE_LOGGER_ADAPTER_DEBUGGER + "|" +
		                 CSPLoggerConstant.TYPE_LOGGER_ADAPTER_LOGGER + "|" +
				         CSPLoggerConstant.TYPE_LOGGER_ADAPTER_AUDITOR + "|" +
				         CSPLoggerConstant.TYPE_LOGGER_ADAPTER_MONITOR;
		
		CSPLoggerAdapter.log(adTypes, CSPLoggerConstant.LEVEL_LOGGER_ADAPTER_ERROR, "This is Test !!!!!!!!!!!!");
	}

}
