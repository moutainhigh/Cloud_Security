package com.cn.ctbri.southapi.adpater.manager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitServlet extends HttpServlet {
	public void init() throws ServletException { 
		DeviceAdpaterManager deviceAdpaterManager = new DeviceAdpaterManager();
		System.out.println(deviceAdpaterManager.loadDeviceAdpater());
	}
}
