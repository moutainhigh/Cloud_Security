package com.cn.ctbri.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.ctbri.cfg.Configuration;
import com.cn.ctbri.common.NorthAPIWorker;
import com.cn.ctbri.common.WafAPIWorker;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.AttackCount;
import com.cn.ctbri.entity.LoginHistory;
import com.cn.ctbri.entity.MobileInfo;
import com.cn.ctbri.entity.Notice;
import com.cn.ctbri.entity.Partner;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.ServiceAPI;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAdvertisementService;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.INoticeService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.IPartnerService;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IServiceAPIService;
import com.cn.ctbri.service.IServiceSysService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.IUserService;
import com.cn.ctbri.util.AddressUtils;
import com.cn.ctbri.util.CommonUtil;
import com.cn.ctbri.util.DateUtils;
import com.cn.ctbri.util.IPCheck;
import com.cn.ctbri.util.LogonUtils;
import com.cn.ctbri.util.Mail;
import com.cn.ctbri.util.MailUtils;
import com.cn.ctbri.util.Random;
import com.cn.ctbri.util.SMSUtils;
import com.cn.ctbri.util.ThreeDes;
import com.cn.ctbri.util.passwordLevelUtil;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * 创 建 人  ：  于永波
 * 创建日期：  2014-12-31
 * 描        述：  用户控制层
 * 版        本：  1.0
 */

@Controller
public class UserController{
	
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
	@Autowired
    IServiceAPIService serviceAPIService;
	@Autowired
    IOrderAssetService orderAssetService;
	@Autowired
    ITaskService taskService;
	@Autowired
	IAdvertisementService adService;
	@Autowired
    IPartnerService partnerService;
	@Autowired
    IServiceSysService serviceSysService;
	

