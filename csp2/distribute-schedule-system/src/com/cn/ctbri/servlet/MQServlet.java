package com.cn.ctbri.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class MQServlet extends HttpServlet{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final String DEFAULT_ACTIVEMQ_ROOT = "e:/apache-activemq-5.5.1";
    String mActiveMQRoot = DEFAULT_ACTIVEMQ_ROOT;
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
