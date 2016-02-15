package com.cn.ctbri.logger;

/*
 * Description : Cloud Security Platform Logger
 * Author: niujf
 * Date : 2015/11/18
 * Copyright: CTBRI
 */
public interface ICSPLogger {
	public boolean initLogger();
	
	public void debug(Object message);
	public void debug(Object message, Throwable t);
	
	public void error(Object message);
	public void error(Object message, Throwable t);
	
	public void fatal(Object message);
	public void fatal(Object message, Throwable t);
	
	public void info(Object message);
	public void info(Object message, Throwable t);
	
	public void warn(Object message);
	public void warn(Object message, Throwable t);
	
	public void trace(Object message);
	public void trace(Object message, Throwable t);
}
