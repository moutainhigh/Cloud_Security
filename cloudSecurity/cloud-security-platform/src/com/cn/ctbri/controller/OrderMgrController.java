package com.cn.ctbri.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.service.IUserService;

/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-1-12
 * 描        述：  订单管理控制层
 * 版        本：  1.0
 */
@Controller
public class OrderMgrController {
	
	 /**
	 * 功能描述： 用户中心-自助下单
	 * 参数描述：  无
	 *     @time 2015-1-12
	 */
	@RequestMapping(value="selfHelpOrderInit.html")
	public String selfHelpOrderInit(HttpServletRequest request){
		return "/source/page/order/order";
	}
	
	/**
     * 功能描述： 用户中心-订单跟踪
     * 参数描述：  无
     *     @time 2015-1-12
     */
    @RequestMapping(value="orderTrackInit.html")
    public String orderTrackInit(HttpServletRequest request){
        return "/source/page/order/orderTrack";
    }
	
	

	

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
