package com.cn.ctbri.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IServService;


/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-2-3
 * 描        述：  后台服务管理
 * 版        本：  1.0
 */
@Controller
public class ServController {
	
	@Autowired
	IServService servService;
	@Autowired
    ISelfHelpOrderService selfHelpOrderService;
	/**
	 * 功能描述：服务管理页面
	 *		 @time 2015-2-3
	 */
	@RequestMapping("/adminServUI.html")
	public String adminDeleteUser(User user,HttpServletRequest request){
		//获取服务类型
        List<Serv> servList = selfHelpOrderService.findService();
        request.setAttribute("servList", servList);//订单总数
		return "/source/adminPage/userManage/serv";
	}
}
