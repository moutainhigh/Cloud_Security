package com.cn.ctbri.listener;

import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

  
public class ContextClient implements ServletContextListener {   
	
	private static String SERVER_WEB_ROOT;
	private static String SERVER_MANAGER_ROOT;
	private static String SouthAPI_WEB_ROOT;
	static{
		try {
			Properties p = new Properties();
			Properties p1 = new Properties();
			p.load(ContextClient.class.getClassLoader().getResourceAsStream("internal.properties"));
			p1.load(ContextClient.class.getClassLoader().getResourceAsStream("southAPI.properties"));
			
			SERVER_WEB_ROOT = p.getProperty("SERVER_WEB_ROOT");
			SERVER_MANAGER_ROOT = p.getProperty("SERVER_MANAGER_ROOT");
			SouthAPI_WEB_ROOT=p1.getProperty("SouthAPI_WEB_ROOT");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Client client = null;
	public static WebTarget mainTarget = null;
	public static WebTarget mainManagerTarget = null;
	public static WebTarget southAPImainTarget = null;
    public void contextInitialized(ServletContextEvent event) {
    	client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
    	mainTarget = client.target(SERVER_WEB_ROOT);
    	mainManagerTarget = client.target(SERVER_MANAGER_ROOT);
        southAPImainTarget = client.target(SouthAPI_WEB_ROOT);
    	System.out.println("created on " +  new Date() + ".");   
    }   
  
    public void contextDestroyed(ServletContextEvent event) {  
    	client.close();
        System.out.println("destroyed on " +   new Date() + ".");   
    }   
}   


