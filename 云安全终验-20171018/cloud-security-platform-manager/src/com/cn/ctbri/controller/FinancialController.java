package com.cn.ctbri.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.FinancialService;
import com.cn.ctbri.util.CommonUtil;

import net.sf.json.JSONObject;

/**
 * 创 建 人  ：  houtp
 * 创建日期：  2016-09-02
 * 描        述：  后台财务统计分析
 * 版        本：  1.0
 */
@Controller
public class FinancialController {
    
    @Autowired
    FinancialService financialService;
    
    /**
     * 功能描述：日交易统计分析
     * @time 2016-09-06
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping("/financialManage.html")
    public ModelAndView financialManage(HttpServletRequest request){
        @SuppressWarnings("unused")
		//User user = (User) request.getSession().getAttribute("globle_user");
    	ModelAndView view = new ModelAndView();
    	//当天订单交易额总数
        List<Map> orderAmount = financialService.countOrderAmount();
        
        //各类服务订单交易额总数
        @SuppressWarnings("unused")
		List<Order> serviceAmountList = financialService.findServiceAmount();
        
        /*//最近6小时交易额变化曲线
        @SuppressWarnings("unused")
		List<Map> tradingCurveList = financialService.findTradingCurve();*/
        
        view.addObject("orderAmount",orderAmount.get(0).get("price"));
        view.addObject("serviceAmountList",serviceAmountList);
//        view.addObject("tradingCurveList",tradingCurveList);
        view.setViewName("/source/adminPage/financial/financialManage");
        return view;
    }
 
    
    /**
     * 功能描述：最近6小时交易额变化曲线
     * @time 2016-09-06
     */
	@RequestMapping("/findTradingCurve.html")
	@ResponseBody
    public void findTradingCurve(HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> map = new HashMap<String,Object>();
    	
        //最近6小时交易额变化曲线
		List<Order> tradingCurveList = financialService.findTradingCurve();
        
		map.put("tradingCurveList",tradingCurveList);
		JSONObject JSON = CommonUtil.objectToJson(response, map);
		// 把数据返回到页面
        try {
			CommonUtil.writeToJsp(response, JSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	 /**
     * 功能描述：当日和当月内各类服务订单交易额占比情况
     * @time 2016-09-07
     */
	@RequestMapping("/findOrderAmountPie.html")
	@ResponseBody
    public void findOrderAmountPie(HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		String createDate = request.getParameter("create_date");
		String reportype = request.getParameter("reportype");
		List<Order> orderAmountPieList = null;
		String flag = "";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
    	if("1".equals(reportype)){//当日内各类服务订单交易额占比情况
    		//String createDate1 = "2016-09-25";
    		orderAmountPieList = financialService.findOrderAmountDayPie(createDate);
    		flag = "1";
    	}
    	
        if("2".equals(reportype)){//当月内各类服务订单交易额占比情况
        	//String createDate2 = "2016-09";
        	orderAmountPieList = financialService.findOrderAmountMonthPie(createDate);
        	flag = "2";
    	}
        if(orderAmountPieList.size()>0){
        	paramMap.put("sta","1");
        }else{
        	paramMap.put("sta","0");
        }

		paramMap.put("orderAmountPieList",orderAmountPieList);
		paramMap.put("flag",flag);
		JSONObject JSON = CommonUtil.objectToJson(response, paramMap);
		// 把数据返回到页面
        try {
			CommonUtil.writeToJsp(response, JSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	 /**
     * 功能描述：当日和当月交易额总数
     * @time 2016-09-07
     */
	@RequestMapping("/findOrderAmountLine.html")
	@ResponseBody
    public void findOrderAmountLine(HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		String createDate = request.getParameter("create_date");
		String reportype = request.getParameter("reportype");
		String serviceId = request.getParameter("serviceId");
		List<Order> orderAmountLineList = null;
		String flag = "";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
    	if("1".equals(reportype)){//当日交易额总数
    		//String createDate1 = "2015-08-07";
    		//String beginDate = createDate1 + " 00:00:00";
    		//String endDate = createDate1 + " 23:59:59";
    		String beginDate = createDate + " 00:00:00";
    		String endDate = createDate + " 23:59:59";
    		paramMap.put("beginDate", beginDate);
    		paramMap.put("endDate", endDate);
//    		paramMap.put("servName", "1");
    		paramMap.put("serviceId", serviceId);
    		orderAmountLineList = financialService.findOrderAmounDayLine(paramMap);
    		flag = "1";
    	}
    	
        if("2".equals(reportype)){//当月交易额总数
        	//String createDate2 = "2015-10";
        	paramMap.put("serviceId", serviceId);
    		paramMap.put("createDate", createDate);
    		orderAmountLineList = financialService.findOrderAmountMonthLine(paramMap);
        	flag = "2";
    	}
        
        if(orderAmountLineList.size()>0){
        	paramMap.put("sta","1");
        }else{
        	paramMap.put("sta","0");
        }
        
		paramMap.put("orderAmountLineList",orderAmountLineList);
		paramMap.put("flag",flag);
		JSONObject JSON = CommonUtil.objectToJson(response, paramMap);
		// 把数据返回到页面
        try {
			CommonUtil.writeToJsp(response, JSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
