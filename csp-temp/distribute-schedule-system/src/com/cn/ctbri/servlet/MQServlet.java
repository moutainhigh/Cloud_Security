package com.cn.ctbri.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class MQServlet extends HttpServlet{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String DEFAULT_ACTIVEMQ_ROOT;
    
	static{
		try {
			Properties p = new Properties();
			p.load(MQServlet.class.getClassLoader().getResourceAsStream("default.properties"));
			DEFAULT_ACTIVEMQ_ROOT = p.getProperty("DEFAULT_ACTIVEMQ_ROOT");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	String mActiveMQRoot = "e:/apache-activemq-5.5.1/bin/activemq.bat";
//	String mActiveMQRoot = "/usr/local/apache-activemq-5.5.1/bin/activemq.bat";
//	String mActiveMQRoot = "/usr/local/apache-activemq-5.5.1/bin/activemq.bat";
    private Process startProcess;
 
    
    public MQServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	MQServlet(String activeMQRoot) {
        mActiveMQRoot = activeMQRoot;
    }
 
    public void init()  throws ServletException{
        try {
			if (checkMQRoot()) {
			    System.out.println("Starting ActiveMQ...");
			    Runtime runtime = Runtime.getRuntime();
 
			    startProcess = runtime.exec(mActiveMQRoot);
 
			    // following are message when start activeMQ
			    BufferedReader bufferedReader = null;
			    try {
			        bufferedReader = new BufferedReader(new InputStreamReader(
			                startProcess.getInputStream()));
			        String line;
			        while ((line = bufferedReader.readLine()) != null) {
//			            System.out.println(line);
			        }
			    } catch (IOException e) {
			    	System.out.println("ActiveMQ has started!");
			    } finally {
			        bufferedReader.close();
			    }
			    System.out.println("Started ActiveMQ...");
			}else{
				System.out.println("Starting ActiveMQ fail...启动文件不存在");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
 
    /**
     * How stop activeMQ?? simulate? output "Ctrl+C" to Process's OutputStream.
     * @return
     * @throws IOException
     */
    public void destroy(){
        try {
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
}
