package com.cn.ctbri.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.entity.Authority;
import com.cn.ctbri.entity.Authority_UserType;
import com.cn.ctbri.service.IAuthorityService;
import com.cn.ctbri.service.IAuthority_UserTypeService;
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
	@Autowired
	IAuthorityService authorityService;
	@Autowired
	IAuthority_UserTypeService authority_UserTypeService;
	/**
	 * 功能描述：用户权限页面
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/adminAuthorityUI.html")
	public String adminDeleteUser(Model model){
		List<Authority> authorityList =  authorityService.findAllAuthority();
		model.addAttribute("authorityList", authorityList);
		//查询所有用户类型和权限表
		List<Authority_UserType> listAu = authority_UserTypeService.findAll();
		//将userType和authorityId组装成一个字符串，返回给前台显示
		List<String> list = new ArrayList<String>();//[01, 02, 03, 04, 05, 06, 07, 08]
		String str = "";
		if(listAu!=null&&listAu.size()>0){
			for(Authority_UserType au :listAu){
				str = ""+au.getAuthorityId()+au.getUserType();
				list.add(str);
			}
		}
		model.addAttribute("list", list);
		return "/source/adminPage/userManage/userAuthority";
	}
	/**
	 * 功能描述：根据用户类型和权限保存权限信息
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/adminAddAuthority_userType.html")
	public String addAuthority_userType(Authority_UserType au){
		authority_UserTypeService.insert(au);
		return "/source/adminPage/userManage/userAuthority";
	}
	
	/**
	 * 功能描述：根据用户类型和权限删除权限信息
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/adminDeleteAuthority_userType.html")
	public String deleteAuthority_userType(Authority_UserType au){
		authority_UserTypeService.delete(au);
		return "/source/adminPage/userManage/userAuthority";
	}
}
