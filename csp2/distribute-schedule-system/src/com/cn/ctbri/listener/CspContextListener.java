package com.cn.ctbri.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cn.ctbri.nio.UdpServerSocket;
/**
 * 描     述： 服务器监听器
 * 創建人: 于永波
 * 日    期: 2015-3-16
 */
public class CspContextListener implements ServletContextListener{

	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	public void contextInitialized(ServletContextEvent sce) {
		//启动华为设置UDP监听接口
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
//		UdpServerSocket server = context.getBean("serverNetty", UdpServerSocket.class);
//		server.start();
		
	}

}
