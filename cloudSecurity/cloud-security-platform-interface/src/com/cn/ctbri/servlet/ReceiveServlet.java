package com.cn.ctbri.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * Servlet implementation class HomeServlet
 */
//@WebServlet("/ReceiveServlet")
public class ReceiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ReceiveServlet() {
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

		String parameter = request.getParameter("parameter");
//		String parameter = "{\"virtual_group_id\": \"5564088443b9090ba07d6297\", \"total\": 26, \"start_at\": \"2015-05-26 13:45:49\", \"task_id\": 6961, \"values\": [{\"url\": \"http://www.sinosoft.com.cn/ywycp/index.html\", \"created_at\": \"2015-05-26 14:13:19\", \"type\": \"xss\", \"value\": {\"body\": \"\", \"place\": \"query\", \"method\": \"GET\", \"param\": \"page\", \"queryheaderspath\": null}}, {\"url\": \"http://www.sinosoft.com.cn/khyal/index.html\", \"created_at\": \"2015-05-26 14:13:19\", \"type\": \"xss\", \"value\": {\"body\": \"\", \"place\": \"query\", \"method\": \"GET\", \"param\": \"page\", \"queryheaderspath\": null}}, {\"url\": \"http://www.sinosoft.com.cn/test/\", \"created_at\": \"2015-05-26 14:13:19\", \"type\": \"info_leak\", \"value\": {\"type\": \"test_samples\", \"match_str\": []}}], \"group_id\": 392, \"site_id\": 46218, \"site\": \"http://www.sinosoft.com.cn/\", \"end_at\": \"2015-05-26 14:13:21\"}";
		System.out.println(parameter);  
//
//		JSONObject  dataJson = new JSONObject();
//		dataJson.put("parameter", parameter);
//		JSONArray array = dataJson.getJSONArray("values");
//
//        for (int i = 0; i < array.size(); i++) {
//            System.out.println("array:" + array.get(i));
//            System.out.println("url:" + array.getJSONObject(i).get("url"));
//            System.out.println("created_at:" + array.getJSONObject(i).get("created_at"));
//            System.out.println("type:" + array.getJSONObject(i).get("type"));
//            System.out.println("value:" + array.getJSONObject(i).get("value"));
//        }
		if (true) { 
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			pw.println("{\"code\": 0, \"message\": \"success\", \"result\": {\"id\": 1}}");
		}
	}

}
