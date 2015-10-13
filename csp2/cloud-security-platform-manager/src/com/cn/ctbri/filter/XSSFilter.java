package com.cn.ctbri.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class XSSFilter implements Filter {

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
    public void destroy() {
    }



	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		 chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
	}

	

}