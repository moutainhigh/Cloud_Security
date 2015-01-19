package com.cn.ctbri.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.service.IUserService;

/**
 * 创 建 人  ：  于永波
 * 创建日期：  2015-1-12
 * 描        述：  用户中心控制层
 * 版        本：  1.0
 */
@Controller
public class UserCenterController {
	
	@Autowired
	IUserService userService;
	
	 /**
	 * 功能描述： 用户中心页面
	 * 参数描述：  无
	 *     @time 2015-1-12
	 */
	@RequestMapping(value="userCenterUI.html")
	public String userCenterUI(){
		return "/source/page/userCenter/userCenter";
	}
	
	/**
     * 功能描述： 用户中心-基本资料页面
     * 参数描述：  无
     *     @time 2015-1-12
     */
  /*  @RequestMapping(value="userDataUI.html")
    public String userDataUI(){
        return "/source/page/userCenter/userData";
    }*/
    
    /**
     * 功能描述： 用户中心-我的账单页面
     * 参数描述：  无
     *     @time 2015-1-12
     */
 /*   @RequestMapping(value="userBillUI.html")
    public String userBillUI(){
        return "/source/page/userCenter/userBill";
    }*/
    
    
    /**
     * 功能描述： 用户中心-我的资产页面
     * 参数描述：  无
     *     @time 2015-1-12
     */
   /* @RequestMapping(value="userAccetsUI.html")
    public String userAccetsUI(){
        return "/source/page/userCenter/userAccets";
    }*/
	
	

	

	/**
	 * 功能描述： 把数据返回到页面
	 * 参数描述： HttpServletResponse response, JSONObject JSON
	 * @throws Exception 
	 *		 @time 2015-1-12
	 */
	private void writeToJsp(HttpServletResponse response, JSONObject JSON)
			throws IOException {
		response.getWriter().write(JSON.toString());
		response.getWriter().flush();
	}
	
	/**
	 * 功能描述：  object转化为Json格式
	 * 参数描述： HttpServletResponse response,Map<String, Object> m
	 * @throws Exception 
	 *		 @time 2015-1-12
	 */
	private JSONObject objectToJson(HttpServletResponse response,
			Map<String, Object> m) {
		JSONObject JSON = JSONObject.fromObject(m);
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		return JSON;
	}
	
	
	
	
	
	
}
