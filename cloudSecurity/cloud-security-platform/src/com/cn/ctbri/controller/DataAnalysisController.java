package com.cn.ctbri.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IUserService;


/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-2-3
 * 描        述：  数据分析
 * 版        本：  1.0
 */
@Controller
public class DataAnalysisController {
	
	@Autowired
	IUserService userService;
	@Autowired
	IAssetService assetService;
	@Autowired
	IOrderService orderService;
	
	/**
	 * 功能描述：服务管理页面
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/dataAnalysisUI.html")
	public String adminDeleteUser(User user){
		return "/source/adminPage/userManage/dataAnalysis";
	}
}
