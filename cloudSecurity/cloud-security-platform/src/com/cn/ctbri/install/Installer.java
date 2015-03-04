package com.cn.ctbri.install;


import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cn.ctbri.entity.User;
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
	
	@Transactional
	public void install() {
		createAdmin();
	}
	
	private void createAdmin(){
		User user = new User("admin",DigestUtils.md5Hex("qwer123"), 1, 0);
		userService.insert(user);
		System.out.println("超级管理员："+user.getName());
		System.out.println("密码：qwer123");
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
