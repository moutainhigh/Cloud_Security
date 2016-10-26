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
		list.add("/admin.html");
		list.add("/adminLogin.html");
		list.add("/adminExit.html");
		list.add("/errorMsg.html");
		list.add("/initDistrictList.html");
		list.add("/getDistrictData.html");
		list.add("/getDistrictAlarmTop5.html");
		list.add("/getServiceAlarmTop5.html");
		list.add("/getServiceAlarmMonth5.html");
		list.add("/findUseAssetAddr.html");
		//------------------
		list.add("/findAllFactory.html");
		list.add("/equFindAll.html");
		list.add("/addEqu.html");
		list.add("/updEqu.html");
		list.add("/findEquById.html");
		list.add("/delEquById.html");
		list.add("/getPageSize.html");
		list.add("/getDiskUsage.html");
		list.add("/getSysCpuUsage.html");
		list.add("/getSysMemoryUsage.html");
		list.add("/getCountRuning.html");
				
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
			User adminUser = (User)request.getSession().getAttribute("admin_user");
//			if(path.contains("admin")){
				if(adminUser!=null){
					//如果访问路径包含admin说明是访问的后台，检验是否有权限访问，只有超级管理员和系统管理员才可以访问
					if(adminUser.getType()==0||adminUser.getType()==1){
						try{
							filterChain.doFilter(request, response);
						}catch (Exception e) {
							response.sendRedirect(request.getContextPath()+"/admin.html");
							e.printStackTrace();
						}
						return;
					}
				}
//				request.setAttribute("msg", "对不起，您可能存在的问题:没有权限登录后台、未登录、登录超时！");
				request.getRequestDispatcher("/admin.html").forward(request,response);
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
