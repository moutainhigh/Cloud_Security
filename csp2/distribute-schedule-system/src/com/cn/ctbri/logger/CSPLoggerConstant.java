package com.cn.ctbri.logger;


/*
 * Description : Cloud Security Platform Logger Constant Define
 * Author: niujf
 * Date : 2015/11/18
 * Copyright: CTBRI
 */
public class CSPLoggerConstant {

	public static final String PATH_LOGGER_CONFIG = "../";
	public static final String FILE_LOGGER_CONFIG = "log4j.properties";
	public static final String SPLIT_SYMBOL_ADAPTER_TYPE =  "|";
	/*
	 * Logger Type
	 */
	public static final int TYPE_LOGGER_ADAPTER_DEBUGGER   = 0x01;
	public static final int TYPE_LOGGER_ADAPTER_LOGGER     = 0x02;
	public static final int TYPE_LOGGER_ADAPTER_AUDITOR   = 0x04;
	public static final int TYPE_LOGGER_ADAPTER_MONITOR = 0x08;
	
	/*
	 * Logger Level
	 */
	public static final int LEVEL_LOGGER_ADAPTER_DEBUG = 0x01;
	public static final int LEVEL_LOGGER_ADAPTER_ERROR = 0x02;
	public static final int LEVEL_LOGGER_ADAPTER_FATAL = 0x04;
	public static final int LEVEL_LOGGER_ADAPTER_INFO  = 0x08;
	public static final int LEVEL_LOGGER_ADAPTER_WARN  = 0x0F;
	public static final int LEVEL_LOGGER_ADAPTER_TRACE = 0x10;

}
