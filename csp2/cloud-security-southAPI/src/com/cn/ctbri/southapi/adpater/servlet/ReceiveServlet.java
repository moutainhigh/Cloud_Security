package com.cn.ctbri.southapi.adpater.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cn.ctbri.southapi.adpater.manager.WebsocDeviceOperation;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/ReceiveServlet")
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
		WebsocDeviceOperation websocDeviceOperation = new WebsocDeviceOperation();
		String parameter = request.getParameter("parameter");
		System.out.println("parameter"+parameter);
		String responseString = websocDeviceOperation.postDetectResult("", parameter);
		if (true) {
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			pw.println(responseString);
		}
	}

}
