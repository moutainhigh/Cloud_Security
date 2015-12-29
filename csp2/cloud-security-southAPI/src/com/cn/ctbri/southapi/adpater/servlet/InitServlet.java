package com.cn.ctbri.southapi.adpater.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.cn.ctbri.southapi.adpater.manager.DeviceAdpaterManager;

public class InitServlet extends HttpServlet {
	public void init() throws ServletException { 
		DeviceAdpaterManager deviceAdpaterManager = new DeviceAdpaterManager();
		System.out.println(deviceAdpaterManager.loadDeviceAdpater());
	}
}
