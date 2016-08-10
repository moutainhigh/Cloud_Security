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
		list.add("/regist_checkNumber.html");
		list.add("/login_checkName.html");
		list.add("/login_checkCookie.html");
		list.add("/regist_checkActivationCode.html");
		list.add("/checkSendMobile.html");
		list.add("/regist.html");
		list.add("/toLoginUI.html");
		list.add("/errorMsg.html");
		list.add("/registToLogin.html");
		list.add("/noticeDescUI.html");
		list.add("/getNum.html");
		list.add("/aider.html");
		list.add("/forgetPass.html");
		list.add("/updatePass.html");
		list.add("/isExitEmail.html");
		list.add("/confirmPass.html");
		list.add("/isExitPhone.html");
		list.add("/key.html");
//		list.add("/chinas.html");
		list.add("/initDistrictList.html");
		list.add("/getDistrictData.html");
		list.add("/getDistrictAlarmTop5.html");
		list.add("/getServiceAlarmTop5.html");
		list.add("/getServiceAlarmMonth5.html");
		list.add("/findUseAssetAddr.html");
		//add by tangxr 2016-1-23
		list.add("/web_anquanbang.html");//网络安全帮 
		list.add("/sa_anquanbang.html");//安全动态感知
		list.add("/joinUs.html");//加入我们
		list.add("/knowUs.html");//了解我们
		//add by tangxr 2016-4-18
		list.add("/api_anquanbang.html");//安全动态感知
		list.add("/Xpage.html");//x专区
		list.add("/detectionUrl.html");//x专区
		list.add("/bugUI.html");//x专区
		list.add("/bug.html");//x专区
		list.add("/serviceUserUI.html");
		list.add("/serviceUser.html");
		list.add("/orderServiceUI.html");
		list.add("/orderServiceCount.html");
		list.add("/attackUI.html");
		list.add("/attackCount.html");
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
//			if(!path.contains("admin")){
				//如果访问路径包含admin说明是访问的后台，检验是否有权限访问，只有超级管理员和系统管理员才可以访问
				if(user!=null&&(user.getType()==2||user.getType()==3)){
					//放行
					try{
						filterChain.doFilter(request, response);
					}catch (Exception e) {
						response.sendRedirect(request.getContextPath()+"/loginUI.html");
						e.printStackTrace();
					}
					return;
				}
//					request.setAttribute("msg", "对不起，您可能存在的问题:没有权限登录前台、未登录、登录超时");
				request.getSession().removeAttribute("globle_user");
				
				//add by tangxr 2016-3-14
				String indexPage = request.getParameter("indexPage");
				if(indexPage!=null){
					int index = Integer.parseInt(request.getParameter("indexPage"));
					if(index == 1){
						request.getSession().setAttribute("serviceId", Integer.parseInt(request.getParameter("serviceId")));
					}else if(index == 2){
						request.getSession().setAttribute("apiId", Integer.parseInt(request.getParameter("apiId")));
					}
					request.getSession().setAttribute("indexPage", index);
				}else{
					request.getSession().setAttribute("indexPage", 0);
				}
				//end
				request.getRequestDispatcher("/loginUI.html").forward(request,response);
				return;
//			}
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
