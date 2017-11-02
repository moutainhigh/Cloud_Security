package com.ctbri.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class ContentTypeFilter implements Filter{
	private String charset = "UTF-8";  
    private FilterConfig config;  
      
    public void destroy() {  
        System.out.println(config.getFilterName()+"被销毁");  
        charset = null;  
        config = null;  
    }  
  
    public void doFilter(ServletRequest request, ServletResponse response,  
            FilterChain chain) throws IOException, ServletException {  
        //设置请求响应字符编码 
        request.setCharacterEncoding(charset);  
        response.setCharacterEncoding(charset);  
          
        HttpServletRequest req = (HttpServletRequest)request;  
          
          
        System.out.println("----请求被"+config.getFilterName()+"过滤");  
        //执行下一个过滤器（如果有的话,否则执行目标servlet）  
        chain.doFilter(req, response);  
          
        System.out.println("----响应被"+config.getFilterName()+"过滤");  
  
    }  
  
    public void init(FilterConfig config) throws ServletException {  
            this.config = config;  
            String charset = config.getServletContext().getInitParameter("charset");    
            if( charset != null && charset.trim().length() != 0)  
            {  
                this.charset = charset;  
            }  
    }  
    
    
 
}
