package com.cn.ctbri.listener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * 描     述： 服务器监听器
 * 創建人: 于永波
 * 日    期: 2015-3-16
 */
public class CspContextListener implements ServletContextListener{
	public static HashMap<String,String> allPropertisMap = new  HashMap<String,String>();
	
	
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	public void contextInitialized(ServletContextEvent sce) {
		//启动华为设置UDP监听接口
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
//		UdpServerSocket server = context.getBean("serverNetty", UdpServerSocket.class);
//		server.start();
		putMapByPropName("codeAndMessage");
		putMapByPropName("southAPI");
		   
	}
	private void putMapByPropName(String propName){
		Properties pros = new Properties();
		InputStream stream = CspContextListener.class.getClassLoader().getResourceAsStream(propName + ".properties");
		Reader reader = null;
		try {
			reader = new InputStreamReader(stream, "UTF-8");
			pros.load(reader);
			
			Enumeration en = pros.propertyNames();//得到资源文件中的所有key值  
	        while (en.hasMoreElements()) {  
			    String key = (String) en.nextElement();
			    allPropertisMap.put(key, pros.getProperty(key));
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if( reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		
		
	}
}
