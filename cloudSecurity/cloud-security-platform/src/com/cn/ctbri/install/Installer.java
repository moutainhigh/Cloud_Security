package com.cn.ctbri.install;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ctbri.entity.Authority;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAuthorityService;
import com.cn.ctbri.service.IUserService;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-29
 * 描        述：  初始化超级管理员
 * 版        本：  1.0
 */
@Component
public class Installer{
	@Autowired
	IUserService userService;
	@Autowired
	IAuthorityService authorityService;
	
	@Transactional
	public void install() {
		createAdmin();
		initAuthority();
	}
	
	private void createAdmin(){
		User user = new User("admin007",DigestUtils.md5Hex("qwer123"), 1, 0);
		userService.insert(user);
		System.out.println("超级管理员："+user.getName());
		System.out.println("密码：qwer123");
	}
	//初始化权限
	private void initAuthority(){
		//登录注册不要添加权限，因为还有没有登录，所以不知道用户的权限是什么
		authorityService.saveAuthority(new Authority("资产维护", "/userAssetsUI.html"));
		authorityService.saveAuthority(new Authority("资产验证", "/verificationAsset.html"));
		authorityService.saveAuthority(new Authority("自助下单", "/selfHelpOrderInit.html"));
		authorityService.saveAuthority(new Authority("订单跟踪", "/orderTrackInit.html"));
		authorityService.saveAuthority(new Authority("用户账单", "/userBillUI.html"));
		authorityService.saveAuthority(new Authority("在线帮助", null));
		authorityService.saveAuthority(new Authority("用户管理", "/adminUserManageUI.html"));
		authorityService.saveAuthority(new Authority("在线充值", null));
		authorityService.saveAuthority(new Authority("资料修改", "/saveUserData.html"));
		authorityService.saveAuthority(new Authority("服务管理", null));
		authorityService.saveAuthority(new Authority("数据分析", "/adminDataAnalysisUI.html"));
		authorityService.saveAuthority(new Authority("系统管理", "/adminSystemManageUI.html"));
		authorityService.saveAuthority(new Authority("用户删除", "/adminDeleteUser.html"));
	}
	public static void main(String[] args) {
		System.out.println("正在执行安装...");
		//安装开始时间
		long begin = new Date().getTime();
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		Installer installer = (Installer) ac.getBean("installer");
		installer.install();
		//安装结束时间
		long end = new Date().getTime();
		//安装用时
		long time = end-begin;
		System.out.println("== 安装完毕 ==");
		System.out.println("安装用时："+time);
	}
}
