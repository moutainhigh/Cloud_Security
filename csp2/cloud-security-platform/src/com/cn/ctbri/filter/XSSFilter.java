package com.cn.ctbri.filter;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class XSSFilter implements Filter
{

	public void init(FilterConfig filterConfig) throws ServletException
	{
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException
	{
		
		List<String> list = new ArrayList<String>();
		list.add("/export.html");
		list.add("/websocket");
		list.add("/rest");
		String path = ((HttpServletRequest) request).getServletPath();
		if(list.contains(path)){
			//放行
			chain.doFilter(request, response);
		}else{
			XSSRequestWrapper xssRequest = new XSSRequestWrapper((HttpServletRequest) request);
	
		   String url=xssRequest.getRequestURI();
		  if(url!=null&&!"".equals(url)&&url.equals("true")){
		  request.getRequestDispatcher("/commonPage.jsp").forward(request, response);
		  }else{
	        chain.doFilter(xssRequest, response);
		  }
		}
	}

	public void destroy()
	{
		// TODO Auto-generated method stub

	}

}
