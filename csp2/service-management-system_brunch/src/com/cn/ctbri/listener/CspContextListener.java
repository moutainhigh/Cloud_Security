package com.cn.ctbri.listener;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Properties;

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
	public static HashMap<String,String> serviceCodeAndMessage = new  HashMap<String,String>();
	
	
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	public void contextInitialized(ServletContextEvent sce) {
		//启动华为设置UDP监听接口
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
//		UdpServerSocket server = context.getBean("serverNetty", UdpServerSocket.class);
//		server.start();
		putServiceCodeAndMessageMap();
		   
	}
	private void putServiceCodeAndMessageMap(){
		Properties p = new Properties();
		try {
			
			p.load(new InputStreamReader(CspContextListener.class.getClassLoader().getResourceAsStream("codeAndMessage.properties"), "UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serviceCodeAndMessage.put("successCode", p.getProperty("successCode"));
		serviceCodeAndMessage.put("successMessage", p.getProperty("successMessage"));
		serviceCodeAndMessage.put("failCode", p.getProperty("failCode"));
		serviceCodeAndMessage.put("failMessage", p.getProperty("failMessage"));
		serviceCodeAndMessage.put("paramErrCode", p.getProperty("paramErrCode"));
		serviceCodeAndMessage.put("paramErrMessage", p.getProperty("paramErrMessage"));
		serviceCodeAndMessage.put("tokenInvalidCode", p.getProperty("tokenInvalidCode"));
		serviceCodeAndMessage.put("tokenInvalidMessage", p.getProperty("tokenInvalidMessage"));
		serviceCodeAndMessage.put("permissionDeniedCode", p.getProperty("permissionDeniedCode"));
		serviceCodeAndMessage.put("permissionDeniedMessage", p.getProperty("permissionDeniedMessage"));
		serviceCodeAndMessage.put("expiredCode", p.getProperty("expiredCode"));
		serviceCodeAndMessage.put("expiredMessage", p.getProperty("expiredMessage"));
		serviceCodeAndMessage.put("usedUpCode", p.getProperty("usedUpCode"));
		serviceCodeAndMessage.put("usedUpMessage", p.getProperty("usedUpMessage"));
	}
}
