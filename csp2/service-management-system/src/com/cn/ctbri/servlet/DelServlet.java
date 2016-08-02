package com.cn.ctbri.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cn.ctbri.common.ArnhemWorker;
import com.cn.ctbri.common.Scheduler4Status;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Task;
import com.cn.ctbri.entity.TaskWarn;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.ITaskService;
import com.sun.jersey.spi.resource.Singleton;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * Servlet implementation class HomeServlet
 */
//@WebServlet("/ReceiveServlet")
public class DelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(Scheduler4Status.class.getName());
	/**
	 * Default constructor.
	 */
	public DelServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	    WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());  
        ITaskService taskService  = (ITaskService) context.getBean("taskService");
        
        String sessionId = ArnhemWorker.getSessionId(2);
//        List<Task> tlist = taskService.getDels();
//        for (Task task : tlist) {
//        	String s = ArnhemWorker.removeTask(sessionId,"55049_16072909552723161",2);
//        	System.out.println(s);
//		}
        for (int i = 56000;i<57000; i++) {
	      	String s = ArnhemWorker.removeTask(sessionId,i+"_16072909552723161",2);
	      	System.out.println(s);
		}
		if (true) { 
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			pw.println("{\"code\": 0, \"message\": \"success\", \"result\": {\"id\": 1}}");
		}
	}

}
