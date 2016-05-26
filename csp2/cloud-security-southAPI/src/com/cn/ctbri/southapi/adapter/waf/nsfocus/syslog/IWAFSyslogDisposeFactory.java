package com.cn.ctbri.southapi.adapter.waf.nsfocus.syslog;


/*
 * Description : 
 * Author: niujf
 * Date : 2016/05/10
 * Copyright: CTBRI
 */
public interface IWAFSyslogDisposeFactory {
	boolean disposeSyslogEvent(String message,WAFSyslogHostParam wafSyslogHostParam,WAFSyslogDBHelper wafSyslogDBHelper); 
}
