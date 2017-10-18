package com.cn.ctbri.listener;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
  
public class ContextClient implements ServletContextListener {   
	
//	public static Client client = null;
    public void contextInitialized(ServletContextEvent event) {
//    	client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
        System.out.println("created on " +  new Date() + ".");   
    }   
  
    public void contextDestroyed(ServletContextEvent event) {  
//    	client.close();
        System.out.println("destroyed on " +   new Date() + ".");   
    }   
}   