	/**
	 * 功能描述： 基本资料
	 * 参数描述： Model model,HttpServletRequest request
	 *		 @time 2015-1-13
	 */
	@RequestMapping("/userDataUI.html")
	public String userData(Model model,HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("globle_user");
		List<Partner> list = partnerService.findAllPartner();
		model.addAttribute("partnerList",list);//合作方列表
		model.addAttribute("user",user);		//传对象到页面
		return "/source/page/personalCenter/userData";
	}
	
	
	/**
	 * 功能描述： 保存修改后的基本资料
	 * 参数描述：HttpServletResponse response,HttpServletRequest request
	 *		 @time 2016-4-10
	 */
	@RequestMapping(value="/saveUserDataBate.html", method=RequestMethod.POST)
	@ResponseBody
	public void saveUserDataBate(HttpServletResponse response,HttpServletRequest request){
		Map<String, Object> m = new HashMap<String, Object>();
		User globle_user = (User) request.getSession().getAttribute("globle_user");
//		String mobile = request.getParameter("mobile");
//		String email = request.getParameter("email");
		String urlAddr = request.getParameter("urlAddr");
		String industry = request.getParameter("industry");
		String job = request.getParameter("job");
		String company = request.getParameter("company");
		String email= request.getParameter("email");
		String partner= request.getParameter("partner");
		if(email!=null&&!"".equals(email)){
		
		 String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		    Pattern regex = Pattern.compile(check);
		    Matcher matcher = regex.matcher(email);
		    if(!matcher.matches()){
				m.put("result", 2);//邮箱地址验证失败
			
				//object转化为Json格式
				JSONObject JSON = CommonUtil.objectToJson(response, m);
				try {
					// 把数据返回到页面
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		//验证公司名称
		String msg = checkCompany(company);
		if (!msg.equals("")) {
			m.put("result", 1);//公司名称验证失败
			m.put("message", msg);
			//object转化为Json格式
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		//手机号码
//		globle_user.setMobile(mobile);
		//邮箱
		globle_user.setEmail(email);
		
		try {
			//公司名称
			globle_user.setCompany(company);
			//行业
			if(!industry.equals("-1")){
				globle_user.setIndustry(industry);
			}else{
				globle_user.setIndustry("");
			}
			//职业
			if(!job.equals("-1")){
				globle_user.setJob(job);
			}else{
				globle_user.setJob("");
			}
			//合作方
			if(!partner.equals("-1")){
				globle_user.setPartner(partner);
			}else{
				globle_user.setPartner("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//add by tangxr 2016-4-10 将推送url存入服务能力系统
		if(urlAddr != null && !urlAddr.equals("")  ){
			UUID uuid = UUID.randomUUID();
			String userID = String.valueOf(globle_user.getId());
			String apikey = globle_user.getApikey();
			String randomChar = uuid.toString().replace("-", "");
			String token = "";
			try {
				token = NorthAPIWorker.login(userID, apikey, randomChar);
			} catch (Exception e) {
				m.put("message", "推送url设置失败,请稍后设置~~");
			}
			
			if(token != null && !token.equals("")){
				//推送地址 add by tangxr 2016-4-9
				globle_user.setUrlAddr(urlAddr);
				NorthAPIWorker.setCallbackAddr(urlAddr, token);
				m.put("message", true);
			}else{
				m.put("message","推送url设置失败,请稍后设置~~");	
			}
		}else{
			globle_user.setUrlAddr(urlAddr);
			m.put("message", true);
		}
		
		userService.update(globle_user);
		
		//add by tangxr 登录时将信息同步至服务能力
		String message = NorthAPIWorker.setUser(String.valueOf(globle_user.getId()), globle_user.getApikey(), globle_user.getPartner());
		
		//object转化为Json格式
		m.put("result", 0);//个人资料保存成功
		JSONObject JSON = CommonUtil.objectToJson(response, m);
		try {
			// 把数据返回到页面
			CommonUtil.writeToJsp(response, JSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		if(user.getPassword() != null){
			String password = user.getPassword();
			String passwdMD5 = DigestUtils.md5Hex(password);
			globle_user.setPassword(passwdMD5);
			userService.update(globle_user);
		}
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
//	    List<Notice> list = noticeService.findAllNotice();
	    //获取服务类型
        List servList = selfHelpOrderService.findService();
        
      //获取服务API类型
        List servAPIList = serviceAPIService.findApiPriceList();
        
        //获取首页的广告
        List adList = adService.findAdvertisementByType(0);
        
        List servSyslist = serviceSysService.findSysPriceList();
        
        //查询网页篡改个数 
//        int whorseNum = selfHelpOrderService.findLeakNum(3);
        //查询木马检测个数 
//        int pageTamperNum = selfHelpOrderService.findLeakNum(2);
        //查询漏洞个数
//        int leakNum = selfHelpOrderService.findLeakNum(1);
        //查询网页数
//        int webPageNum = selfHelpOrderService.findWebPageNum();
        //检测网页数
//        int webSite = selfHelpOrderService.findWebSite();
        //断网次数
//        int brokenNetwork = selfHelpOrderService.findBrokenNetwork();
//        m.addAttribute("whorseNum", whorseNum);
//        m.addAttribute("pageTamperNum", pageTamperNum);
//        m.addAttribute("leakNum", leakNum);
//        m.addAttribute("webPageNum", webPageNum);
//        m.addAttribute("webSite", webSite);
//        m.addAttribute("brokenNetwork", brokenNetwork);
        m.addAttribute("servList", servList);
        m.addAttribute("servAPIList", servAPIList);
        m.addAttribute("adList", adList);
        m.addAttribute("servSyslist", servSyslist);
//        m.addAttribute("noticeList", list);
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
	public String loginUI(Model m,HttpServletRequest request,HttpServletResponse response){
		m.addAttribute("flag", "dl");
		Map<String,Object> map = LogonUtils.readCookie(request, response);
		if(map!=null && map.get("result")!=null){
			String[] cookies = (String[])map.get("result");
			m.addAttribute("userName", cookies[1]);
			m.addAttribute("password", cookies[2]);
		}
		return "/source/page/regist/login";
	}
	
	/**
	 * 功能描述：  登录时检验用户名是否已经存在
	 * 参数描述：  String name,HttpServletResponse response
	 * @throws Exception 
	 *		 @time 2014-12-31
	 */
	@RequestMapping(value="login_checkName.html", method = RequestMethod.POST)
	@ResponseBody
	public void login_checkName(String name,HttpServletResponse response){
		List<User> users = userService.findUserByName(name);
		int count = 0;
		if(users.size()>0){
			count = users.size();
		}else{
			users = userService.findUserByMobile(name);
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
	 * 功能描述：  修改用户名时判断是否为cookie保存
	 * 参数描述：  String name,HttpServletResponse response
	 * @throws Exception 
	 *		 @time 2014-12-31
	 */
	@RequestMapping(value="login_checkCookie.html", method = RequestMethod.POST)
	@ResponseBody
	public void login_checkCookie(HttpServletRequest request,HttpServletResponse response){
		String userName = request.getParameter("name");
		Map<String, Object> m = new HashMap<String, Object>();
		
		//获取cookie值
		Map<String,Object> map = LogonUtils.readCookie(request, response);		
		if(map!=null && map.get("result")!=null){
			String[] cookies = (String[])map.get("result");
			if(cookies[1].equals(userName)){				
				m.put("cookie", true);
				m.put("password", cookies[2]);
				m.put("remeber", "true");
			}else{
				m.put("cookie", false);
			}
		}else{
			m.put("cookie", false);
		}

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
	 * 功能描述： 登录
	 * 参数描述：  User user,HttpServletRequest request,HttpServletResponse response
	 *		 @time 2015-1-8
	 */
	@RequestMapping(value="login.html")
	@ResponseBody
	public void login(HttpServletRequest request,HttpServletResponse response){
		String Salt="";
		 String name = request.getParameter("name");
		String password = request.getParameter("password"); 
		//boolean remeberMe = Boolean.valueOf(request.getParameter("remeberMe"));
		String md5password ="";
		boolean remeberMe = false;
		//加密
	  
        String newPass ="";
        	
		Map<String, Object> map = new HashMap<String, Object>();
		int count=0;
		int count1=0;
		try {
				
			User _user = null;
			List<User> users = userService.findUserByName(name);
			if(users!=null && users.size()>0){
				 byte[] enk = ThreeDes.hex(name);// 用户名
				 byte[] encoded = ThreeDes.encryptMode(enk, password.getBytes());
				md5password= DigestUtils.md5Hex(password);
				newPass=Base64.encode(encoded);
				count=users.size();
			}
			
			List<User> users1 = userService.findUserByMobile(name);
			if(users1!=null && users1.size()>0){
				 byte[] enk = ThreeDes.hex(users1.get(0).getName());// 用户名
				 byte[] encoded = ThreeDes.encryptMode(enk, password.getBytes());
				md5password= DigestUtils.md5Hex(password);
				newPass=Base64.encode(encoded);
				count1=users1.size();
			}
			if(count<=0&&count1<=0){
				//密码错误
				map.put("result", 2);
				JSONObject JSON = CommonUtil.objectToJson(response, map);
				try {
					// 把数据返回到页面
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
				
			}
			if(users!=null && users.size()>0){			
				
				Salt = users.get(0).getSalt();
				_user = users.get(0);
				//判断用户名对应的密码 判断当前用户密码是否已经有盐值； 无 用md5比较
				if("".equals(Salt)||Salt==null){
				//判断用户名对应的密码
				if(!md5password.equals(users.get(0).getPassword())){
						//密码错误
						map.put("result", 2);
						JSONObject JSON = CommonUtil.objectToJson(response, map);
						try {
							// 把数据返回到页面
							CommonUtil.writeToJsp(response, JSON);
						} catch (IOException e) {
							e.printStackTrace();
						}
						return;
				 }
			  }else{
				  if(!newPass.equals(users.get(0).getPassword())){
						//密码错误
						map.put("result", 2);
						JSONObject JSON = CommonUtil.objectToJson(response, map);
						try {
							// 把数据返回到页面
							CommonUtil.writeToJsp(response, JSON);
						} catch (IOException e) {
							e.printStackTrace();
						}
						return;
				  }
			  }
		   }else{
			   Salt = users1.get(0).getSalt();
				 if(users!=null && users.size()>0){
					  if("".equals(Salt)||Salt==null){
						if(!md5password.equals(users.get(0).getPassword())){
							//密码错误
							map.put("result", 2);
							JSONObject JSON = CommonUtil.objectToJson(response, map);
							try {
								// 把数据返回到页面
								CommonUtil.writeToJsp(response, JSON);
							} catch (IOException e) {
								e.printStackTrace();
							}
							return;
						}
					}
				}else{
					if(!newPass.equals(users1.get(0).getPassword())){
						//密码错误
						map.put("result", 2);
						JSONObject JSON = CommonUtil.objectToJson(response, map);
						try {
							// 把数据返回到页面
							CommonUtil.writeToJsp(response, JSON);
						} catch (IOException e) {
							e.printStackTrace();
						}
						return;
				  }
				}
					_user = users1.get(0);
			}
			
		
			if(_user.getStatus()!=1 && _user.getStatus()!=2 && _user.getStatus()!=0){
				//对不起，您的帐号已停用
				map.put("result", 1);
				JSONObject JSON = CommonUtil.objectToJson(response, map);
				try {
					// 把数据返回到页面
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			

			//添加验证码，判断验证码输入是否正确
			boolean flag = LogonUtils.checkNumber(request);
			if(!flag){
				//验证码输入有误
				map.put("result",3);
				JSONObject JSON = CommonUtil.objectToJson(response, map);
				try {
					// 把数据返回到页面
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			//如果是企业用户判断IP是否在库存地址段内
			String ip = "";
			if (request.getHeader("x-forwarded-for") == null) {
				ip = request.getRemoteAddr();
			}else{
				ip = request.getHeader("x-forwarded-for");
			}
			  //获得ip地址所在的省份
		    AddressUtils addressUtils = new AddressUtils(); 
		  
		    //ip所在的省份
		    String ipProvice = addressUtils.GetAddressByIp(ip);
		    	//addressUtils.getAddresses("ip="+ip, "utf-8"); 
			String ipStart = _user.getStartIP();
			String ipEnd = _user.getEndIP();
				
			/**记住密码功能*/
			if(remeberMe){
				LogonUtils.remeberMe(request,response,name,password);
			}			
			//盐值
			if("".equals(Salt)||Salt==null){
				RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
				Salt=randomNumberGenerator.nextBytes().toHex();
				_user.setSalt(Salt);
				_user.setPassword(newPass);
			}
			//ip地址所在的省份
			if(ipProvice!=null&&!"".equals(ipProvice)){
				_user.setIpProvice(ipProvice);
			}
			_user.setLastLoginTime(new Date());			//设置登录状态：2
			_user.setStatus(2);
			userService.update(_user);
			
			//添加到登录历史表中，用于统计分析
			LoginHistory lh = new LoginHistory();
		    String ipAddr = request.getHeader("x-forwarded-for");
			 if(ipAddr == null || ipAddr.length() == 0 || "unknown".equalsIgnoreCase(ipAddr)) {
			     ipAddr = request.getHeader("Proxy-Client-IP");
			  }
			 if(ipAddr == null || ipAddr.length() == 0 || "unknown".equalsIgnoreCase(ipAddr)) {
			    ipAddr = request.getHeader("WL-Proxy-Client-IP");
			 }
			 if(ipAddr == null || ipAddr.length() == 0 || "unknown".equalsIgnoreCase(ipAddr)) {
			    ipAddr = request.getRemoteAddr();
		     }
			lh.setUserId(_user.getId());
			lh.setUserType(_user.getType());
			lh.setLoginTime(_user.getLastLoginTime());
			lh.setIPAddr(ipAddr);
			userService.insertLoginHistory(lh);
			
			//登入key如果为空，则新增key值  add by tangxr 2016-03-31
			if(_user.getApikey()==null||_user.getApikey().equals("")){
				UUID uuid = UUID.randomUUID();
				String apikey = uuid.toString().replace("-", "");
				_user.setApikey(apikey);
				userService.update(_user);
			}
			
			if (_user.getType()==3 && !IPCheck.ipIsValid(ipStart, ipEnd, ip)) {
				map.put("msg", "登录的IP不在IP地址段范围内");
				map.put("result", 4);
				JSONObject JSON = CommonUtil.objectToJson(response, map);
				try {
					// 把数据返回到页面
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			
			//将User放置到Session中，用于这个系统的操作
			request.getSession().setAttribute("globle_user", _user);
			
			//add by tangxr 登录时将信息同步至服务能力
			String msg=NorthAPIWorker.setUser(String.valueOf(_user.getId()), _user.getApikey(), _user.getPartner());
			
			//add by tangxr 2016-3-14
			if(request.getSession().getAttribute("indexPage")!=null){
				int indexPage = (Integer) request.getSession().getAttribute("indexPage");
				if(indexPage == 1 || indexPage == 3 ){

					int serviceId = (Integer) request.getSession().getAttribute("serviceId");
					map.put("result", 5);
					map.put("serviceId", serviceId);
					map.put("indexPage", indexPage);
					
					JSONObject JSON = CommonUtil.objectToJson(response, map);
					try {
						// 把数据返回到页面
						CommonUtil.writeToJsp(response, JSON);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}else if(indexPage == 2){
					int apiId = (Integer) request.getSession().getAttribute("apiId");
					map.put("result", 6);
					map.put("apiId", apiId);
					map.put("indexPage", indexPage);
					
					JSONObject JSON = CommonUtil.objectToJson(response, map);
					try {
						// 把数据返回到页面
						CommonUtil.writeToJsp(response, JSON);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}else{
					map.put("result", 7);
					map.put("afterLoginUrl", request.getSession().getAttribute("afterLoginUrl"));
					JSONObject JSON = CommonUtil.objectToJson(response, map);
					try {
						// 把数据返回到页面
						CommonUtil.writeToJsp(response, JSON);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
			}else{
				map.put("result", 7);
				map.put("afterLoginUrl", request.getSession().getAttribute("afterLoginUrl"));
				
				JSONObject JSON = CommonUtil.objectToJson(response, map);
				try {
					// 把数据返回到页面
					CommonUtil.writeToJsp(response, JSON);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return ;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//登录异常，返回登录页面
			map.put("result", 8);
			JSONObject JSON = CommonUtil.objectToJson(response, map);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return ;
		}

	}
	
//	public void sessionHandlerByCacheMap(HttpSession session, int userId){
//		//String userid=session.getAttribute("userid").toString();
//		String userIdStr = String.valueOf(userId);
//		if(SessionListener.sessionContext.getSessionMap().get(userIdStr)!=null){
//			HttpSession userSession=(HttpSession)SessionListener.sessionContext.getSessionMap().get(userIdStr);
//			//
//			//注销在线用户(重复登录后，强制之前登录的session过期)
//			try {
//				userSession.invalidate();           
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			SessionListener.sessionContext.getSessionMap().remove(userIdStr);
//			
//			//监听器维护服务器上缓存的sessionMap :是<session.getId(),session>的键值对，
//		    //在登录后，使用userid替换session.getId()，从而使得sessionMap中维护的是<userid, session>的键值对。
//			SessionListener.sessionContext.getSessionMap().remove(session.getId()); 
//			SessionListener.sessionContext.getSessionMap().put(userIdStr,session); 
//		}
//		else
//		{
//			//监听器维护服务器上缓存的sessionMap :是<session.getId(),session>的键值对，
//		    //在登录后，使用userid替换session.getId()，从而使得sessionMap中维护的是<userid, session>的键值对。
//			//SessionListener.sessionContext.getSessionMap().get(session.getId());
//			SessionListener.sessionContext.getSessionMap().put(userIdStr,SessionListener.sessionContext.getSessionMap().get(session.getId()));
//			SessionListener.sessionContext.getSessionMap().remove(session.getId());
//		}
//}
	
		
	/**
	 * 功能描述： 用户中心页面
	 * 参数描述：  无
	 *     @time 2015-1-12
	 */
	@RequestMapping(value="userCenterUI.html")
	public String userCenterUI(HttpServletRequest request){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		List orderList = orderService.findOrderByUserId(globle_user.getId());
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
//        List servList = orderService.findByCombineOrderTrack(m);
        //是否分组标志
        int list_group = -1;
        List servList = orderService.findByUserIdAndPage(globle_user.getId(),-1,"1",null,list_group);
        int servNum = 0;
		if(servList.size()>0&&servList!=null){
			servNum = servList.size();
		}
		request.setAttribute("orderNum", orderNum);//订单总数
		request.setAttribute("servNum",servNum);//服务中
		//总告警数
//		List alarmList = alarmService.findAlarmByUserId(globle_user.getId());
		List alarmList = orderService.findByUserIdAndPage(globle_user.getId(),-1,"2",null,list_group);
		int alarmSum = 0;
		if(alarmList.size()>0&&alarmList!=null){
			alarmSum = alarmList.size();
		}
		request.setAttribute("alarmSum",alarmSum);
		
		//add by zhang_shaohua 签到按钮的表示
		User loginUser = userService.findUserById(globle_user.getId()).get(0);
		boolean signIn= false; //今日是否已签到标志位  false:今日未签到
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//取得上次签到日期
		String lastTime = null;
		if (loginUser.getLastSignInTime()!= null) {
			lastTime = sdf.format(globle_user.getLastSignInTime());
		}
		//今天的日期
		String nowTime = sdf.format(new Date());
		if (lastTime != null && lastTime.compareTo(nowTime) >=0){
			signIn = true;//今日已签到
		}
		request.setAttribute("signIn",signIn);
		return "/source/page/personalCenter/center_index";
	}
	
	
	/**
	 * 功能描述： 订单列表
	 * 参数描述：  无
	 *     @time 2016-5-8
	 */
	@RequestMapping(value="orderList.html")
	public String orderList(HttpServletRequest request){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
//		List orderList = orderService.findOrderByUserId(globle_user.getId());
		List orderList = orderService.findByUserIdAndPage(globle_user.getId(),-1,null,null,1);
		if(orderList.size()>2){
			orderList = orderList.subList(0, 2);
		}
        //根据orderId查询task表判断告警是否查看过
		
        if(orderList != null && orderList.size() > 0){
        	//个人中心显示前两条订单信息
	        for(int i = 0; i < orderList.size(); i++){
	        	HashMap<String,Object>  mapOrder = (HashMap<String,Object>)orderList.get(i);
	        	String orderListId = (String)mapOrder.get("orderListId");
	        	//根据orderListId查询订单
	        	List ol = orderService.findByOrderListId(orderListId,null); // 查询from cs_order o,cs_service s  o.orderListId = orderlistid
	        	for(int j = 0; j < ol.size(); j++){
	        		//获取对应资产 add by tangxr 2016-4-25
		        	HashMap<String,Object>  map = (HashMap<String,Object>)ol.get(j);
		        	String orderId = (String)map.get("id");
		        	String type1 = map.get("type").toString();
		        	
		        	//Map<String,Object> paramMap = new HashMap<String,Object>();
		        	//paramMap.put("orderId", orderId);
		        	//paramMap.put("type", type1);
		        	
			        List<Asset> assetList = orderAssetService.findAssetNameByOrderId(orderId);
			        map.put("assetList", assetList);
			        
			        //多资产情况下，判断已完成的 add by tangxr 2016-7-7 
//			        List<Task> tlist = taskService.findAllByOrderId(paramMap);
//					List<Task> finistlist = taskService.findFinishAlarmByOrderId(paramMap);
//					map.put("finistTask", finistlist.size());
	        	}
	        	mapOrder.put("order", ol);
	        }
        }	
        
 
        //获取当前时间
        SimpleDateFormat setDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String temp = setDateFormat.format(Calendar.getInstance().getTime());
        request.setAttribute("nowDate", temp);
		request.setAttribute("orderList", orderList);
		return "/source/page/personalCenter/userCenterList";
	}
	
	
	/**
	 * 功能描述： 退出
	 * 参数描述：  HttpServletRequest request
	 *		 @time 2015-1-12
	 */
	@RequestMapping(value="exit.html")
	public String exit(HttpServletRequest request){
		//request.getSession().removeAttribute("globle_user");
		User user = (User)request.getSession().getAttribute("globle_user");
		//设置退出状态：0
		if(user != null){
			user.setStatus(0);
			userService.update(user);
		}
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
		List<Partner> list = partnerService.findAllPartner();
        m.addAttribute("partnerList",list);//合作方列表
		m.addAttribute("flag", "zc");
		return "/source/page/regist/register";
	}
	
	
	/**
	 * 功能描述： 注册
	 * 参数描述：  User user
	 *		 @time 2014-12-31
	 */
	@RequestMapping(value="registToLogin.html", method = RequestMethod.POST)
	public String regist(User user,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0; //0:注册成功 1：用户名2:密码3:确认密码4:公司5:行业6:职业7:手机号8:验证码9:手机验证码
		String msg = "";
		try {
			//验证验证码
			if(!LogonUtils.checkNumber(request)){
				//验证码输入有误
				msg="验证码输入有误";
				result = 8;
				return null;
			}
			
			String name = user.getName();
			//验证用户名
			msg = checkUserName(name);
			if (!msg.equals("")) {
				result = 1;
				return null;
			}
			//验证密码
			String password = user.getPassword();
			msg = checkPassword(password, name);
			if (!msg.equals("")) {
				result = 2;
				return null;
			}
			//验证确认密码
			String confirmPass =  request.getParameter("confirm_password");
			if (confirmPass == null || !confirmPass.equals(password)) {
				msg = "两次输入密码不一致";
				result = 3;
				return null;
			}
			
			//验证公司
			String company = user.getCompany();
			msg = checkCompany(company);
			if (!msg.equals("")) {
				result = 4;
				return null;
			}
			
			//验证行业
			if(user.getIndustry().equals("-1")){
				user.setIndustry("");
			}
			//验证职业
			if(user.getJob().equals("-1")){
				user.setJob("");
			}
			
			//验证职业
			if(user.getPartner().equals("-1")){
				user.setPartner("");
			}
			
			//验证手机号
			String mobile = user.getMobile();
			msg = checkMobile(mobile);
			if (!msg.equals("")) {
				result = 7;
				return null;
			}
			
			//验证手机验证码
			String code = request.getParameter("verification_code");
			msg = checkActivationCode(mobile, code, 0, request);
			if (!msg.equals("")) {
				result = 9;
				return null;
			}
			//验证邮箱
			String email = request.getParameter("email");
			if(email!=null&&!"".equals(email)){
				String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			    Pattern regex = Pattern.compile(check);
			    Matcher matcher = regex.matcher(email);
			    if (!matcher.matches()) {
			    	msg = "请输入正确的邮箱地址!";
			    	result = 10;
			    	return null;
			    }
			}
			//密码 加密
			byte[] enk = ThreeDes.hex(name);// 用户名
			byte[] encoded = ThreeDes.encryptMode(enk, password.getBytes());
		    String newPass = Base64.encode(encoded);
		     //获得ip地址所在的省份
		    AddressUtils addressUtils = new AddressUtils(); 
		    String ip=request.getRemoteAddr();
		    //ip所在的省份
		    String ipProvice =addressUtils.GetAddressByIp(ip);;
		    	//addressUtils.getAddresses("ip="+ip, "utf-8"); 
		    
			RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
			user.setPassword(newPass);
			//加盐
			String salt=randomNumberGenerator.nextBytes().toHex();
			user.setSalt(salt);
			user.setStatus(1);  //用户状态(1：正常，0：停用)
			user.setType(2);	//用户类型（0：超级管理员，1：管理员，2：用户）
			user.setCreateTime(new Date());//创建时间
			user.setIp(ip);
			if(ipProvice!=null&&!"".equals(ipProvice)){
				user.setIpProvice(ipProvice);
			}
			
			//生成apikey add by tangxr 2016-4-9
			user.setApikey(UUID.randomUUID().toString().replace("-", ""));
			
			//安全币功能(注册时奖励500安全币)  add by zhangsh 2016-5-17
			user.setBalance(2000D);
			//user.setLastSignInTime(new Date());
			userService.insert(user);
			
			request.getSession().removeAttribute("regist_activationCode");//注册成功，验证码失效
			
			//注册成功时发送短信,短信模板：【安全帮】恭喜您注册成功! 系统赠送500安全币。
			SMSUtils smsUtils = new SMSUtils();
			smsUtils.sendRegistSuccessMessage(user.getMobile());
			
			return null;
		} catch(Exception e) {
			e.printStackTrace();
			result = 99;
			return null;
		}finally{
			map.put("result", result);
			map.put("msg", msg);
			JSONObject JSON = CommonUtil.objectToJson(response, map);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	private String checkUserName(String name) {
		String msg = "";
		//用户名不能为空
		if (name == null || name.equals("")) {
			msg = "用户名不能为空";
			return msg;
		}
		//支持4-20位字母/数字/下划线及其组合
		String pattern = "^[a-zA-Z0-9_]{4,20}$";
	    Pattern pat = Pattern.compile(pattern);
	    if(!(pat.matcher(name).matches())){
			msg = "支持4-20位字母/数字/下划线及其组合";
			return msg;
	    }
	    //用户名已经存在
	    List<User> userList = userService.findUserByName(name);
	    if (userList.size() <=0) {
	    	userList = userService.findUserByMobile(name);
	    }
	    
		if(userList.size()>0){
			msg="用户名已经存在";
			return msg;
		}
		return msg;
	}
	
	private String checkPassword(String password, String name){
		String msg = "";
		if (password == null || password.equals("")) {
			msg="密码不能为空";
			return msg;
		}
		
		//支持6-20位,且至少两种字符组合(大小写字母/数字/字符)
		if (password.length()<6 ||password.length()>20) {
			msg="支持6-20位,且至少两种字符组合(大小写字母/数字/字符)";
			return msg;
		}
		
		//支持6-20位,且至少两种字符组合(大小写字母/数字/字符)
		if(passwordLevelUtil.passwordLevel(password)<2) {
			msg="支持6-20位,且至少两种字符组合(大小写字母/数字/字符)";
			return msg;
		}
		
		
		if (name != null && password.equals(name)) {
			msg="用户名和密码一致，请重新修改密码!";
			return msg;
		}
		return msg;
		
	}
	
	private String checkCompany(String company) {
		String msg = "";
		if (company == null || company.equals("")) {
			msg="公司名称不能为空";
			return msg;
		}
		
		//公司名称含有非法字符 /[`~@#$%^&*()+<>"{},\\;'[\]]/im
		String pattern = "[`~@#$%^&*()+<>\"{},;'\\[\\]]";
	    Pattern pat = Pattern.compile(pattern);
	    if(pat.matcher(company).find()){
			msg = "公司名称含有非法字符";
			return msg;
	    }
	    
	    if (company.length() > 20) {
	    	msg = "公司名称长度不能超过20个字符";
			return msg;
	    }
		
		return msg;
	}
	
	private String checkMobile(String mobile){
		String msg = "";
		if (mobile == null || mobile.equals("")) {
			msg="手机号码不能为空";
			return msg;
		}
		List<User> users = userService.findUserByMobile(mobile);
		if(users.size()>0){
			msg="该手机号码已使用";
			return msg;
		}
		
		String patternM = "^1[3|5|8|7][0-9]{9}$";
	    Pattern patM = Pattern.compile(patternM);
	    if(!(patM.matcher(mobile).matches())){
	    	msg="手机号码格式不正确";
			return msg;
	    }
	    
	    return msg;
	}
	
	private String checkActivationCode(String mobile, String myactivationCode, int useFlag, HttpServletRequest request) {
		String msg = "";
		//从session中获取发送的验证码
		String newactivationCode =  "";
		long sendTime =0;  //从session中获取发送验证码的时间
		String sendMobile = null;  //从session中获取发送验证码的手机号
		switch(useFlag){
		case 0://注册
			newactivationCode = String.valueOf(request.getSession().getAttribute("regist_activationCode"));
			break;
			
		case 1://忘记密码
			newactivationCode = String.valueOf(request.getSession().getAttribute("forgetCode_activationCode"));
			break;
			
		case 2://修改密码
			newactivationCode = String.valueOf(request.getSession().getAttribute("modifyCode_activationCode"));
			break;
			
		case 3://修改手机号
			newactivationCode = String.valueOf(request.getSession().getAttribute("modifyMobile_activationCode"));
			break;
		}
		
		if (newactivationCode != null && !newactivationCode.equals("null")) {
			String checkCode[] = newactivationCode.split(",");
			sendMobile = checkCode[0];
			newactivationCode = checkCode[1];
			sendTime = Long.parseLong(checkCode[2]);
		}
		
    	Map<String, Object> m = new HashMap<String, Object>();
    	
    	if (sendMobile == null ||                     //session中没有验证码相关信息
    		!sendMobile.equals(mobile)||              //或手机号不匹配
    		System.currentTimeMillis() - sendTime >= 3*60*1000) {  //或验证码获取时间超过3分钟
    		msg = "未获取验证码或验证码失效";
    		
    	}else if(newactivationCode!=null&&newactivationCode.equals(myactivationCode) ){
    		//用户填写验证码正确
    		msg = "";
    	}else{
    		//用户填写验证码正确
    		msg = "短信验证码输入有误";
    	}
    	return msg;
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
//		if(users.size()>0){
//			count = users.size();
//		}
		if (users.size() <= 0) {
			users = userService.findUserByMobile(name);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("count", users.size());
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
	 * 功能描述：  注册时检验邮箱是否已经存在
	 * 参数描述：  String name,HttpServletResponse response
	 * @throws Exception 
	 *		 @time 2015-1-4
	 */
	@RequestMapping(value="regist_checkNumber.html", method = RequestMethod.POST)
	@ResponseBody
	public void regist_checkNumber(HttpServletRequest request,HttpServletResponse response){
		boolean flag = LogonUtils.checkNumber(request);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("flag", flag);

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
	@RequestMapping(value="checkSendMobile.html", method = RequestMethod.POST)
	@ResponseBody
    public void sendMobile(HttpServletRequest request,HttpServletResponse response) throws URISyntaxException {
		activationCode = Random.code();//生成激活码
		String userMobile = request.getParameter("mobile");
		int useFlag = Integer.parseInt(request.getParameter("useFlag"));//0:注册；1：忘记密码；2：修改密码
		
        Map<String, Object> m = new HashMap<String, Object>();
        SMSUtils smsUtils = new SMSUtils();
        try {
        	boolean flag = false;
        	//当前时间
    		Date date = new Date();
    		SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	//判断当前手机号是否已经发送过短信
        	MobileInfo mobile = userService.getMobileById(userMobile);
        	if(mobile==null){
        		MobileInfo mobileInfo = new MobileInfo();
        		mobileInfo.setMobileNumber(userMobile);
        		mobileInfo.setTimes(1);
        		mobileInfo.setSendDate(ds.format(date));
        		userService.addMobile(mobileInfo);
        		flag = true;
        	}else if(mobile.getTimes()<3){
        		Map<String, Object> m1 = new HashMap<String, Object>();
        		int times = mobile.getTimes()+1;
        		
        		//判断时间间隔
        		String dateLast = mobile.getSendDate();
        		Date lastDate=DateUtils.stringToDateNYRSFM(dateLast);
	            Date nowDate=DateUtils.stringToDateNYRSFM(ds.format(date));  
	            //时间之间的毫秒数
	            long ms = DateUtils.getMsByDays(lastDate, nowDate);
	            //间隔大于2分钟
	            if(ms>(1000*60*2)){

	        		m1.put("mobileNumber", mobile.getMobileNumber());
	        		m1.put("times", times);
	        		m1.put("sendDate", ds.format(date));
	        		userService.updateMobile(m1);
	            	flag = true;
	            }
        	}else if(mobile.getTimes()==3){
        		Map<String, Object> m2 = new HashMap<String, Object>();
        		//判断1小时以后
        		String dateLast = mobile.getSendDate();
        		Date lastDate=DateUtils.stringToDateNYRSFM(dateLast);
        		Date nowDate=DateUtils.stringToDateNYRSFM(ds.format(date));  
        		//时间之间的毫秒数
	            long ms = DateUtils.getMsByDays(lastDate, nowDate);
	            if(ms>(1000*60*60)){
	            	m2.put("mobileNumber", mobile.getMobileNumber());
	        		m2.put("times", 1);
	        		m2.put("sendDate", ds.format(date));
	        		userService.updateMobile(m2);
	            	flag = true;
	            }
        	}
        	if(flag){
        		long nowTime = System.currentTimeMillis();
        		String sessionCode = userMobile +","+ activationCode + "," + nowTime;
           	 //将验证码保存到session中
        		switch(useFlag){
        			case 0://注册
        				request.getSession().setAttribute("regist_activationCode", sessionCode);
        				smsUtils.sendMessage(userMobile, String.valueOf(activationCode));
        				break;
        			case 1://忘记密码
        				request.getSession().setAttribute("forgetCode_activationCode", sessionCode);
        				smsUtils.sendMessage_forgetCode(userMobile, String.valueOf(activationCode));
        				break;
        			case 2://修改密码
        				request.getSession().setAttribute("modifyCode_activationCode", sessionCode);
        				smsUtils.sendMessage_modifyCode(userMobile, String.valueOf(activationCode));
        				break;
        			case 3://修改手机号
        				request.getSession().setAttribute("modifyMobile_activationCode", sessionCode);
        				smsUtils.sendMessage_modifyMobile(userMobile, String.valueOf(activationCode));
        				break;
        		}
        		
    			m.put("msg", "1");//1：验证码发送成功
    			 //object转化为Json格式
        		JSONObject JSON = CommonUtil.objectToJson(response, m);
        		// 把数据返回到页面
        		CommonUtil.writeToJsp(response, JSON);
        	}else{
        		m.put("msg", "2");//1：一个手机一小时只能发送三次短信且时间间隔大于2分钟
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
	 * 功能描述：  检测用户填写的验证码是否正确
	 * 参数描述：  User user,HttpServletRequest request,HttpServletResponse response
	 * @throws Exception 
	 *		 @time 2015-1-4
	 */
	@RequestMapping(value="regist_checkActivationCode.html", method = RequestMethod.POST)
	@ResponseBody
    public void checkActivationCode(User user,HttpServletRequest request,HttpServletResponse response) {
		int useFlag = Integer.parseInt(request.getParameter("useFlag"));
		String mobile = request.getParameter("mobile");
		String myactivationCode = request.getParameter("verification_code");
		//从session中获取发送的验证码
		String newactivationCode =  "";
		long sendTime =0;  //从session中获取发送验证码的时间
		String sendMobile = null;  //从session中获取发送验证码的手机号
		switch(useFlag){
		case 0://注册
			newactivationCode = String.valueOf(request.getSession().getAttribute("regist_activationCode"));
			break;
			
		case 1://忘记密码
			newactivationCode = String.valueOf(request.getSession().getAttribute("forgetCode_activationCode"));
			break;
			
		case 2://修改密码
			newactivationCode = String.valueOf(request.getSession().getAttribute("modifyCode_activationCode"));
			break;
			
		case 3://修改手机号
			newactivationCode = String.valueOf(request.getSession().getAttribute("modifyMobile_activationCode"));
			break;
		}
		
		if (newactivationCode != null && !newactivationCode.equals("null")) {
			String checkCode[] = newactivationCode.split(",");
			sendMobile = checkCode[0];
			newactivationCode = checkCode[1];
			sendTime = Long.parseLong(checkCode[2]);
		}
		
//    	String myactivationCode = user.getVerification_code();
    	Map<String, Object> m = new HashMap<String, Object>();
    	
    	if (sendMobile == null ||                     //session中没有验证码相关信息
    		!sendMobile.equals(mobile)||              //或手机号不匹配
    		System.currentTimeMillis() - sendTime >= 3*60*1000) {  //或验证码获取时间超过3分钟
    		
    		m.put("msg", "2");//未获取验证码或验证码失效
    		//object转化为Json格式
    		JSONObject JSON = CommonUtil.objectToJson(response, m);
    		try {
    			// 把数据返回到页面
    			CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}else if(newactivationCode!=null&&newactivationCode.equals(myactivationCode) ){
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
	public String forgetPass(HttpServletRequest request,Model m){
//		String originalMobile = request.getParameter("originalMobile");
//		if(originalMobile!=null && !originalMobile.equals("")){
//			m.addAttribute("originalMobile", originalMobile);
//		}
		return "/forgetPass";
	}
	
	/**
	 * 功能描述： 修改密码
	 * 参数描述： Model m
	 *		 @time 2016-7-6
	 */
	@RequestMapping(value="modifyPassUI.html")
	public String modifyPassUI (HttpServletRequest request,Model m){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		List<User> userList = userService.findUserById(globle_user.getId());
		String originalMobile = userList.get(0).getMobile();
		
		if(originalMobile!=null && !originalMobile.equals("")){
			m.addAttribute("originalMobile", originalMobile);
		}
		return "/forgetPass";
	}
	
	/**
	 * 功能描述： 忘记密码
	 * 参数描述： Model m
	 *		 @time 2015-1-8
	 */
	@RequestMapping(value="updatePass.html", method = RequestMethod.POST)
	public String updatePass(User user,HttpServletRequest request,Model m){
//		String email = request.getParameter("eamil_ecode");
		String mobile = request.getParameter("phone_code");
		String phoneCode = request.getParameter("verification_phone");
		if (mobile==null || "".equals(mobile)||phoneCode == null || phoneCode.equals("")) {
			return "redirect:/index.html";
		}
		
		String msg;
		
		//原始密码:为空是忘记密码；不为空修改密码
		//提交成功时，短信验证码失效
		String originalMobile = request.getParameter("originalMobile");
		int useFlag; //1:忘记密码 2：修改密码
		if(originalMobile!=null && !originalMobile.equals("")){
			//修改密码
			//验证手机号
			User globle_user = (User) request.getSession().getAttribute("globle_user");
			if(globle_user == null) {
				return "redirect:/index.html";
			}
			
			List<User> userList = userService.findUserById(globle_user.getId());
			String mobileOfDB = userList.get(0).getMobile();
			if (!mobileOfDB.equals(mobile)||!mobileOfDB.equals(originalMobile)) {
				return "redirect:/index.html";
			}
			
			useFlag = 2;
		}else{
			//忘记密码
			//验证手机号
			List<User> userList = userService.findUserByMobile(mobile);
			if (userList.size() == 0) {
				return "redirect:/index.html";
			}
			useFlag = 1;
		}
		
		//验证短信验证码
		msg = checkActivationCode(mobile, phoneCode, useFlag, request);
		if (!msg.equals("")) {
			return "redirect:/index.html";
		}
		
		
		user.setMobile(mobile);
		
		m.addAttribute("User", user);
		m.addAttribute("useFlag", useFlag);
		m.addAttribute("verification_phone", activationCode);
		return "/updatePass";
	}
	
	
	/** 功能描述： 忘记密码
	 * 参数描述： Model m
	 *		 @time 2015-1-8
	 */
	@RequestMapping(value="/confirmPass.html",method=RequestMethod.POST)
	public String confirmPass(User user,HttpServletRequest request){
		try {
			String useFlagStr = request.getParameter("useFlag");
			String mobile = user.getMobile();
			int useFlag = 1;
			User userInfo;
			//验证手机号
			User globle_user = (User) request.getSession().getAttribute("globle_user");
			if ("2".equals(useFlagStr)) {  //2：修改密码
				if(globle_user == null) {
					return "/updatePassfail";
				}
				
				List<User> userList = userService.findUserById(globle_user.getId());
				userInfo = userList.get(0);
				if (!userInfo.getMobile().equals(mobile)) {
					return "/updatePassfail";
				}
				useFlag = 2;
			} else {  //1:忘记密码 
				List<User> userList = userService.findUserByMobile(mobile);
				if (userList.size() == 0) {
					return "/updatePassfail";
				}
				userInfo = userList.get(0);
			}
			
			//验证短信验证码
			String phoneCode = request.getParameter("verification_phone");
			String msg;
			msg = checkActivationCode(mobile, phoneCode, useFlag, request);
			if (!msg.equals("")) {
				return "/updatePassfail";
			}
			
			String password = user.getPassword();
			//验证密码
			msg = checkPassword(password,null);
			if (!msg.equals("")) {
				return "/updatePassfail";
			}
			
			//密码 加密
			byte[] enk = ThreeDes.hex(userInfo.getName());// 用户名
			byte[] encoded = ThreeDes.encryptMode(enk, password.getBytes());
		    String newPass = Base64.encode(encoded);
		    
			RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
			user.setPassword(newPass);
			//加盐
			String salt=randomNumberGenerator.nextBytes().toHex();
			user.setSalt(salt);
			
			user.setMobile(mobile);
			int result = userService.updatePass(user);
			
			if(useFlag == 1) {
				request.getSession().removeAttribute("forgetCode_activationCode");
			} else if(useFlag == 2) {
				request.getSession().removeAttribute("modifyCode_activationCode");
			}
			
			if (globle_user != null && userInfo.getName().equals(globle_user.getName())) {
				globle_user.setPassword(newPass);
				globle_user.setSalt(salt);
			}
			
			if(result==1){
				return "/updatePassSuccess";
			}else{
				return "/updatePassfail";
			}
		} catch(Exception e) {
			e.printStackTrace();
			return "/updatePassfail";
		}

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
  				  //解析攻击类型
  				  byte[] base64Bytes = Base64.decode(obj.getString("eventTypeBase64").toString());	
  				  String eventType = new String(base64Bytes,"UTF-8");
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
  			byte[] base64Bytes = org.apache.commons.codec.binary.Base64.decodeBase64(obj.get("eventType")
  					.toString().getBytes());
  			String eventType = new String(base64Bytes, "UTF-8");
//  			Integer typeCode = EventTypeCode.typeToCodeMap.get(eventType);
  			Integer count = (Integer) obj.get("count");
  			attackCountList.add(new AttackCount(eventType, count));
  		}
  		m.addAttribute("wafEventTypeCount",attackCountList.toString());
  		m.addAttribute("currentId", currentId);
	  	} catch (Exception e) {
//	  		e.printStackTrace();
	  		m.addAttribute("error", "服务器异常，请您耐心等待...");
	  		return "/source/page/anquanbang/anquan_state";
	  	}  	  
		return "/source/page/anquanbang/anquan_state";
	}
	
	
	/**
	 * 功能描述： 跳转网站安全帮
	 *		 @time 2016-1-23
	 */
	@RequestMapping(value="/web_anquanbang.html")
	public String webAnquanbang(Model m){
	    //获取公告
	    List<Notice> list = noticeService.findAllNotice();
	    //获取服务类型
        List servList = selfHelpOrderService.findService();
        //查询漏洞个数
        int leakNum = selfHelpOrderService.findLeakNum(1);
        //查询网页数
        int webPageNum = selfHelpOrderService.findWebPageNum();
        //检测网页数
        int webSite = selfHelpOrderService.findWebSite();
        //断网次数
        int brokenNetwork = selfHelpOrderService.findBrokenNetwork();
        
        //获取网站安全帮页面的广告
        List adList = adService.findAdvertisementByType(1);
        
        m.addAttribute("leakNum", leakNum);
        m.addAttribute("webPageNum", webPageNum);
        m.addAttribute("webSite", webSite);
        m.addAttribute("brokenNetwork", brokenNetwork);
        m.addAttribute("servList", servList);
        m.addAttribute("noticeList", list);
        m.addAttribute("adList", adList);
		return "/source/page/child/web_anquanbang";
	}
	
	/**
	 * 功能描述： 加入我们
	 * 参数描述： Model m
	 *		 @time 2016-1-25
	 */
	@RequestMapping(value="joinUs.html")
	public String joinUs(Model m){
		return "/joinUs";
	}
	
	/**
	 * 功能描述： 了解安全帮
	 * 参数描述： Model m
	 *		 @time 2016-2-25
	 */
	@RequestMapping(value="knowUs.html")
	public String knowUs(Model m){
		return "/knowUs";
	}
	
	/**
	 * 功能描述： 跳转api安全帮
	 *		 @time 2016-4-18
	 */
	@RequestMapping(value="/api_anquanbang.html")
	public String apiAnquanbang(Model m){
	    //获取公告
	    List<Notice> list = noticeService.findAllNotice();
	    //获取服务类型
        List servList = serviceAPIService.findApiPriceList();
        //查询漏洞个数
        int leakNum = selfHelpOrderService.findLeakNum(1);
        //查询网页数
        int webPageNum = selfHelpOrderService.findWebPageNum();
        //检测网页数
        int webSite = selfHelpOrderService.findWebSite();
        //断网次数
        int brokenNetwork = selfHelpOrderService.findBrokenNetwork();
        //获取安全能力API页面的广告
        List adList = adService.findAdvertisementByType(2);
        
        m.addAttribute("leakNum", leakNum);
        m.addAttribute("webPageNum", webPageNum);
        m.addAttribute("webSite", webSite);
        m.addAttribute("brokenNetwork", brokenNetwork);
        m.addAttribute("servList", servList);
        m.addAttribute("noticeList", list);
        m.addAttribute("adList", adList);
		return "/source/page/child/api_anquanbang";
	}
	
	/**
	 * 功能描述： 头部商品分类的显示
	 * 参数描述：Model m
	 *		 @time 2016-5-6
	 */
	@RequestMapping("/category.html")
	public String getCategory(Model m){
		//获取服务类型
        List servList = selfHelpOrderService.findService();
        //获取服务API类型
        List<ServiceAPI> servAPIList = serviceAPIService.findServiceAPI();
        List<Serv> systemServList = selfHelpOrderService.findServiceByParent(3);
        m.addAttribute("servList", servList);
        m.addAttribute("servAPIList", servAPIList);
        m.addAttribute("systemServList", systemServList);
        return "/category";
	} 
	
	/**
	 * 功能描述： 修改手机号码
	 * 参数描述： Model m
	 *		 @time 2016-6-29
	 */
	@RequestMapping(value="updateMobile.html")
	public String updateMobile(HttpServletRequest request,Model m){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		List<User> userList = userService.findUserById(globle_user.getId());
		String originalMobile = userList.get(0).getMobile();
		if(originalMobile!=null && !originalMobile.equals("")){
			m.addAttribute("originalMobile", originalMobile);
		}
		return "/updateMobile";
	}
	
	/** 功能描述： 修改手机号码
	 * 参数描述： User user
	 *		 @time 2016-6-29
	 */
	@RequestMapping(value="/confirmMobile.html",method=RequestMethod.POST)
	public String confirmMobile(HttpServletRequest request){
		boolean success = false;
		try {
			User globle_user = (User) request.getSession().getAttribute("globle_user");
			String mobile = request.getParameter("mobile");
			//验证手机号
			if (!globle_user.getMobile().equals(mobile)) {
				if(!checkMobile(mobile).equals("")) {
					request.setAttribute("success", false);
					return "/updateMobileFinish";
				}
			}
			
			String code = request.getParameter("verification_phone");
			//验证验证码
			if(!checkActivationCode(mobile, code, 3, request).equals("")) {
				request.setAttribute("success", false);
				return "/updateMobileFinish";
			}
			
			User user = new User();
			user.setMobile(mobile);
			user.setId(globle_user.getId());
			int result = userService.updateUserMobile(user);
			if(result==1){
				globle_user.setMobile(mobile);
				request.getSession().removeAttribute("modifyMobile_activationCode");
				success = true;
			}
			
			request.setAttribute("success", success);
			
		} catch (Exception e) {
			request.setAttribute("success", false);
		}
		return "/updateMobileFinish";
	}
	/**
	 * 功能描述： 更新ip地址所在的省份
	 * 参数描述：  无
	 *     @time 2016-8-11
	 */
	@RequestMapping(value="updateUserProvice.html")
	public void updateUserProvice(HttpServletRequest request) throws Exception{
		User user= new User();
		List<User> userList = userService.findAll(user);
		if(userList!=null&&userList.size()>0){
		   for(int i=0;i<userList.size();i++){
			   User userInfo =userList.get(i); 
			   String ipProvince = userInfo.getIpProvice();
			   String ip = userInfo.getIp();
				  //获得ip地址所在的省份
			    AddressUtils addressUtils = new AddressUtils(); 
			  
			    //ip所在的省份
			    if(ip!=null&&!"".equals(ip)){
			    	System.out.println("i=="+i);
			    	System.out.println("ip="+ip);
			    	String ipProviceVal=addressUtils.GetAddressByIp(ip);
			    	System.out.println("ipProviceVal=="+ipProviceVal);
					   if(ipProvince==null||"".equals(ipProvince)){
						   if(ipProviceVal!=null&&!"".equals(ipProviceVal)){
							   userInfo.setIpProvice(ipProviceVal);
							   userService.update(userInfo); 
						   }
						  
					   }
			    }
			   
		   }	
		}
		System.out.println("====已完成=====");
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
		User user = (User)request.getSession().getAttribute("globle_user");
		
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
	
	/**
	 * 功能描述： 跳转系统安全帮
	 *		 @time 2017-2-8
	 */
	@RequestMapping(value="/system_anquanbang.html")
	public String systemAnquanbang(Model m){
	    //获取公告
	    List<Notice> list = noticeService.findAllNotice();
	    //获取服务类型
        List servList = serviceSysService.findSysPriceList();//需要修改为system
        //查询漏洞个数
        int leakNum = selfHelpOrderService.findLeakNum(1);
        //查询网页数
        int webPageNum = selfHelpOrderService.findWebPageNum();
        //检测网页数
        int webSite = selfHelpOrderService.findWebSite();
        //断网次数
        int brokenNetwork = selfHelpOrderService.findBrokenNetwork();
        //获取安全能力API页面的广告
        List adList = adService.findAdvertisementByType(2);
        
        System.out.println(servList);
        m.addAttribute("leakNum", leakNum);
        m.addAttribute("webPageNum", webPageNum);
        m.addAttribute("webSite", webSite);
        m.addAttribute("brokenNetwork", brokenNetwork);
        m.addAttribute("servList", servList);
        m.addAttribute("noticeList", list);
        m.addAttribute("adList", adList);
		return "/source/page/child/system_anquanbang";
	}
	
	
	/**
	 * 功能描述： 系统安全帮活动详情页面
	 * 参数描述： Model m
	 *		 @time 2017-6-27
	 */
	@RequestMapping(value="system_activity.html")
	public String system_activity(Model m){
		return "/system_fuli";
	}
	
}