package com.cn.ctbri.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IUserService;
import com.cn.ctbri.util.LogonUtils;


/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-29
 * 描        述：  后台用户管理
 * 版        本：  1.0
 */
@Controller
public class AdminUserController {
	
	@Autowired
	IUserService userService;
	
	/**
	 * 功能描述：后台登录页面 
	 * 参数描述：无
	 *		 @time 2015-1-29
	 */
	@RequestMapping("/admin.html")
	public String admin(){
		return "/source/adminPage/adminLogin/adminLogin";
	}
	/**
	 * 功能描述：后台登录
	 * 参数描述： Model model,HttpServletRequest request
	 *		 @time 2015-1-29
	 */
	@RequestMapping("/adminLogin.html")
	public String adminLogin(User user,HttpServletRequest request,HttpServletResponse response){
		//添加验证码，判断验证码输入是否正确
		boolean flag = LogonUtils.checkNumber(request);
		if(!flag){
			request.setAttribute("msg", "验证码输入有误");//向前台页面传值
			return "/source/adminPage/adminLogin/adminLogin";
		}
		//判断用户名密码输入是否正确
		User _user = null;
		String name = user.getName().trim();
		String password = user.getPassword().trim();
		List<User> users = userService.findUserByName(name);
		if(users.size()>0){
			_user = users.get(0);
			//从页面上获取密码和User对象中存放的密码，进行匹配，如果不一致，提示【密码输入有误】
			String md5password = DigestUtils.md5Hex(password);
			if(!md5password.equals(_user.getPassword())){
				request.setAttribute("msg", "密码输入有误");
				return "/source/adminPage/adminLogin/adminLogin";//跳转到登录页面
			}
		}else{
			request.setAttribute("msg", "用户名输入有误");
			return "/source/adminPage/adminLogin/adminLogin";//跳转到登录页面
		}
		//判断是不是后台可以登录的用户0：超级管理员，1：管理员
		if(_user.getType() == 2){
			request.setAttribute("msg", "对不起，您没有登录后台的权限！");
			return "/source/adminPage/adminLogin/adminLogin";
		}
		/**记住密码功能*/
		LogonUtils.remeberAdmin(request,response,name,password);
		//将User放置到Session中，用于这个系统的操作
		request.getSession().setAttribute("globle_user", _user);
		return "/source/adminPage/userManage/userManage";
	}
	/**
	 * 功能描述：退出
	 * 参数描述：无
	 *		 @time 2015-1-29
	 */
	@RequestMapping("/adminExit.html")
	public String adminExit(HttpServletRequest request){
		request.getSession().invalidate();
		return "redirect:/admin.html";
	}
	/**
	 * 功能描述：自注册用户无权限登录后台
	 *		 @time 2015-1-29
	 */
	@RequestMapping("/errorMsg.html")
	public String errorMsg(){
		return "/source/error/errorMsg";
	}
	
	
}
