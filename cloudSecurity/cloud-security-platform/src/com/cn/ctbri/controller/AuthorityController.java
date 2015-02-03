package com.cn.ctbri.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IUserService;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-2-3
 * 描        述：  后台权限管理
 * 版        本：  1.0
 */
@Controller
public class AuthorityController {
	
	@Autowired
	IUserService userService;
	/**
	 * 功能描述：用户权限页面
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/adminAuthorityUI.html")
	public String adminDeleteUser(User user){
		return "/source/adminPage/userManage/userAuthority";
	}
	
	
}
