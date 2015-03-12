package com.cn.ctbri.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.cn.ctbri.entity.User;


/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-4
 * 描        述：  系统过滤器
 * 版        本：  1.0
 */
public class SystemFilter extends OncePerRequestFilter  {
	@Override
	protected void doFilterInternal(HttpServletRequest request,  
            HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//存放在没有Session之前，可以放行的连接
		List<String> list = new ArrayList<String>();
		list.add("/asset_verification.html");
		list.add("/registUI.html");
		list.add("/loginUI.html");
		list.add("/login.html");
		list.add("/index.html");
		list.add("/exit.html");
		list.add("/regist_checkSendEmail.html");
		list.add("/regist_checkName.html");
		list.add("/regist_checkMobile.html");
		list.add("/regist_checkEmail.html");
		list.add("/regist_checkEmailActivationCode.html");
		list.add("/regist_checkSendMobile.html");
		list.add("/regist.html");
		list.add("/toLoginUI.html");
		list.add("/admin.html");
		list.add("/adminLogin.html");
		list.add("/adminExit.html");
		list.add("/errorMsg.html");
		list.add("/registToLogin.html");
		list.add("/noticeDescUI.html");
		list.add("/getNum.html");
		//获取访问的url路径
		String path = request.getServletPath();
		forwordIndexPage(path,request,response);
		
		//当没有Session之前，可以放行的连接
		if(list.contains(path)){
			//放行
			filterChain.doFilter(request, response);
			return;
		}else{
			//获取Session，完成粗颗粒权限控制，从Session中获取用户信息
			User user = (User)request.getSession().getAttribute("globle_user");
			//说明当前操作存在Session，需要放行
			if(user!=null){
				//如果访问路径包含admin说明是访问的后台，检验是否有权限访问，只有超级管理员和系统管理员才可以访问
				if(path.contains("admin")){
					if(user.getType()==2){
						request.setAttribute("errorMsg", "对不起，您没有权限登录后台！");
						request.getRequestDispatcher("/errorMsg.html").forward(request,response);
						return;
					}
				}
				//放行
				try{
					filterChain.doFilter(request, response);
				}catch (Exception e) {
					response.sendRedirect(request.getContextPath()+"/loginUI.html");
					e.printStackTrace();
				}
				return;
			}else{
				response.sendRedirect(request.getContextPath()+"/loginUI.html");
			}
		}
	}

	/**在跳转到login.jsp页面之前，先执行从Cookie中获取name和password的操作*/
	private void forwordIndexPage(String path, HttpServletRequest request,HttpServletResponse response) {
		if(path.contains("loginUI.html")||path.contains("admin.html")){
			//登录名
			String name = "";
			//密码
			String password = "";
			//如果登录名和密码在Cookie中存在，默认让记住我的复选框选中
			String checked = "";
			Cookie [] cookies = request.getCookies();
			if(cookies!=null && cookies.length>0){
				for(Cookie cookie:cookies){
					if(cookie.getName().equals("name")){
						name = cookie.getValue();
						//如果name存在中文，进行解码
						try {
							name = URLDecoder.decode(name, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						checked = "checked";
					}
					if(cookie.getName().equals("password")){
						password = cookie.getValue();
					}
				}
			}
			request.setAttribute("name", name);
			request.setAttribute("password", password);
			request.setAttribute("checked", checked);
		}
	}

	

}
