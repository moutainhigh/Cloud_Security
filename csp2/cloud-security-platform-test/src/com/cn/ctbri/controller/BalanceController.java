package com.cn.ctbri.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cn.ctbri.entity.OrderList;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.pager.PageBean;
import com.cn.ctbri.service.IOrderListService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IUserService;
import com.cn.ctbri.util.CommonUtil;

/**
 * 创 建 人  ：  zsh
 * 创建日期：  2016-5-19
 * 描        述：  安全币管理控制层
 * 版        本：  1.0
 */
@Controller
public class BalanceController {
	
	@Autowired
	IUserService userService;
	@Autowired
	IOrderListService orderListService;
	
	@Autowired
	IOrderService orderService;
	/**
	 * 功能描述：安全币的消费记录
	 *		 @time 2016-5-19
	 */
    @RequestMapping(value="balanceUI.html")
    public ModelAndView payRecord(Model model,HttpServletRequest request){
    	
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
		User loginUser = userService.findUserById(globle_user.getId()).get(0);
		
		//消费记录分页查询
    	int pageCode = 1;
    	String s= request.getParameter("pageCode");
    	
    	//获取页数
    	if(null != s && !s.trim().isEmpty()){
    		pageCode = Integer.parseInt(s);
    	}
    	
        //获取订单条目
    	PageBean<OrderList> pb = orderListService.queryPayRecordByPage(globle_user.getId(), pageCode);

		ModelAndView mv = new ModelAndView("/source/page/personalCenter/my_balance");
		mv.addObject("pb", pb);
		mv.addObject("balance", loginUser.getBalance());
        return mv;
    }
    
    /**
	 * 功能描述： 每日签到，领取安全币
	 * 参数描述：HttpServletRequest request
	 * 			HttpServletResponse response
	 *		 @time 2016-5-17
	 */
	@RequestMapping(value="/signIn.html",method=RequestMethod.POST)
	public void signIn(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> m = new HashMap<String,Object>();
		try{
			User globle_user = (User) request.getSession().getAttribute("globle_user");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String lastTime = null;
			if (globle_user.getLastSignInTime()!= null) {
				lastTime = sdf.format(globle_user.getLastSignInTime());
			}
			String nowTime = sdf.format(new Date());
			//每天只能领一次，今天领取过，以后不得再领取
			if (lastTime != null && lastTime.compareTo(nowTime) >=0){
				m.put("collect", "1");//本次没有获取金额
				return;
			}
			
			double balance = globle_user.getBalance();
			globle_user.setBalance(balance +10);
			globle_user.setLastSignInTime(new Date());
			userService.updateBalance(globle_user);
			m.put("collect", "2");//本次获取金额成功
			m.put("balance", balance +10);
			
			
		}catch(Exception e){
			e.printStackTrace();
			m.put("collect", "0");//系统异常
		}finally{
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
	
	/**
	 * 功能描述： 下单成功，领取安全币
	 * 参数描述：HttpServletRequest request
	 * 			HttpServletResponse response
	 *		 @time 2016-5-17
	 */
	@RequestMapping(value="/collectBalance.html",method=RequestMethod.POST)
	public void collectBalance(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> m = new HashMap<String,Object>();
		try{
			User globle_user = (User) request.getSession().getAttribute("globle_user");
			
			String orderListId = request.getParameter("orderListId");
			OrderList order = orderListService.findById(orderListId);
			if(order == null || order.getBalanceFlag()!= 0) {
				m.put("collect", "1");//领取失败
				return;
			}
			
			//支付金额小于5安全币,不能领取
			if (order.getPrice() < 5) {
				m.put("collect", "1");//领取失败
				return;
			}
			
			//更新安全币余额
			double balance = globle_user.getBalance();
			User user = new User();
			user.setId(globle_user.getId());
			user.setBalance(balance + 5);
			userService.updateBalance(user);
			globle_user.setBalance(user.getBalance());
			
			//设置该订单号的安全币领取状态为已领取
			OrderList orderList = new OrderList();
			orderList.setId(orderListId);
			orderList.setBalanceFlag(1);
			orderListService.updateBalanceFlag(orderList);
			
			m.put("collect", "0");//本次获取金额成功
		} catch(Exception e){
			e.printStackTrace();
			m.put("collect", "1");//系统异常
		} finally{
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
}
