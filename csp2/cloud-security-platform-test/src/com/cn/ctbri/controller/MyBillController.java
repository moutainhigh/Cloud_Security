package com.cn.ctbri.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.util.CommonUtil;

/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-14
 * 描        述：  我的账单
 * 版        本：  1.0
 */
@Controller
public class MyBillController {
	
	@Autowired
	IOrderService orderService;
	@Autowired
	IServService servService;
	@Autowired
	IOrderAssetService orderAssetService;
	@Autowired
	ITaskService taskService;
	
	/**
	 * 功能描述： 查看详情
	 * 参数描述： Model model
	 *		 @time 2015-1-15
	 */
	@RequestMapping("/orderDetail.html")
	public void orderDetail(String orderId,HttpServletResponse response){
		List<OrderAsset> orderAsset = orderAssetService.findOrderAssetByOrderId(orderId);
		int count = 0;//资产个数
		if(orderAsset!=null&&orderAsset.size()>0){
			count=orderAsset.size();
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("count", count);
		int num = orderService.findScanCountByOrderId(orderId);
		m.put("num", num);
		//object转化为Json格式
		JSONObject JSON = CommonUtil.objectToJson(response, m);
		try {
			// 把数据返回到页面
			CommonUtil.writeToJsp(response, JSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
