package com.cn.ctbri.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import se.akerfeldt.com.google.gson.Gson;
import se.akerfeldt.com.google.gson.JsonArray;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IUserService;
import com.cn.ctbri.util.CommonUtil;
import com.cn.ctbri.util.LogonUtils;


/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-29
 * 描        述：  后台用户管理
 * 版        本：  1.0
 */
@Controller
public class AdminUserController {
	
	@Autowired
	IUserService userService;
	@Autowired
	IAssetService assetService;
	@Autowired
	IOrderService orderService;
	@Autowired
	ISelfHelpOrderService selfHelpOrderService;
	/**
	 * 功能描述：后台登录页面 
	 * 参数描述：无
	 *		 @time 2015-1-29
	 */
	@RequestMapping("/admin.html")
	public String admin(){
		return "/source/adminPage/adminLogin/adminLogin";
	}
	/**
	 * 功能描述：后台登录
	 * 参数描述： Model model,HttpServletRequest request
	 *		 @time 2015-2-2
	 */
	@RequestMapping("/adminLogin.html")
	public String adminLogin(User user,HttpServletRequest request,HttpServletResponse response){
		//添加验证码，判断验证码输入是否正确
		boolean flag = LogonUtils.checkNumberAdmin(request);
		if(!flag){
			request.setAttribute("msg", "验证码输入有误");//向前台页面传值
			return "/source/adminPage/adminLogin/adminLogin";
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
				return "/source/adminPage/adminLogin/adminLogin";//跳转到登录页面
			}
		}else{
			request.setAttribute("msg", "用户名或密码错误");
			return "/source/adminPage/adminLogin/adminLogin";//跳转到登录页面
		}
//		//判断是不是后台可以登录的用户0：超级管理员，1：管理员
//		if(_user.getType() == 2){
//			request.setAttribute("msg", "对不起，您没有登录后台的权限！");
//			return "/source/adminPage/adminLogin/adminLogin";
//		}
		if(_user.getStatus()!=1 && _user.getStatus()!=2 && _user.getStatus()!=-1){
			request.setAttribute("msg", "对不起，您的帐号已停用");
			return "/source/page/regist/regist";//跳转到登录页面
		}
		/**记住密码功能*/
		LogonUtils.remeberAdmin(request,response,name,password);
		//将User放置到Session中，用于这个系统的操作
		request.getSession().setAttribute("admin_user", _user);
		return "redirect:/adminWelcomeUI.html";
	}
	/**
	 * 功能描述：后台用户管理页面
	 * 参数描述：无
	 *		 @time 2015-1-29
	 */
	@RequestMapping("/adminUserManageUI.html")
	public String adminUserManageUI(Model model,HttpServletRequest request,User user){
		//User globle_user = (User) request.getSession().getAttribute("globle_user");
		List<User> list = userService.findAll(user);
		List<User> newSupList = new ArrayList<User>();
		List<User> newSysList = new ArrayList<User>();
		List<User> newRegList = new ArrayList<User>();
		List<User> newOnlineList = new ArrayList<User>();
		if(list!=null && list.size()>0){
			for(User u :list){
				//查询服务个数,一个订单一个服务
				List<Order> order = orderService.findOrderByUserId(u.getId());
				if(order.size()>0&&order!=null){
					u.setServSum(order.size());
				}
				//查询资产个数
				List<Asset> assetList = assetService.findByUserId(u.getId());
				if(assetList.size()>0&&assetList!=null){
					u.setAssetSum(assetList.size());
				}
				//用户类型（0：超级管理员，1：管理员，2：用户(注册用户和企业用户)）
				if(u.getType()==0){
					newSupList.add(u);	
				}
				if(u.getType()==1){
					newSysList.add(u);	
				}
				if(u.getType()==2 || u.getType()==3){
					newRegList.add(u);	
				}
				//查询在线用户（status:2）
				if(u.getStatus()==2){
					newOnlineList.add(u);
				}
			}
		}
		model.addAttribute("list",newSupList);//超级管理员列表
		model.addAttribute("sysList", newSysList);//系统管理员列表
		model.addAttribute("regList", newRegList);//注册用户列表
		model.addAttribute("onLineList", newOnlineList);//在线用户列表
		int supSum = 0;//超级管理员个数
		if(newSupList.size()>0&&newSupList!=null){
			supSum = newSupList.size();
		}
		request.setAttribute("supSum", supSum);//超级管理员个数
		
		int sysSum = 0;//查询系统管理员个数
		if(newSysList.size()>0&&newSysList!=null){
			sysSum = newSysList.size();
		}
		request.setAttribute("sysSum", sysSum);
		
		int regSum = 0;//注册用户个数
		if(newRegList.size()>0&&newRegList!=null){
			regSum = newRegList.size();
		}
		request.setAttribute("regSum", regSum);
		
		int onLineSum = 0;//在线用户个数
		if(newOnlineList.size()>0 && newOnlineList!=null){
			onLineSum = newOnlineList.size();
		}
		request.setAttribute("onLineSum", onLineSum);
		
		if(user.getName()!=null){
			model.addAttribute("name",user.getName());//回显用户名
		}
		if(user.getPassword()!=null){
			model.addAttribute("password",user.getPassword());//回显用户名
		}
		if(user.getMobile()!=null){
			model.addAttribute("mobile",user.getMobile());//回显电话
		}
		if(user.getIndustry()!=null && !user.getIndustry().equals("")){
			model.addAttribute("industry",user.getIndustry());//回显行业
		}
		if(user.getJob()!=null && !user.getJob().equals("")){
			model.addAttribute("job",user.getJob());//回显职业
		}
		if(user.getCompany()!=null){
			model.addAttribute("company",user.getCompany());//回显公司
		}
		if(user.getType()!=-1){
			model.addAttribute("type",user.getType());//回显类型
		}
		if(user.getStartIP() != null && !user.getStartIP().equals("")){
			model.addAttribute("startIP",user.getStartIP());//回显起始IP
		}
		if(user.getEndIP() != null && !user.getEndIP().equals("")){
			model.addAttribute("endIP",user.getEndIP());//回显终止IP
		}		
		return "/source/adminPage/userManage/userManage";
	}
	/**
	 * 功能描述：退出
	 * 参数描述：无
	 *		 @time 2015-1-29
	 */
	@RequestMapping("/adminExit.html")
	public String adminExit(HttpServletRequest request){
		//request.getSession().removeAttribute("admin_user");
		request.getSession().invalidate();
		return "redirect:/admin.html";
	}
	/**
	 * 功能描述：自注册用户无权限登录后台
	 *		 @time 2015-1-29
	 */
	@RequestMapping("/errorMsg.html")
	public String errorMsg(){
		return "/source/error/errorMsg";
	}
	
	/**
	 * 功能描述：添加用户
	 *		 @time 2015-2-2
	 */
	@RequestMapping("/adminAddUser.html")
	public String add(User user){
		String realName = "";
	    //行业
		String industry = "";
		//职业
		String job = "";
		//公司名称
		String company = "";
		try {//中文乱码
			realName = new String(user.getRealName().getBytes("ISO-8859-1"), "UTF-8");
			user.setRealName(realName);
			industry = new String(user.getIndustry().getBytes("ISO-8859-1"), "UTF-8");
			job = new String(user.getJob().getBytes("ISO-8859-1"),"UTF-8");
			company = new String(user.getCompany().getBytes("ISO-8859-1"),"UTF-8");
			user.setIndustry(industry);
			user.setJob(job);
			user.setCompany(company);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String password = user.getPassword();
		String md5password = DigestUtils.md5Hex(password);//密码加密
		user.setPassword(md5password);
		user.setStatus(1);//用户状态(1：正常，0：停用)
		user.setCreateTime(new Date());//创建时间
		userService.insert(user);
		return "redirect:/adminUserManageUI.html";
	}
	/**
	 * 功能描述：修改用户
	 *		 @time 2015-2-9
	 */
	@RequestMapping("/adminEditUser.html")
	public String edit(User user){
		String realName = "";
	    //行业
		String industry = "";
		//职业
		String job = "";
		//公司名称
		String company = "";
		try {//中文乱码
			realName = new String(user.getRealName().getBytes("ISO-8859-1"), "UTF-8");
			String password = user.getPassword();
			String md5password = DigestUtils.md5Hex(password);//密码加密
			user.setPassword(md5password);
			user.setRealName(realName);
			industry = new String(user.getIndustry().getBytes("ISO-8859-1"), "UTF-8");
			job = new String(user.getJob().getBytes("ISO-8859-1"),"UTF-8");
			company = new String(user.getCompany().getBytes("ISO-8859-1"),"UTF-8");
			user.setIndustry(industry);
			user.setJob(job);
			user.setCompany(company);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		userService.update(user);
		return "redirect:/adminUserManageUI.html";
	}
	
	
	/**
	 * 功能描述：检查删除用户
	 *		 @time 2015-2-2
	 */
	@RequestMapping("/adminDeleteCheck.html")
	public void adminDeleteCheck(User user,HttpServletResponse response){
		int id = user.getId();
		int count = 0;
		//检查要删除的用户下，是否有订单服务
		List<Order> orderList = orderService.findOrderByUserId(id);
		//检查要删除的用户下，是否有资产
		List<Asset> assetList = assetService.findByUserId(id);
		if(orderList.size()!=0&&orderList!=null){
			count += orderList.size();
		}
		if(assetList.size()!=0&&assetList!=null){
			count += assetList.size();
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
	 * 功能描述：删除用户
	 *		 @time 2015-2-2
	 */
	@RequestMapping("/adminDeleteUser.html")
	public String adminDeleteUser(User user){
		userService.delete(user.getId());
		return "redirect:/adminUserManageUI.html";
	}
	
	/**
	 * 功能描述：后台用户分析页面
	 * 参数描述：无
	 *		 @time 2015-10-29
	 */
	@RequestMapping("/adminUserAnalysisUI.html")
	public String adminUserAnalysisUI(Model model,HttpServletRequest request){	
		return "/source/adminPage/userManage/userAnalysis";
	}
	
	/**
	 * 功能描述：后台用户活跃度统计分析
	 * @param request
	 * @return
	 */
	@RequestMapping("/adminUserAnalysis.html")
	@ResponseBody
	public void adminUserAnalysis(HttpServletResponse response,HttpServletRequest request){
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		
		String begin_date = request.getParameter("begin_date");
		String end_date = request.getParameter("end_date");
		String activeSel = request.getParameter("activeSel");
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("begin_date", begin_date);
		paramMap.put("end_date", end_date);

		try {
			//登录用户数量统计情况
			if(activeSel.equals("1")){

				//查询历史登录情况
				int loginCount = userService.findRegisterCountByDates(paramMap);
				//查询所有注册用户的数量
				int allCount = userService.findRegisterCount();
				double loginParent = ((double)loginCount/(double)allCount)*100;
				BigDecimal b = new BigDecimal(loginParent); 
				float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue(); 
				
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("loginCount", loginCount);
				m.put("loginParent", f1+"%");

				//object转化为Json格式
				JSONObject JSON = CommonUtil.objectToJson(response, m);
				
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);

			}else{//用户活跃度排行榜
				List loginTop10List = userService.findLoginTop10(paramMap);
				Gson gson= new Gson();  
				String resultGson = gson.toJson(loginTop10List);
				PrintWriter out;
				out = response.getWriter();
				out.write(resultGson); 
				out.flush(); 
				out.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能描述：后台用户使用习惯统计分析
	 * @param request
	 * @return
	 */
	@RequestMapping("/adminUserHabitAnalysis.html")
	@ResponseBody
	public void adminUserHabitAnalysis(HttpServletResponse response,HttpServletRequest request){
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		
		String begin_date = request.getParameter("begin_date");
		String end_date = request.getParameter("end_date");
		String useSel = request.getParameter("useSel");
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("begin_date", begin_date);
		paramMap.put("end_date", end_date);

		try {
			Gson gson= new Gson(); 
			String resultGson = "";
			//登录最集中时间段top5
			if(useSel.equals("1")){
				List timesTop5List = userService.findTimesTop5(paramMap);
				resultGson = gson.toJson(timesTop5List);

			}else{//下单最集中时间段top5
				List orderTimesTop5List = orderService.findOrderTimesTop5(paramMap);
				resultGson = gson.toJson(orderTimesTop5List);
			}
			
			PrintWriter out;
			out = response.getWriter();
			out.write(resultGson); 
			out.flush(); 
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 功能描述：后台用户行业分布统计
	 * @param request
	 * @return
	 */
	@RequestMapping("/adminIndusAnalysis.html")
	@ResponseBody
	public void adminIndusAnalysis(HttpServletResponse response,HttpServletRequest request){
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		
		String begin_date = request.getParameter("begin_date");
		String end_date = request.getParameter("end_date");
		String industry = request.getParameter("industry");
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("begin_date", begin_date);
		paramMap.put("end_date", end_date);

		try {
			Gson gson= new Gson(); 
			
			if(!industry.equals("-1")){
				paramMap.put("industry", industry);
			}
			//根据行业查询下单用户数量
			List list = orderService.findUserCountByIndus(paramMap);
			String resultGson = gson.toJson(list);
			
			PrintWriter out;
			out = response.getWriter();
			out.write(resultGson); 
			out.flush(); 
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	
	/**
	 * 功能描述：后台welcome页面
	 * 参数描述：无
	 *		 @time 2016-10-13
	 */
	@RequestMapping("/adminWelcomeUI.html")
	public String adminWelcomeUI(Model model,HttpServletRequest request,User user){
		//User globle_user = (User) request.getSession().getAttribute("globle_user");
		List<User> userlist = userService.findAll(user);
		int userSum = 0;//查询用户数个数
		if(userlist.size()>0&&userlist!=null){
			userSum = userlist.size();
		}
		request.setAttribute("userSum", userSum);
		//扫描网站
        int webSite = selfHelpOrderService.findWebSite();
        request.setAttribute("webSite", webSite);
        //扫描页面数
        int webPageNum = selfHelpOrderService.findWebPageNum();
        request.setAttribute("webPageNum", webPageNum);
		//订单数
        int orderSum = orderService.getOrder().size();
        request.setAttribute("orderSum", orderSum);
        //防护网站数
        int wafNum = 0;
        request.setAttribute("wafNum", wafNum);
        //告警数
        int leakNum = selfHelpOrderService.findLeakNum(1);
        request.setAttribute("leakNum", leakNum);
        
        
				
		return "/source/adminPage/userManage/welcomePage";
	}
	
	
	/**
	 * 功能描述：后台menu页面
	 * 参数描述：无
	 *		 @time 2016-10-13
	 */
	@RequestMapping("/menu.html")
	public String menu(Model model,HttpServletRequest request,User user){
		return "/source/adminPage/common/menu";
	}
	
	/**
	 * 功能描述：后台footer页面
	 * 参数描述：无
	 *		 @time 2016-10-13
	 */
	@RequestMapping("/footer.html")
	public String footer(Model model,HttpServletRequest request,User user){
		return "/source/adminPage/common/footer";
	}
}
