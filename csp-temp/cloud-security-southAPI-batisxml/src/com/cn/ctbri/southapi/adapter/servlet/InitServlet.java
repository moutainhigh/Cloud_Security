package com.cn.ctbri.southapi.adapter.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.quartz.SchedulerException;

import com.cn.ctbri.southapi.adapter.manager.DeviceAdpaterManager;
import com.cn.ctbri.southapi.adapter.manager.QuartzManager;

public class InitServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String rootPath = null;
	public void init() throws ServletException { 
		
	
		DeviceAdpaterManager deviceAdpaterManager = new DeviceAdpaterManager();
		deviceAdpaterManager.loadDeviceAdpater();
		/*try {
			QuartzManager.runJob();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
