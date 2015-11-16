package com.cn.ctbri.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ActiveMQListener  implements ServletContextListener{
    static final String DEFAULT_ACTIVEMQ_ROOT = "e:/apache-activemq-5.5.1";
    String mActiveMQRoot = DEFAULT_ACTIVEMQ_ROOT;
    private Process startProcess;
 
    ActiveMQListener(String activeMQRoot) {
        mActiveMQRoot = activeMQRoot;
    }
 
    public boolean startup()  throws IOException {
        if (checkMQRoot()) {
            System.out.println("Starting ActiveMQ...");
            Runtime runtime = Runtime.getRuntime();
 
            startProcess = runtime.exec(mActiveMQRoot + "/bin/activemq.bat");
 
            // following are message when start activeMQ
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(
                        startProcess.getInputStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                // TODO: handle exception
                throw e;
            } finally {
                bufferedReader.close();
            }
            System.out.println("Started ActiveMQ...");
        }
 
        return true;
    }
 
    /**
     * How stop activeMQ?? simulate? output "Ctrl+C" to Process's OutputStream.
     * @return
     * @throws IOException
     */
    public boolean stop() throws IOException {
        if (checkMQRoot()) {
            OutputStream outputStream = null;
            try {
                outputStream = startProcess.getOutputStream();
                //outputStream.write("".getBytes());
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                outputStream.close();
            }
        }
 
        return true;
    }
 
    /**
     * check ActiveMQ Root
     * 
     * @return
     */
    private boolean checkMQRoot() {
        File file = new File(mActiveMQRoot);
        if (!file.exists() || !file.isDirectory()) {
            return false;
        }
 
        return true;
    }
 
 /*   *//**
     * for test
     * 
     * @param args
     *//*
    public static void main(String[] args) {
        ActiveMQListener activeMQStarter = new ActiveMQListener(
                "D:/ActiveMQ/apache-activemq-5.2.0");
        try {
            activeMQStarter.startup();
        } catch (IOException e) {
            System.out.println("Failed to startup ActiveMQ." + e.getMessage());
        }
    }*/

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
	}


 
}
