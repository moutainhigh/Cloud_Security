package com.cn.ctbri.southapi.adpater.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.quartz.SchedulerException;

import com.cn.ctbri.southapi.adpater.manager.DeviceAdpaterManager;
import com.cn.ctbri.southapi.adpater.manager.QuartzTest;

public class InitServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
