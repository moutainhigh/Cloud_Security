package com.cn.ctbri.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.cfg.Configuration;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Notice;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.INoticeService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IUserService;
import com.cn.ctbri.util.CommonUtil;
import com.cn.ctbri.util.LogonUtils;
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
	@Autowired
    ISelfHelpOrderService selfHelpOrderService;
	@Autowired
	IAlarmService alarmService;
	@Autowired
	IOrderService orderService;
	@Autowired
    INoticeService noticeService;
	/**
	 * 功能描述： 基本资料
	 * 参数描述： Model model,HttpServletRequest request
	 *		 @time 2015-1-13
	 */
	@RequestMapping("/userDataUI.html")
	public String userData(Model model,HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("globle_user");
		model.addAttribute("user",user);		//传对象到页面
		return "/source/page/userCenter/userData";
	}
	
	/**
	 * 功能描述： 保存修改后的基本资料
	 * 参数描述：User user,Model model,HttpServletRequest request
	 *		 @time 2015-1-13
	 */
	@RequestMapping("/saveUserData.html")
	public String saveUserData(User user,Model model,HttpServletRequest request){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		//用户名
		String userName = user.getName();
		globle_user.setName(userName);
		//手机号码
		String mobile = user.getMobile();
		globle_user.setMobile(mobile);
		//邮箱
		String email = user.getEmail();
		globle_user.setEmail(email);
		userService.update(globle_user);
		return "redirect:/userDataUI.html";
	}
	
	/**
     * 功能描述： 首页数据查询
     * 参数描述：  无
     *     @time 2015-3-9
     */
    @RequestMapping(value="/getNum.html")
    @ResponseBody
    public void getNum(HttpServletResponse response,HttpServletRequest request){
        Map<String, Object> m = new HashMap<String, Object>();
        //查询漏洞个数 
        int leakNum = selfHelpOrderService.findLeakNum(1);
        //查询网页数
        int webPageNum = selfHelpOrderService.findWebPageNum();
        m.put("leakNum", leakNum);
        m.put("webPageNum", webPageNum);
        //object转化为Json格式
        JSONObject JSON = CommonUtil.objectToJson(response, m);
        try {
            // 把数据返回到页面
            CommonUtil.writeToJsp(response, JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * 功能描述： 首页
	 * 参数描述：  Model m
	 *		 @time 2015-1-12
	 */
	@RequestMapping(value="index.html")
	public String index(Model m){
	    //获取公告
	    List<Notice> list = noticeService.findAllNotice();
	    //获取服务类型
        List<Serv> servList = selfHelpOrderService.findService();
        //查询漏洞个数
        int leakNum = selfHelpOrderService.findLeakNum(1);
        //查询网页数
        int webPageNum = selfHelpOrderService.findWebPageNum();
        m.addAttribute("leakNum", leakNum);
        m.addAttribute("webPageNum", webPageNum);
        m.addAttribute("servList", servList);
        m.addAttribute("noticeList", list);
		return "/main";
	}
	/**
	 * 功能描述： 登录页面
	 * 参数描述： Model m
	 *		 @time 2015-1-8
	 */
	@RequestMapping(value="toLoginUI.html")
	public String toLoginUI(Model m){
		return "/source/error/notLogin";
	}
	
	/**
	 * 功能描述： 登录页面
	 * 参数描述： Model m
	 *		 @time 2015-1-8
	 */
	@RequestMapping(value="loginUI.html")
	public String loginUI(Model m){
		m.addAttribute("flag", "dl");
		return "/source/page/regist/regist";
	}
	/**
	 * 功能描述： 登录
	 * 参数描述：  User user,HttpServletRequest request,HttpServletResponse response
	 *		 @time 2015-1-8
	 */
	@RequestMapping(value="login.html")
	public String login(User user,HttpServletRequest request,HttpServletResponse response){
		//添加验证码，判断验证码输入是否正确
		boolean flag = LogonUtils.checkNumber(request);
		if(!flag){
			request.setAttribute("msg", "验证码输入有误");//向前台页面传值
			return "/source/page/regist/regist";
		}
		
		//判断用户名密码输入是否正确
		User _user = null;
		String name = user.getName().trim();
		String password = user.getPassword().trim();
		List<User> users = userService.findUserByName(name);
		if(users.size()>0){
			_user = users.get(0);
			//从页面上获取密码和User对象中存放的密码，进行匹配，如果不一致，提示【密码输入有误】
			String md5password = DigestUtils.md5Hex(password);
			if(!md5password.equals(_user.getPassword())){
				request.setAttribute("msg", "密码输入有误");
				return "/source/page/regist/regist";//跳转到登录页面
			}
			if(_user.getStatus()!=1){
				request.setAttribute("msg", "对不起，您的帐号已停用");
				return "/source/page/regist/regist";//跳转到登录页面
			}
		}else{
			request.setAttribute("msg", "用户名输入有误");
			return "/source/page/regist/regist";//跳转到登录页面
		}
		/**记住密码功能*/
		LogonUtils.remeberMe(request,response,name,password);
		//将User放置到Session中，用于这个系统的操作
		request.getSession().setAttribute("globle_user", _user);
		return "redirect:/userCenterUI.html";
	}
	
	 /**
		 * 功能描述： 用户中心页面
		 * 参数描述：  无
		 *     @time 2015-1-12
		 */
		@RequestMapping(value="userCenterUI.html")
		public String userCenterUI(HttpServletRequest request){
			User globle_user = (User) request.getSession().getAttribute("globle_user");
			//根据用户id查询订单表
			List<Order> orderList = orderService.findOrderByUserId(globle_user.getId());
			int orderNum = 0;
			if(orderList.size()>0&&orderList!=null){
				orderNum = orderList.size();
			}
			//根据用户id查询服务中订单表在开始时间和结束时间中间
	        Map<String, Object> m = new HashMap<String, Object>();
	        m.put("userId", globle_user.getId());
	        m.put("state", 1);
	        SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        /* 時：分：秒  HH:mm:ss  HH : 23小時制 (0-23)
	                                 kk : 24小時制 (1-24)
	                                 hh : 12小時制 (1-12)
	                                 KK : 11小時制 (0-11)*/
	        String temp = setDateFormat.format(Calendar.getInstance().getTime());
	        m.put("currentDate", temp);
	        List servList = orderService.findByCombineOrderTrack(m);
			int servNum = 0;
			if(servList.size()>0&&servList!=null){
				servNum = servList.size();
			}
			request.setAttribute("orderNum", orderNum);//订单总数
			request.setAttribute("servNum",servNum);//服务中
			//总告警数
			List<Alarm> alarmList = alarmService.findAlarmByUserId(globle_user.getId());
			int alarmSum = 0;
			if(alarmList.size()>0&&alarmList!=null){
				alarmSum = alarmList.size();
			}
			request.setAttribute("alarmSum",alarmSum);
			return "/source/page/userCenter/userCenter";
		}
	
	
	/**
	 * 功能描述： 退出
	 * 参数描述：  HttpServletRequest request
	 *		 @time 2015-1-12
	 */
	@RequestMapping(value="exit.html")
	public String exit(HttpServletRequest request){
   		request.getSession().invalidate();
   		return "redirect:/loginUI.html";
	}
	
	 /**
	 * 功能描述： 注册页面
	 * 参数描述：  Model m
	 *		 @time 2014-12-31
	 */
	@RequestMapping(value="registUI.html")
	public String registUI(Model m){
		m.addAttribute("flag", "zc");
		return "/source/page/regist/regist";
	}
	
	
	/**
	 * 功能描述： 注册
	 * 参数描述：  User user
	 *		 @time 2014-12-31
	 */
	@RequestMapping(value="registToLogin.html")
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
				user.setCreateTime(new Date());//创建时间
				userService.insert(user);
			}
		}
		return "/source/page/regist/registToLogin";
		//return "redirect:/loginUI.html";
	}
	
	/**
	 * 功能描述： 注册成功跳转到登录页面
	 * 参数描述： Model m
	 *		 @time 2015-1-8
	 */
	@RequestMapping(value="regist.html")
	public String registToLogin(){
		return "redirect:/loginUI.html";
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
		JSONObject JSON = CommonUtil.objectToJson(response, m);
		try {
			// 把数据返回到页面
			CommonUtil.writeToJsp(response, JSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		JSONObject JSON = CommonUtil.objectToJson(response, m);
		try {
			// 把数据返回到页面
			CommonUtil.writeToJsp(response, JSON);
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
		JSONObject JSON = CommonUtil.objectToJson(response, m);
		try {
			// 把数据返回到页面
			CommonUtil.writeToJsp(response, JSON);
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
    		JSONObject JSON = CommonUtil.objectToJson(response, m);
    		// 把数据返回到页面
    		CommonUtil.writeToJsp(response, JSON);
        } catch (Exception e) {
        	m.put("msg", "0");//1：验证码发送失败
        	//object转化为Json格式
     		JSONObject JSON = CommonUtil.objectToJson(response, m);
     		try {
     			// 把数据返回到页面
     			CommonUtil.writeToJsp(response, JSON);
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
    		JSONObject JSON = CommonUtil.objectToJson(response, m);
    		// 把数据返回到页面
    		CommonUtil.writeToJsp(response, JSON);
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
    		JSONObject JSON = CommonUtil.objectToJson(response, m);
    		try {
    			// 把数据返回到页面
    			CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}else{
    		m.put("msg", "0");//用户填写验证码错误
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
