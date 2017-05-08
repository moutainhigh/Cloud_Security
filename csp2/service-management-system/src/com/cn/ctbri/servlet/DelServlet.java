package com.cn.ctbri.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cn.ctbri.common.ArnhemWorker;
import com.cn.ctbri.service.ITaskService;


/**
 * Servlet implementation class HomeServlet
 */
//@WebServlet("/ReceiveServlet")
public class DelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(DelServlet.class.getName());
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
        for (int i = 173673;i<180595; i++) {//  16072909552723161
	      	String s = ArnhemWorker.removeTask(sessionId,i+"_16113016152646796",2);
	      	System.out.println(s+i+"_16113016152646796");
		}
		if (true) { 
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			pw.println("{\"code\": 0, \"message\": \"success\", \"result\": {\"id\": 1}}");
		}
	}

}
