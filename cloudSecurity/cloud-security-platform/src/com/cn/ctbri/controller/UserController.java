package com.cn.ctbri.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.ctbri.cfg.Configuration;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IUserService;
import com.cn.ctbri.util.Mail;
import com.cn.ctbri.util.MailUtils;
import com.cn.ctbri.util.Random;
import com.cn.ctbri.util.SMSUtils;

/**
 * 创 建 人  ：  于永波
 * 创建日期：  2014-12-31
 * 描        述：  用户控制层
 * 版        本：  1.0
 */
@Controller
public class UserController {
	
	@Autowired
	IUserService userService;
	
	@RequestMapping(value="login.html")
	public ModelAndView login(){
		return null;
	}
	
	 /**
	 * 功能描述： 注册页面
	 * 参数描述：  无
	 *		 @time 2014-12-31
	 */
	@RequestMapping(value="registUI.html")
	public String registUI(){
		return "/source/page/regist/regist";
	}
	
	
	/**
	 * 功能描述： 注册
	 * 参数描述：  User user
	 *		 @time 2014-12-31
	 */
	@RequestMapping(value="regist.html")
	public String regist(User user){
		String name = user.getName();
		String password = user.getPassword();
		if(name!=null&&!"".equals(name)&&password!=null&&!"".equals(password)){
			//按用用户名、邮箱、手机号码组合查询用户,防止刷页面
			List<User> users = userService.findUserByCombine(user);
			if(!(users.size()>0)){
				String passwdMD5 = DigestUtils.md5Hex(password);
				user.setPassword(passwdMD5);
				user.setStatus(1);  //用户状态(1：正常，0：停用)
				user.setType(2);	//用户类型（0：超级管理员，1：管理员，2：用户）
				userService.insert(user);
			}
		}
		return "/source/page/regist/regist";
	}
	
