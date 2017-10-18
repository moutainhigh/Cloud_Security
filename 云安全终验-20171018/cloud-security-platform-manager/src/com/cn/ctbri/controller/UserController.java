package com.cn.ctbri.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.cfg.Configuration;
import com.cn.ctbri.common.WafAPIWorker;
import com.cn.ctbri.entity.AttackCount;
import com.cn.ctbri.entity.MobileInfo;
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
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

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
//		String userName = user.getName();
//		globle_user.setName(userName);
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
	 * 功能描述： 保存修改后的密码
	 * 参数描述：User user,Model model,HttpServletRequest request
	 *		 @time 2015-6-18
	 */
	@RequestMapping("/saveUserPassword.html")
	public String saveUserPassword(User user,Model model,HttpServletRequest request){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		//密码
		String password = user.getPassword();
		String passwdMD5 = DigestUtils.md5Hex(password);
		globle_user.setPassword(passwdMD5);
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
        //查询网页篡改个数 
//        int whorseNum = selfHelpOrderService.findLeakNum(3);
        //查询木马检测个数 
//        int pageTamperNum = selfHelpOrderService.findLeakNum(2);
        //查询漏洞个数 
        int leakNum = selfHelpOrderService.findLeakNum(1);
        //查询网页数
        int webPageNum = selfHelpOrderService.findWebPageNum();
        //检测网站数
        int webSite = selfHelpOrderService.findWebSite();
        //断网次数
        int brokenNetwork = selfHelpOrderService.findBrokenNetwork();
//        m.put("whorseNum", whorseNum);
//        m.put("pageTamperNum", pageTamperNum);
        m.put("leakNum", leakNum);
        m.put("webPageNum", webPageNum);
        m.put("webSite", webSite);
        m.put("brokenNetwork", brokenNetwork);
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
        //查询网页篡改个数 
//        int whorseNum = selfHelpOrderService.findLeakNum(3);
        //查询木马检测个数 
//        int pageTamperNum = selfHelpOrderService.findLeakNum(2);
        //查询漏洞个数
        int leakNum = selfHelpOrderService.findLeakNum(1);
        //查询网页数
        int webPageNum = selfHelpOrderService.findWebPageNum();
        //检测网页数
        int webSite = selfHelpOrderService.findWebSite();
        //断网次数
        int brokenNetwork = selfHelpOrderService.findBrokenNetwork();
//        m.addAttribute("whorseNum", whorseNum);
//        m.addAttribute("pageTamperNum", pageTamperNum);
        m.addAttribute("leakNum", leakNum);
        m.addAttribute("webPageNum", webPageNum);
        m.addAttribute("webSite", webSite);
        m.addAttribute("brokenNetwork", brokenNetwork);
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
				request.setAttribute("msg", "用户名或密码错误");
				return "/source/page/regist/regist";//跳转到登录页面
			}
			if(_user.getStatus()!=1){
				request.setAttribute("msg", "对不起，您的帐号已停用");
				return "/source/page/regist/regist";//跳转到登录页面
			}
		}else{
			request.setAttribute("msg", "用户名或密码错误");
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
			List alarmList = alarmService.findAlarmByUserId(globle_user.getId());
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
		//request.getSession().removeAttribute("globle_user");
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
	public String regist(User user,HttpServletRequest request){
		String name = user.getName();
		String password = user.getPassword();
		//add by tang 2015-06-01
		//验证用户名
		String pattern = "^[a-zA-Z0-9_]{4,20}$";
	    Pattern pat = Pattern.compile(pattern);
	    Matcher m = null;
	    if(name!=null&&!"".equals(name)){
	        m = pat.matcher(name);
	    }
	    
	    //验证手机号
	    String patternM = "^1[3|5|8|7][0-9]{9}$";
	    Pattern patM = Pattern.compile(patternM);
	    Matcher mMobile = null;
	    boolean mb = false;
	    if(user.getMobile()!=null&&!"".equals(user.getMobile())){
	        mMobile = patM.matcher(user.getMobile());
	        mb = mMobile.matches();
	    }
	    //验证邮箱
//	    String patternE = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.[a-zA-Z0-9_-])$";
	    String patternE = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	    Pattern patE = Pattern.compile(patternE);
	    Matcher mEmail = null;
	    boolean me = false;
	    if(user.getEmail()!=null&&!"".equals(user.getEmail())){
	        mEmail = patE.matcher(user.getEmail());
	        me = mEmail.matches();
	    }
	    
		if(name!=null&&!"".equals(name)&&m.matches()&&password!=null&&!"".equals(password)&&password.length()>=6&&password.length()<=20&&(mb||me)&&user.getVerification_code()!=null&&!"".equals(user.getVerification_code())){
			//按用用户名、邮箱、手机号码组合查询用户,防止刷页面
			List<User> users = userService.findUserByCombine(user);
			if(!(users.size()>0)){
				String passwdMD5 = DigestUtils.md5Hex(password);
				user.setPassword(passwdMD5);
				user.setStatus(1);  //用户状态(1：正常，0：停用)
				user.setType(2);	//用户类型（0：超级管理员，1：管理员，2：用户）
				user.setCreateTime(new Date());//创建时间
				user.setIp(request.getRemoteAddr());
				userService.insert(user);
			}
			//return "/source/page/regist/registToLogin";
			return "redirect:/loginUI.html";
		}else{
			request.setAttribute("errorMsg", "注册异常!");
        	return "/source/error/errorMsg";
		}
		
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
	public void regist_checkName(String name,String mark,HttpServletResponse response){
		List<User> users = userService.findUserByName(name);
		int count = 0;
		if(users.size()>0){
			//mark作为修改标识，修改用户名未变情况。
			if(mark!=null){
				count = 0;
			}else{
				count = users.size();
			}
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
	 * 功能描述： 检验用户名密码是否正确
	 * 参数描述：  String name,HttpServletResponse response
	 * @throws Exception 
	 *		 @time 2014-12-31
	 */
	@RequestMapping(value="regist_checkPwd.html", method = RequestMethod.POST)
	@ResponseBody
	public void regist_checkPwd(String name,String opassword,HttpServletResponse response){
		List<User> users = userService.findUserByName(name);
		boolean count = false;
		//判断用户名密码输入是否正确
		User _user = null;
		if(users.size()>0){
			_user = users.get(0);
			//从页面上获取密码和User对象中存放的密码，进行匹配，如果不一致，提示【密码输入有误】
			String md5password = DigestUtils.md5Hex(opassword);
			if(!md5password.equals(_user.getPassword())){
				count = true;
			}
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
	 * @throws URISyntaxException 
	 * @throws Exception 
	 *		 @time 2015-1-4
	 */
	@RequestMapping(value="regist_checkSendMobile.html", method = RequestMethod.POST)
	@ResponseBody
    public void sendMobile(User user,HttpServletRequest request,HttpServletResponse response) throws URISyntaxException {
		activationCode = Random.code();//生成激活码
    	 //将验证码保存到session中
        request.getSession().setAttribute("activationCode", activationCode);
        Map<String, Object> m = new HashMap<String, Object>();
        SMSUtils smsUtils = new SMSUtils();
        try {
        	boolean flag = false;
        	//判断当前手机号是否已经发送过短信
        	MobileInfo mobile = userService.getMobileById(user.getMobile());
        	if(mobile==null){
        		MobileInfo mobileInfo = new MobileInfo();
        		mobileInfo.setMobileNumber(user.getMobile());
        		mobileInfo.setTimes(1);
        		Date date = new Date();
        		SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");
        		mobileInfo.setSendDate(ds.format(date));
        		userService.addMobile(mobileInfo);
        		flag = true;
        	}else if(mobile.getTimes()<3){
        		int times = mobile.getTimes()+1;
        		mobile.setTimes(times);
        		userService.updateMobile(times);
        		flag = true;
        	}
        	if(flag){
        		//发送短信
    			smsUtils.sendMessage(user.getMobile(), String.valueOf(activationCode));
    			m.put("msg", "1");//1：验证码发送成功
    			 //object转化为Json格式
        		JSONObject JSON = CommonUtil.objectToJson(response, m);
        		// 把数据返回到页面
        		CommonUtil.writeToJsp(response, JSON);
        	}else{
        		m.put("msg", "2");//1：验证码发送成功
        		JSONObject JSON = CommonUtil.objectToJson(response, m);
        		// 把数据返回到页面
        		CommonUtil.writeToJsp(response, JSON);
        	}
        
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
	/**
	 * 功能描述： 在线帮助
	 * 参数描述：  User user
	 *		 @time 2014-12-31
	 */
	@RequestMapping(value="aider.html")
	public String OnlineHelp(){
		
		return "/source/page/onLineHelp/onLineHelp";
		//return "redirect:/loginUI.html";
	}
	/**
	 * 功能描述： 在线帮助
	 * 参数描述：  User user
	 *		 @time 2014-12-31
	 */
	@RequestMapping(value="loginHelp.html")
	public String loginHelp(){
		
		return "/source/page/onLineHelp/loginHelp";
		//return "redirect:/loginUI.html";
	}
	/**
	 * 功能描述： 在线帮助
	 * 参数描述：  User user
	 *		 @time 2014-12-31
	 */
	@RequestMapping(value="userCenterHelp.html")
	public String userCenterHelp(){
		
		return "/source/page/onLineHelp/userCenterHelp";
		//return "redirect:/loginUI.html";
	}
	/**
	 * 功能描述： 在线帮助
	 * 参数描述：  User user
	 *		 @time 2014-12-31
	 */
	@RequestMapping(value="OrderHelp.html")
	public String OrderHelp(){
		
		return "/source/page/onLineHelp/OrderHelp";
		//return "redirect:/loginUI.html";
	}
	/**
	 * 功能描述： 在线帮助
	 * 参数描述：  User user
	 *		 @time 2014-12-31
	 */
	@RequestMapping(value="webQuestions.html")
	public String webQuestions(){
		
		return "/source/page/onLineHelp/webQuestions";
		//return "redirect:/loginUI.html";
	}
	/**
	 * 功能描述： 忘记密码
	 * 参数描述： Model m
	 *		 @time 2015-1-8
	 */
	@RequestMapping(value="forgetPass.html")
	public String forgetPass(Model m){
		return "/forgetPass";
	}
	/**
	 * 功能描述： 忘记密码
	 * 参数描述： Model m
	 *		 @time 2015-1-8
	 */
	@RequestMapping(value="updatePass.html")
	public String updatePass(User user,HttpServletRequest request,Model m){
		String email = request.getParameter("eamil_ecode");
		String mobile = request.getParameter("phone_code");
		if(email!=null&&!"".equals(email)){
			user.setEmail(email);
		}
		if(mobile!=null&&!"".equals(mobile)){
			user.setMobile(mobile);
		}
		m.addAttribute("User", user);
		return "/updatePass";
	}
	
	
	/** 功能描述： 忘记密码
	 * 参数描述： Model m
	 *		 @time 2015-1-8
	 */
	@RequestMapping(value="/confirmPass.html")
	public String confirmPass(User user){
		String password = user.getPassword();
		String passwdMD5 = DigestUtils.md5Hex(password);
		user.setPassword(passwdMD5);
		userService.updatePass(user);
	
	return "redirect:/loginUI.html";
	}
	
	/**
	 * 功能描述：判断邮箱是否已经注册
	 * 参数描述：  
	 * @throws Exception 
	 *		 @time 2015-1-4
	 */
	@RequestMapping(value="isExitEmail.html", method = RequestMethod.POST)
	@ResponseBody
    public void isExitEmail(User user,HttpServletRequest request,HttpServletResponse response) {

    	String email = user.getEmail();
    	 List list = userService.findUserByEmail(email);
    	Map<String, Object> m = new HashMap<String, Object>();
    	if(list!=null&&list.size()>0){
    		m.put("isExit", "1");//用户填写验证码正确
    		//object转化为Json格式
    		JSONObject JSON = CommonUtil.objectToJson(response, m);
    		try {
    			// 把数据返回到页面
    			CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}else{
    		m.put("isExit", "0");//用户填写验证码错误
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
	 * 功能描述：判断邮箱是否已经注册
	 * 参数描述：  
	 * @throws Exception 
	 *		 @time 2015-1-4
	 */
	@RequestMapping(value="isExitPhone.html", method = RequestMethod.POST)
	@ResponseBody
    public void isExitPhone(User user,HttpServletRequest request,HttpServletResponse response) {

    	String mobile = user.getMobile();
    	 List list = userService.findUserByMobile(mobile);
    	Map<String, Object> m = new HashMap<String, Object>();
    	if(list!=null&&list.size()>0){
    		m.put("isExit", "1");//用户填写验证码正确
    		//object转化为Json格式
    		JSONObject JSON = CommonUtil.objectToJson(response, m);
    		try {
    			// 把数据返回到页面
    			CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}else{
    		m.put("isExit", "0");//用户填写验证码错误
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
	 * 功能描述： 跳转动态感知
	 * 参数描述： Model m
	 *		 @time 2015-1-8
	 */
	@RequestMapping(value="/sa_anquanbang.html")
	public String saAnquanbang(Model m){
	  	try {	        
	        JSONArray jsonArray;
	  	    int wafAlarmLevel = 0;
  		  
  		  //waf结果集	
  		  int countAll = 0;
  		  String wafResult = WafAPIWorker.getEventTypeCountByDay("7");
  		  jsonArray = JSONArray.fromObject(wafResult);
  		  if(jsonArray!=null && jsonArray.size()>0){
  			  for(int i = 0; i < jsonArray.size(); i++){
  				  JSONObject obj=(JSONObject) jsonArray.get(i);
  				  String base64=obj.getString("eventTypeBase64").toString();
  				  if(base64!=null&&!"".equals(base64)){
  					 //解析攻击类型
  	  				  byte[] base64Bytes = Base64.decode(base64);	
  	  			     String eventType = new String(base64Bytes,"UTF-8");
  				  }
  				  //解析count
  				  JSONArray array = obj.getJSONArray("list");
  				  
  				  if(array!=null && array.size()>0){
  					  for(int j = 0; j < array.size(); j++){
  						  JSONObject jsonTemp =(JSONObject) array.get(j);
  						  countAll += jsonTemp.getInt("count");
  					  }
  				  }

  				 
  			  }
  		  }
  		  if(countAll>100){
  			  wafAlarmLevel = 4;
  		  }else if(countAll>50){
  			  wafAlarmLevel = 3;
  		  }else if(countAll>30){
  			  wafAlarmLevel = 2;
  		  }else if(countAll>10){
  			  wafAlarmLevel = 1;
  		  }
  	  	  m.addAttribute("wafAlarmLevel",wafAlarmLevel);
  		WafAPIWorker worker = new WafAPIWorker();
  		String texts = worker.getEventTypeCountInTimeCurrent(500);
  		JSONObject json=JSONObject.fromObject(texts);
  		JSONArray array = json.getJSONArray("eventTypeCountList");
  		long currentId=json.getLong("currentId");
  		List<AttackCount> attackCountList = new ArrayList<AttackCount>();
  		for (int i = 0; i < array.size(); i++) {
  			JSONObject obj = (JSONObject) array.get(i);
  			String base64=obj.get("eventType").toString();
  			/*System.out.println(base64);
  			if(base64!=null&&!"".equals(base64)){
  	//			byte[] base64Bytes = org.apache.commons.codec.binary.Base64.decodeBase64(base64.getBytes());
  	  			String eventType = new String(base64.getBytes(), "UTF-8");*/
  	  		   Integer count = (Integer) obj.get("count");
  			   attackCountList.add(new AttackCount(base64, count));
  			//}
  		
//  			Integer typeCode = EventTypeCode.typeToCodeMap.get(eventType);
  		
  		}
  		m.addAttribute("wafEventTypeCount",attackCountList.toString());
  		m.addAttribute("currentId", currentId);
	  	} catch (UnsupportedEncodingException e) {
	  		e.printStackTrace();
	  	}  	  

		return "/source/adminPage/anquanbang/anquan_state";
	}
	
	/**
	 * 功能描述：获取登录用户
	 * 参数描述：  
	 * @throws Exception 
	 */
	@RequestMapping(value="getLoginUser.html", method = RequestMethod.POST)
	@ResponseBody
	public void getLoginUser(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> m = new HashMap<String, Object>();
		User user = (User)request.getSession().getAttribute("admin_user");
		
		m.put("LoginUser", user);
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
