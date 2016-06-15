package com.cn.ctbri.southapi.adapter.servlet;

import java.net.MalformedURLException;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.quartz.SchedulerException;

import com.cn.ctbri.southapi.adapter.manager.DeviceAdpaterManager;
import com.cn.ctbri.southapi.adapter.manager.QuartzTest;
import com.cn.ctbri.southapi.adapter.waf.config.WAFConfigManager;
import com.cn.ctbri.southapi.adapter.waf.syslog.WAFSyslogManager;

public class InitServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String rootPath = null;
	public void init() throws ServletException { 
		

	
		DeviceAdpaterManager deviceAdpaterManager = new DeviceAdpaterManager();
		deviceAdpaterManager.loadDeviceAdpater();
		try {
			QuartzTest.runJob();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