	/**
	 * 功能描述：  注册时检验用户名是否已经存在
	 * 参数描述：  String name,HttpServletResponse response
	 * @throws Exception 
	 *		 @time 2014-12-31
	 */
	@RequestMapping(value="regist_checkName.html", method = RequestMethod.POST)
	@ResponseBody
	public void regist_checkName(String name,HttpServletResponse response){
		List<User> users = userService.findUserByName(name);
		int count = 0;
		if(users.size()>0){
			count = users.size();
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("count", count);
		//object转化为Json格式
		JSONObject JSON = objectToJson(response, m);
		try {
			// 把数据返回到页面
			writeToJsp(response, JSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 功能描述： 把数据返回到页面
	 * 参数描述： HttpServletResponse response, JSONObject JSON
	 * @throws Exception 
	 *		 @time 2014-12-31
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
	 *		 @time 2014-12-31
	 */
	private JSONObject objectToJson(HttpServletResponse response,
			Map<String, Object> m) {
		JSONObject JSON = JSONObject.fromObject(m);
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		return JSON;
	}
	
	/**
	 * 功能描述：  注册时检验手机号码是否已经存在
	 * 参数描述：  String name,HttpServletResponse response
	 * @throws Exception 
	 *		 @time 2015-1-4
	 */
	@RequestMapping(value="regist_checkMobile.html", method = RequestMethod.POST)
	@ResponseBody
	public void regist_checkMobile(String mobile,HttpServletResponse response){
		List<User> users = userService.findUserByMobile(mobile);
		int count = 0;
		if(users.size()>0){
			count = users.size();
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("count", count);
		//object转化为Json格式
		JSONObject JSON = objectToJson(response, m);
		try {
			// 把数据返回到页面
			writeToJsp(response, JSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能描述：  注册时检验邮箱是否已经存在
	 * 参数描述：  String name,HttpServletResponse response
	 * @throws Exception 
	 *		 @time 2015-1-4
	 */
	@RequestMapping(value="regist_checkEmail.html", method = RequestMethod.POST)
	@ResponseBody
	public void regist_checkEmail(String email,HttpServletResponse response){
		List<User> users = userService.findUserByEmail(email);
		int count = 0;
		if(users.size()>0){
			count = users.size();
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("count", count);
		//object转化为Json格式
		JSONObject JSON = objectToJson(response, m);
		try {
			// 把数据返回到页面
			writeToJsp(response, JSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能描述： 发送邮箱验证码
	 * 参数描述：  User user,HttpServletResponse response
	 * @throws Exception 
	 *		 @time 2015-1-4
	 */
	long activationCode = 0;
	@RequestMapping(value="regist_checkSendEmail.html", method = RequestMethod.POST)
	@ResponseBody
    public void send(User user,HttpServletRequest request,HttpServletResponse response) {
    	activationCode = Random.code();//生成激活码
    	 //将验证码保存到session中
        request.getSession().setAttribute("activationCode", activationCode);
    	Session session = MailUtils.createSession(Configuration.getServer(),
                Configuration.getName(), Configuration.getPassword());
    	String from = Configuration.getEmailFrom();
        String to = user.getEmail();
        String subject = "这是来自云安全平台的注册验证邮箱";
        String content = "【云安全平台】您好，注册验证码为：" + activationCode;
        Mail mail = new Mail(from, to, subject, content);
        Map<String, Object> m = new HashMap<String, Object>();
        try {
            MailUtils.send(session, mail);
            m.put("msg", "1");//1：验证码发送成功
            //object转化为Json格式
    		JSONObject JSON = objectToJson(response, m);
    		// 把数据返回到页面
    		writeToJsp(response, JSON);
        } catch (Exception e) {
        	m.put("msg", "0");//1：验证码发送失败
        	//object转化为Json格式
     		JSONObject JSON = objectToJson(response, m);
     		try {
     			// 把数据返回到页面
				writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        }
    }
	/**
	 * 功能描述： 发送手机验证码
	 * 参数描述：  User user,HttpServletResponse response
	 * @throws Exception 
	 *		 @time 2015-1-4
	 */
	@RequestMapping(value="regist_checkSendMobile.html", method = RequestMethod.POST)
	@ResponseBody
    public void sendMobile(User user,HttpServletRequest request,HttpServletResponse response) {
		activationCode = Random.code();//生成激活码
    	 //将验证码保存到session中
        request.getSession().setAttribute("activationCode", activationCode);
        Map<String, Object> m = new HashMap<String, Object>();
        SMSUtils smsUtils = new SMSUtils();
        try {
        	//发送短信
			smsUtils.sendMessage(user.getMobile(), String.valueOf(activationCode));
			m.put("msg", "1");//1：验证码发送成功
			 //object转化为Json格式
    		JSONObject JSON = objectToJson(response, m);
    		// 把数据返回到页面
    		writeToJsp(response, JSON);
		} catch (IOException e) {
			m.put("msg", "0");//0：验证码发送失败
			e.printStackTrace();
		}
    }
	
	
	
	/**
	 * 功能描述：  检测用户填写的邮箱验证码是否正确
	 * 参数描述：  User user,HttpServletRequest request,HttpServletResponse response
	 * @throws Exception 
	 *		 @time 2015-1-4
	 */
	@RequestMapping(value="regist_checkEmailActivationCode.html", method = RequestMethod.POST)
	@ResponseBody
    public void checkEmailActivationCode(User user,HttpServletRequest request,HttpServletResponse response) {
		//从session中获取发送的验证码
		String newactivationCode =  String.valueOf(request.getSession().getAttribute("activationCode"));
    	String myactivationCode = user.getVerification_code();
    	Map<String, Object> m = new HashMap<String, Object>();
    	if(newactivationCode!=null&&newactivationCode.equals(myactivationCode) ){
    		m.put("msg", "1");//用户填写验证码正确
    		//object转化为Json格式
    		JSONObject JSON = objectToJson(response, m);
    		try {
    			// 把数据返回到页面
				writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}else{
    		m.put("msg", "0");//用户填写验证码错误
    		//object转化为Json格式
    		JSONObject JSON = objectToJson(response, m);
    		try {
    			// 把数据返回到页面
				writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
	}
	
}
