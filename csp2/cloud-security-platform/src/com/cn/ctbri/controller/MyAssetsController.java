package com.cn.ctbri.controller;


import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.ctbri.cfg.Configuration;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.City;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IDistrictDataService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IUserService;
import com.cn.ctbri.util.AddressUtils;
import com.cn.ctbri.util.CommonUtil;
import com.cn.ctbri.util.GetNetContent;
/**
 * 创 建 人  ：  邓元元
 * 创建日期：  2015-1-16
 * 描        述：  我的资产
 * 版        本：  1.0
 */
@Controller
public class MyAssetsController {
	@Autowired
	IAssetService assetService;
	@Autowired
	IOrderAssetService orderAssetService;
	@Autowired
	IDistrictDataService districtService;
	@Autowired
	IUserService userService;
	@Autowired
    IDistrictDataService districtDataService;
	@Autowired
    ISelfHelpOrderService selfHelpOrderService;
	
	private String  CODE_STRING = "<a href=\"http://www.anquanbang.net/userAssetsUI.html\">网站安全帮</a>";
	/**
	 * 功能描述： 我的资产页面
	 * 参数描述： Model model,HttpServletRequest request
	 *		 @time 2015-1-16
	 */
	@RequestMapping("/userAssetsUI.html")
	public String userBillUI(Model model,HttpServletRequest request){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		List<Asset> list = assetService.findByUserId(globle_user.getId());
		model.addAttribute("list",list);
		model.addAttribute("type",request.getParameter("type"));
		return "/source/page/personalCenter/userAssets";
	}
	
	/**
	 * 异步进入我的资产页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/userAssets.html")
	public ModelAndView userAssets(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("source/page/personalCenter/assetList");
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		List<Asset> list = assetService.findByUserId(globle_user.getId());
		if(list!=null && list.size()>0){
			mv.addObject("list", list);
		}
		return mv;
	}

	/**
	 * 功能描述： 检查资产地址是否已经存在
	 * 参数描述： Model model
	 *		 @time 2015-3-9
	 */
	@RequestMapping("/asset_addrIsExist.html")
	@ResponseBody
	public void addrIsExist(Model model,Asset asset,HttpServletResponse response,HttpServletRequest request) throws Exception{
	    User globle_user = (User) request.getSession().getAttribute("globle_user");
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    String oldName = request.getParameter("oldName");
	    String oldAddr = request.getParameter("oldAddr");
	    String oldAddrVal="";
	    if(oldAddr!=null&&!"".equals(oldAddr)){
	    	oldAddrVal=oldAddr;
	    }

	    String addr = asset.getAddr();
	    //判断资产地址是否包含http
	    Pattern pattern2 = Pattern.compile("([hH][tT][tT][pP][sS]?):\\/\\/([\\w.]+\\/?)\\S*");
		Matcher matcher2 =	pattern2.matcher(addr);
		if(!matcher2.find()){
			addr="http://"+addr.trim();
		}
	    String assetName =URLDecoder.decode(asset.getName(),"utf-8"); ;
        paramMap.put("userId", globle_user.getId());
    	//String addrType = request.getParameter("addrType");
    	/*if(!(addr.startsWith(addrType))){
            addr = addrType + "://" + addr.trim();
        }*/
		Map<String, Object> m = new HashMap<String, Object>();
	    if(oldName!=null || oldAddrVal!=null){
	    	//修改时判断存在
	    	if(!assetName.trim().equals(oldName)){
	            paramMap.put("name", assetName.trim());
	    		List<Asset> listForName = assetService.findByAssetAddr(paramMap);

	    		if(listForName != null && listForName.size()>0){
	    			m.put("msg", '1');//1：表示资产名称已经存在
	    		}else{
	    			 if(!addr.trim().equals(oldAddrVal)){
	    		    		Map<String, Object> paramMap1 = new HashMap<String, Object>();
	    					paramMap1.put("userId", globle_user.getId());
	    					paramMap1.put("addr", addr.trim());
	    					List<Asset> listForAddr = assetService.findByAssetAddr(paramMap1);
	    					if(listForAddr != null && listForAddr.size()>0){
	    						m.put("msg", '2');//1：表示资产地址已经存在
	    					}
	    		    	}else{
	    		    		m.put("msg", false);
	    		    	}
	    		}
	    	}else if(!addr.trim().equals(oldAddrVal)){
	    		Map<String, Object> paramMap1 = new HashMap<String, Object>();
				paramMap1.put("userId", globle_user.getId());
				paramMap1.put("addr", addr.trim());
				List<Asset> listForAddr = assetService.findByAssetAddr(paramMap1);
				if(listForAddr != null && listForAddr.size()>0){
					m.put("msg", '2');//1：表示资产地址已经存在
				}
	    	}else{
	    		m.put("msg", false);
	    	}
	    }else{
	        paramMap.put("name", assetName.trim());
			List<Asset> listForName = assetService.findByAssetAddr(paramMap);
			if(listForName != null && listForName.size()>0){
				m.put("msg", '1');//1：表示资产名称已经存在
			}else{
				Map<String, Object> paramMap1 = new HashMap<String, Object>();
				paramMap1.put("userId", globle_user.getId());
				paramMap1.put("addr", addr.trim());
				List<Asset> listForAddr = assetService.findByAssetAddr(paramMap1);
				if(listForAddr != null && listForAddr.size()>0){
					m.put("msg", '2');//1：表示资产地址已经存在
				}else{
					m.put("msg", false);
				}
			}
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
	 * 功能描述： 添加资产
	 * 参数描述： Model model
	 * @throws Exception 
	 *		 @time 2015-1-16
	 */
	@RequestMapping(value="/addAsset.html",method=RequestMethod.POST)
	public void addAsset(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		int result = 0; //1:资产数验证失败 2：资产名称验证失败 3：资产地址 4：物理地址 5：用途
		int subResult = 0;
		try {
			User globle_user = (User) request.getSession().getAttribute("globle_user");
			
			String name = request.getParameter("assetName");//资产名称
			String addr = request.getParameter("assetAddr");//资产地址
//			String addrType = request.getParameter("addrType");//资产类型
			String purpose = request.getParameter("purpose");//用途
			String prov = request.getParameter("prov");
			String city = request.getParameter("city");
			
			//资产域名转换限制 add by tangxr 2016-11-25
			addr = getNewAddr(addr);
			
			//验证资产数量
			subResult = checkAssetCount(globle_user.getId());
			if (subResult != 0) {
				result = 1;
				return;
			}
			
			//验证资产名称
			// 1:资产名称为空 2：资产名称含有非法字符  3：资产名称重复
			subResult = checkAssetName(name, null, globle_user.getId());
			if (subResult != 0) {
				result = 2;
				return;
			}
			
			//验证资产地址
			subResult = checkAssetAddress(addr, null, globle_user.getId());
			if (subResult != 0) {
				result = 3;
				return;
			}
			
			//验证资产所在物理地址
			subResult = checkPhysicalAddress(prov,city);
			if (subResult != 0) {
				result = 4;
				return;
			}
			
			//验证资产用途
			subResult = checkPurpose(purpose);
			if (subResult != 0) {
				result = 5;
				return;
			}
			
			//判断资产地址是否包含http
			Pattern pattern2 = Pattern.compile("([hH][tT][tT][pP][sS]?):\\/\\/([\\w.]+\\/?)\\S*");
			Matcher matcher2 =	pattern2.matcher(addr);
			if(!matcher2.find()){
				addr="http://"+addr.trim();
			}
			
			Asset asset = new Asset();
			asset.setUserid(globle_user.getId());//用户ID
			asset.setCreate_date(new Date());//创建日期
			if(globle_user.getType()==2){
				asset.setStatus(0);//资产状态(1：已验证，0：未验证)
//				asset.setStatus(1);//资产状态(1：已验证，0：未验证)
			}else if(globle_user.getType()==3){
				asset.setStatus(1);//资产状态(1：已验证，0：未验证)
			}
			//获得资产地址所在的省份
			String province = getProvinceInfo(addr);
			if(province!=null&&!"".equals(province)){
				//更新资产对应的省份
				String provinceId = assetService.getProvinceIdByName(province.substring(0,2));
				asset.setAssetProvince(Integer.parseInt(provinceId));
			}else{
			    asset.setAssetProvince(35);
			}
			asset.setName(name);
			asset.setAddr(addr);
			asset.setPurpose(purpose);
			asset.setDistrictId(prov);
			asset.setCity(city);
			assetService.saveAsset(asset);
//		return "redirect:/userAssetsUI.html";
			
		}catch(Exception e) {
			e.printStackTrace();
			result = 6;
		}finally{
			m.put("result", result);
			m.put("subResult", subResult);
			JSONObject JSON = CommonUtil.objectToJson(response, m);
			try {
				// 把数据返回到页面
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		
	}
	
	//资产域名转换限制
	private String getNewAddr(String addr) {
		String addrName = "";
		//域名
		String domainName = "";
		//非域名部分
		String notDomainName = "";
		//判断http协议
		if(addr.indexOf("http://")!=-1){
			addrName = addr.trim().substring(7,addr.length());
			if(addrName.indexOf("/")!=-1){
				domainName = addrName.substring(0, addrName.indexOf("/"));
				notDomainName = addrName.substring(addrName.indexOf("/"));
				addr = "http://"+domainName.toLowerCase()+notDomainName;
			}else{
				addr = "http://"+addrName.toLowerCase();
			}
		}else if(addr.indexOf("https://")!=-1){
			addrName = addr.trim().substring(8,addr.length());
			if(addrName.indexOf("/")!=-1){
				domainName = addrName.substring(0, addrName.indexOf("/"));
				notDomainName = addrName.substring(addrName.indexOf("/"));
				addr = "https://"+domainName.toLowerCase()+notDomainName;
			}else{
				addr = "https://"+addrName.toLowerCase();
			}
		}else{
			addrName = addr;
			if(addrName.indexOf("/")!=-1){
				domainName = addrName.substring(0, addrName.indexOf("/"));
				notDomainName = addrName.substring(addrName.indexOf("/"));
				addr = "http://"+domainName.toLowerCase()+notDomainName;
			}else{
				addr = "http://"+addrName.toLowerCase();
			}
		}
		return addr;
	}

	private int checkAssetName(String assetName, String oldAssetName, int userId) {
		if (null == assetName || assetName == "") {
//			return "请输入网站名称!";
			return 1;
		}
		
		String pattern = "[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]"; 
		Pattern pat = Pattern.compile(pattern);
		if(pat.matcher(assetName).find()){
//			return "请输入正确的网站名称!";
			return 2;
		}
		
		if (oldAssetName != null && assetName.equals(oldAssetName)) {
			return 0;
		}
		
	    Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        paramMap.put("name", assetName.trim());
		List<Asset> listForName = assetService.findByAssetAddr(paramMap);
		if(listForName != null && listForName.size()>0){
//			return "网站名称重复!";
			return 3;
		}
		
		return 0;
	}
	
	private int checkAssetAddress(String assetAddress, String oldAssetAddr, int userId) {
		if (null == assetAddress || assetAddress == "") {
//			return "请输入网站地址!";
			return 1;
		}
		
		String pattern = "[`~!@#$^&*()=|{}';',<>?~！@#￥……&*（）——|{}【】‘；”“'。，？]"; 
		Pattern pat1 = Pattern.compile(pattern);
		if(pat1.matcher(assetAddress).find()){
//			return "请输入正确的网站地址!";
			return 2;
		}
		
		String strRegex = "^((http|https)\\://)?([a-zA-Z0-9\\.\\-]+(\\:[a-zA-"   
	        + "Z0-9\\.&%\\$\\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{"   
	        + "2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}"   
	        + "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|"   
	        + "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-"   
	        + "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0"   
	        + "-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/"   
	        + "[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*$";
		Pattern pat2 = Pattern.compile(strRegex);
		if(!pat2.matcher(assetAddress).matches()){
//			return "请输入正确的网站地址!";
			return 2;
		}
		
	    //验证资产地址是否重复
		String addr = assetAddress;
		//判断资产地址是否包含http
		Pattern pattern2 = Pattern.compile("([hH][tT][tT][pP][sS]?):\\/\\/([\\w.]+\\/?)\\S*");
		Matcher matcher2 =	pattern2.matcher(addr);
		if(!matcher2.find()){
			addr="http://"+addr.trim();
		}
		
		if (oldAssetAddr != null && assetAddress.equalsIgnoreCase(oldAssetAddr)) {
			return 0;
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
		paramMap.put("addr", addr.trim());
		List<Asset> listForAddr = assetService.findByAssetAddr(paramMap);
		if(listForAddr != null && listForAddr.size()>0){
//			return "资产地址重复!";
			return 3;
		}
		
		return 0;
	}
	
	/**
	 * 功能描述：检查物理地址是否正确
	 */
	private int checkPhysicalAddress(String districtId, String cityId) {
		try {
			//未选择省份的场合，校验失败
			if (districtId == null || districtId.equals("")) {
				return 1;
			}
			
			//省份Id不存在的场合，校验失败
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("provId", districtId);
			String name = districtDataService.getProvNameById(paramMap);
			if (null == name || name.equals("")) {
				return 1;
			}
			
			Map<String, Object> paramMap2 = new HashMap<String, Object>();
			paramMap2.put("districtId", districtId);
			List<City> cityList = districtDataService.getCityListByProv(paramMap2);
			
			if (cityList == null || cityList.size() == 0) {   //省份没有下属城市
				if (cityId == null || cityId.equals("")|| cityId.equals("-1")) {
					return 0;
				}else {  //但城市信息参数有值时，校验失败
					return 1;
				}
			}else {     //省份有下属城市
				//检验城市信息
				int cityIdInt = Integer.valueOf(cityId);
				for (City city : cityList) {
					if(city.getId()== cityIdInt) {   //该省份包括该城市，校验成功
						return 0;
					}
				}
				return 1;    //该省份不包括该城市，校验失败
			}
			
		} catch(Exception e) {
			return 1;   //校验失败
		}
	}
	
	/**
	 * 功能描述：资产用途是否正确
	 */
	private int checkPurpose(String purpose) {
		if ("公共服务".equals(purpose) || 
				"信息发布".equals(purpose) || 
				"信息服务".equals(purpose)|| 
				"社交".equals(purpose)|| 
				"娱乐".equals(purpose)|| 
				"电子商务/金融".equals(purpose)|| 
				"移动互联网".equals(purpose)|| 
				"教育/医疗".equals(purpose)|| 
				"其他".equals(purpose)) {
			return 0;
		}
		return 1;
	}
	
	private int checkAssetCount(int userId) {
		//根据用户id查询
	    List<User> userList = userService.findUserById(userId);
	    User _user = userList.get(0);
	    
        //根据用户判断资产数是否超过预定
		List<Asset> list = assetService.findByUserId(userId);
		Map<String, Object> m = new HashMap<String, Object>();
		
		//个人用户
		if(_user.getType()==2){
			if(list != null && list.size() >= 5){
				//表示添加的资产数已经为5，不能再进行添加
				return 5;
			}
		}else if(_user.getType()==3){
			//企业用户
			if(list != null && list.size() >= 100){
				//表示添加的资产数已经为10，不能再进行添加
				return 100;
			}
		}
		return 0;
	}
	

	/**
	 * 功能描述：检查资产是否可以被删除
	 * 参数描述：Asset asset ,HttpServletResponse response
	 *		 @time 2015-1-19
	 */
	@RequestMapping("/checkdelete.html")
	public void checkdelete(Asset asset,HttpServletResponse response){
		//检查订单资产表里面是否含有此资产
		List<OrderAsset> list = orderAssetService.findAssetById(asset.getId());
		int count = 0;
		if(list.size()>0){
			count = list.size();
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
	 * 功能描述：检查资产是否可以被修改
	 * 参数描述：Asset asset ,HttpServletResponse response
	 *		 @time 2016-7-1
	 */
	@RequestMapping("/checkedit.html")
	@ResponseBody
	public void checkedit(Asset asset,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		//id验证
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
		Asset oldAsset = assetService.findById(asset.getId(),globle_user.getId());
		if(oldAsset == null) {
			m.put("checkId", false);
		} else {
			//检查订单资产表里面是否含有此资产
			List<OrderAsset> list = orderAssetService.findRunAssetById(asset.getId());
			int count = 0;
			if(list.size()>0){
				count = list.size();
			}
			m.put("checkId", true);
			m.put("count", count);
		}
		
		m.put("assetId", asset.getId());
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
	 * 功能描述：修改资产
	 * 参数描述： Model model
	 *		 @time 2015-1-19
	 */
	@RequestMapping(value="/editAsset.html",method=RequestMethod.POST)
	public void editAsset(HttpServletResponse response,HttpServletRequest request){
		Map<String, Object> m = new HashMap<String, Object>();
		int result = 0;
		int subResult = 0;
		 //用户
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
        try {
			int id = Integer.parseInt(request.getParameter("id"));
			Asset oldAsset = assetService.findById(id,globle_user.getId());
			
			//id验证
			if(oldAsset == null) {
				result = 1;
				subResult = 1;
				return;
			}
			
			//检查订单资产表里面是否含有此资产
			List<OrderAsset> list = orderAssetService.findRunAssetById(id);
			if(list.size() > 0){
				result = 1;
				subResult = 2;
				return;
			}
			
			String name = request.getParameter("assetName");
			String assetAddr = request.getParameter("assetAddr");
			String districtId = request.getParameter("prov");
			String city = request.getParameter("city");
			String purpose = request.getParameter("purpose");
			//String addrType = request.getParameter("addrType");
			Pattern pattern2 = Pattern.compile("([hH][tT][tT][pP][sS]?):\\/\\/([\\w.]+\\/?)\\S*");
			Matcher matcher2 =	pattern2.matcher(assetAddr);
			if(!matcher2.find()){
				assetAddr="http://"+assetAddr.trim();
			}
			/*//判断资产地址是否包含http
		    if((assetAddr.trim().toLowerCase().indexOf("http://")==-1||assetAddr.trim().toLowerCase().indexOf("https://")==-1)){
		    
		    }*/
			
			//资产域名转换 add by tangxr 2016-11-25
			assetAddr = getNewAddr(assetAddr);
			
			String oldCity = "";
			if(oldAsset.getCity()!= null){
				oldCity = oldAsset.getCity();
			}
			
			if(oldAsset.getName().equals(name) && oldAsset.getAddr().equals(assetAddr) && 
					oldAsset.getPurpose().equals(purpose) &&
					oldAsset.getDistrictId().equals(districtId) && oldCity.equals(city)){
				return;
			}
			
			//资产名称验证
			subResult = checkAssetName(name, oldAsset.getName(), globle_user.getId());
			if (subResult != 0) {
				result = 2;
				return;
			}
			
			
			//资产地址验证
			subResult = checkAssetAddress(assetAddr, oldAsset.getAddr(), globle_user.getId());
			if (subResult != 0) {
				result = 3;
				return;
			}
			
			//资产物理地址验证
			subResult = checkPhysicalAddress(districtId,city);
			if (subResult != 0) {
				result = 4;
				return;
			}
			
			//资产用途验证
			subResult = checkPurpose(purpose);
			if (subResult != 0) {
				result = 5;
				return;
			}
			
			Asset asset = new Asset();
			asset.setName(name);
			asset.setId(id);
			
			asset.setAddr(assetAddr);
			asset.setStatus(0);
//			asset.setStatus(1);
			asset.setDistrictId(districtId);
			asset.setCity(city);
			asset.setPurpose(purpose);
			//获得资产地址所在的省份
			String province = getProvinceInfo(assetAddr);
			if(province!=null&&!"".equals(province)){
				//更新资产对应的省份
				String provinceId = assetService.getProvinceIdByName(province.substring(0,2));
				asset.setAssetProvince(Integer.parseInt(provinceId));
			}else{
			    asset.setAssetProvince(35);
			}
			assetService.updateAsset(asset);
			
		} catch (Exception e) {
			e.printStackTrace();
			result = 6;
		}finally {
			m.put("result", result);
			m.put("subResult", subResult);
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
	 * 功能描述：删除资产
	 * 参数描述： Model model
	 *		 @time 2015-1-19
	 */
	@RequestMapping(value="/deleteAsset.html",method=RequestMethod.POST)
	public void delete(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		int errorCode = 0;
		int id = Integer.parseInt(request.getParameter("id"));
	    try {
	    	 //用户
	    	User globle_user = (User) request.getSession().getAttribute("globle_user");
			Asset oldAsset = assetService.findById(id,globle_user.getId());
			//id验证
			if(oldAsset == null) {
				m.put("successFlag", false);
				return;
			}
			
			//检查订单资产表里面是否含有此资产
			List<OrderAsset> list = orderAssetService.findRunAssetById(id);
			if(list.size() > 0){
				m.put("successFlag", false);
				errorCode = 1;
				return;
			}
			
			if(validate(request)){
			    assetService.delete(id);
			    m.put("successFlag", true);
			    return;
			}
			m.put("successFlag", false);
		} catch (Exception e) {
			m.put("successFlag", false);
			e.printStackTrace();
		}finally {
			m.put("errorCode", errorCode);
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
	 * 功能描述：联合搜索
	 * 参数描述： Model model,Asset asset,HttpServletRequest request
	 *		 @time 2015-1-19
	 */
	@RequestMapping("/searchAssetCombine.html")
	public ModelAndView searchAssetsCombine(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("source/page/personalCenter/assetList");
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		Map<String, Object> map = new HashMap<String, Object>();

		String name = request.getParameter("searchAssetName");

		if(name!=null && name.equals("输入资产名称或地址")){
			name="";
		}
		map.put("userid", globle_user.getId());
		map.put("keyword", name);
		List<Asset> list = assetService.searchAssetsCombine(map);//根据userid 资产状态 和资产名称联合查询
		
		mv.addObject("list", list);

				
		return mv;
	}
	/**
	 * 功能描述：检查资产是否通过验证
	 * 参数描述： Asset asset
	 *		 @time 2015-1-19
	 *	返回值：无
	 */
	@RequestMapping(value="/asset_verification.html",method=RequestMethod.POST)
	public void asset_verification(HttpServletRequest request,HttpServletResponse response){
		 //用户
    	User globle_user = (User) request.getSession().getAttribute("globle_user");
		int id = Integer.valueOf(request.getParameter("id"));
		Asset _asset = assetService.findById(id,globle_user.getId());
		String path = _asset.getAddr();
		int status = _asset.getStatus();
		//获取验证方式:代码验证 ;上传文件验证
		String verification_msg;
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("status", status);//返回验证状态
		try {
//			URL url =new URL(path);
//			HttpClient httpClient = HttpClient.New(url);
//			OutputStream outputStream = httpClient.getOutputStream();
//			verification_msg = new String(request.getParameter("codeStyle").getBytes("ISO-8859-1"), "UTF-8");
			verification_msg = request.getParameter("codeStyle");
			//代码验证
			if(verification_msg.equals("codeVerification")){
				 //获取已知代码
	//			 String code1 = StringEscapeUtils.unescapeHtml(String.valueOf(request.getParameter("code1"))).trim();
//				 String code1 = new String(String.valueOf(request.getParameter("code1")).getBytes("ISO-8859-1"),"UTF-8");
				 String code1 = CODE_STRING;
				 String str = GetNetContent.getNodeList(path);
//				 String str= rt.toString();
//				 String str= rt.toHtml();
				 if(str.contains(code1)){
					 m.put("msg", 1);//验证成功
				 }else{
					 m.put("msg", 0);//验证失败
				 }
			}
			//上传文件验证
			if(verification_msg.equals("fileVerification")){
				try{
					String newPath = "";
					if(path.endsWith("/")){
						newPath = path+"key.html";
					}else{
						newPath =  path+"/key.html";
					}
					URL fileUrl = new URL(newPath);//http://localhost:8080/cloud-security-platform/key.txt
					//sun.net.www.protocol.http.HttpURLConnection:http://localhost:8080/cloud-security-platform/key.txt
					HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
					DataInputStream input = new DataInputStream(conn.getInputStream());
					StringBuffer sb = new StringBuffer();
					int len = 0;
					while ((len = input.read()) != -1) {
	
						sb.append((char) len);
					}
					String toStringSb = sb.toString();
					input.close();
					if(toStringSb!=null&&toStringSb.contains(Configuration.getFileContent())){
						m.put("msg", 1);//验证成功
					}else{
						m.put("msg", 0);//验证失败
					}
				
				}catch (Exception e) {
					m.put("msg",0);//验证失败
					e.printStackTrace();
				}
			}
			 JSONObject JSON = CommonUtil.objectToJson(response, m);
			// 把数据返回到页面
			 CommonUtil.writeToJsp(response, JSON);
		} catch (Exception e) {
			m.put("msg",0);//验证失败
			JSONObject JSON = CommonUtil.objectToJson(response, m);
				// 把数据返回到页面
			try {
				CommonUtil.writeToJsp(response, JSON);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能描述：资产验证
	 * 参数描述： Asset asset
	 *		 @time 2015-1-19
	 *	返回值：redirect:/userAssetsUI.html
	 */
	@RequestMapping(value="/verificationAsset.html",method=RequestMethod.POST)
	public String verificationAsset(Asset asset){
		asset.setStatus(1);
		assetService.updateAsset(asset);
		return "redirect:/userAssetsUI.html";
	}
	//================下载=================
    private String type;// 文件的MIME类型
    
	public String getType() {
		return type;
	}
	
	/**
	 * 功能描述：下载导入模板
	 * 参数描述：HttpServletRequest request,HttpServletResponse response
	 *		 @time 2015-1-21
	 */
	@RequestMapping("/download.html")
	public void download(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		String fileName = request.getParameter("fileName");
		String fileName = "key.html";
		//fileName = new String(fileName.getBytes("ISO-8859-1"), "UTF-8");//反编译解决路径乱码
		//获取文件路径
		String path = request.getSession().getServletContext().getRealPath("/source/download");
		File file = new File(path+"/"+fileName);
		//response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1") + ";");//文件的下载方式：attachment：附件
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");//文件的下载方式：attachment：附件
		// 通常文件名称得到mime类型
		type = request.getSession().getServletContext().getMimeType(fileName);
		//将路径path转化成输入流
		InputStream in = new FileInputStream(file);
		//将输入流的数据放置到模型驱动对象的InputStream的属性中
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		byte[] temp = new byte[1024];
		int size = 0;
		while ((size = in.read(temp)) != -1) {
			out.write(temp, 0, size);
		}
		in.close();
		ServletOutputStream os = response.getOutputStream();
		os.write(out.toByteArray());
		os.flush();
		os.close();
		return;
	}
	
	/**
     * 功能描述：防止跨域操作
     * 参数描述：HttpServletRequest request
     *       @time 2015-8-6
     */
	public static boolean validate(HttpServletRequest request){
        String Referer = "";
        boolean referer_sign = true; // true 站内提交，验证通过 //false 站外提交，验证失败
        Enumeration headerValues = request.getHeaders("Referer");
        while (headerValues.hasMoreElements())
            Referer = (String) headerValues.nextElement();
        // 判断是否存在请求页面
        if (Referer == null || Referer.length() < 1)
            referer_sign = false;
        else {
            // 判断请求页面和getRequestURI是否相同
            String servername_str = request.getServerName();
            if (servername_str != null || servername_str.length() > 0) {
                int index = 0;
                if (StringUtils.indexOf(Referer, "https://") == 0) {
                    index = 8;
                } else if (StringUtils.indexOf(Referer, "http://") == 0) {
                    index = 7;
                }
                if (Referer.length() - index < servername_str.length()) // 长度不够
                    referer_sign = false;
                else { // 比较字符串（主机名称）是否相同
                    String referer_str = Referer.substring(index, index
                            + servername_str.length());
                    if (!servername_str.equalsIgnoreCase(referer_str))
                        referer_sign = false;
                }
            } else
                referer_sign = false;
        }
        return referer_sign;
	    }
	
	@RequestMapping("/asset_CountOver.html")
	@ResponseBody
	public void isAssetCountOver(HttpServletResponse response,HttpServletRequest request) throws Exception{
	    User globle_user = (User) request.getSession().getAttribute("globle_user");
	    
	    //根据用户id查询
	    List<User> userList = userService.findUserById(globle_user.getId());
	    User _user = userList.get(0);
	    
        //根据用户判断资产数是否超过预定
		List<Asset> list = assetService.findByUserId(globle_user.getId());
		Map<String, Object> m = new HashMap<String, Object>();
		
		//个人用户
		if(_user.getType()==2){
			if(list != null && list.size() >= 5){
				m.put("msg", true);//表示添加的资产数已经为5，不能再进行添加
				m.put("allowCount", "5");
			}else{
				m.put("msg", false);
			}
		}else if(_user.getType()==3){
			//企业用户
			if(list != null && list.size() >= 100){
				m.put("msg", true);//表示添加的资产数已经为10，不能再进行添加
				m.put("allowCount", "100");
			}else{
				m.put("msg", false);
			}
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
	 * 功能描述： 添加资产
	 * 参数描述： Model model
	 * @throws Exception 
	 *		 @time 2015-1-16
	 */
	@RequestMapping(value="/addWebSite.html", method=RequestMethod.POST)
	public void addWebSite(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		int result = 0; //1:资产数验证失败 2：资产名称验证失败 3：资产地址 4：物理地址 5：用途
		int subResult = 0;
		
		try {
			User globle_user = (User) request.getSession().getAttribute("globle_user");
			String name = request.getParameter("assetName");//资产名称
			String addr = request.getParameter("assetAddr");//资产地址
			String purpose = request.getParameter("purpose");//用途
			String prov = request.getParameter("prov");
			String city = request.getParameter("city");
			
			//资产域名转换限制 add by tangxr 2016-11-25
			addr = getNewAddr(addr);
			
			//验证资产数量
			subResult = checkAssetCount(globle_user.getId());
			if (subResult != 0) {
				result = 1;
				return;
			}
			
			//验证资产名称
			// 1:资产名称为空 2：资产名称含有非法字符  3：资产名称重复
			subResult = checkAssetName(name, null, globle_user.getId());
			if (subResult != 0) {
				result = 2;
				return;
			}
			
			//验证资产地址
			subResult = checkAssetAddress(addr, null, globle_user.getId());
			if (subResult != 0) {
				result = 3;
				return;
			}
			
			//验证资产所在物理地址
			subResult = checkPhysicalAddress(prov,city);
			if (subResult != 0) {
				result = 4;
				return;
			}
			
			//验证资产用途
			subResult = checkPurpose(purpose);
			if (subResult != 0) {
				result = 5;
				return;
			}
			
			Asset asset = new Asset();
			asset.setUserid(globle_user.getId());//用户ID
			asset.setCreate_date(new Date());//创建日期
			if(globle_user.getType()==2){
				asset.setStatus(0);//资产状态(1：已验证，0：未验证)
//				asset.setStatus(1);//资产状态(1：已验证，0：未验证)
			}else if(globle_user.getType()==3){
				asset.setStatus(1);//资产状态(1：已验证，0：未验证)
			}

						
//			if(!(addr.startsWith(addrType)) || addr.equals(addrType)){
//				addr = addrType + "://" + addr.trim();
//			}
			//判断资产地址是否包含http
			Pattern pattern2 = Pattern.compile("([hH][tT][tT][pP][sS]?):\\/\\/([\\w.]+\\/?)\\S*");
			Matcher matcher2 =	pattern2.matcher(addr);
			if(!matcher2.find()){
				addr="http://"+addr.trim();
			}
			//获得资产地址所在的省份
			String province = getProvinceInfo(addr);
			if(province!=null&&!"".equals(province)){
				//更新资产对应的省份
				String provinceId = assetService.getProvinceIdByName(province.substring(0,2));
				asset.setAssetProvince(Integer.parseInt(provinceId));
			}else{
			    asset.setAssetProvince(35);
			}
			asset.setName(name);
			asset.setAddr(addr);
			asset.setPurpose(purpose);
			asset.setDistrictId(prov);
			asset.setCity(city);
			assetService.saveAsset(asset);

			//获取服务对象资产
		    List<Asset> serviceAssetList = selfHelpOrderService.findServiceAsset(globle_user.getId());
		    m.put("serviceAssetList", serviceAssetList);
//			m.put("success", true);
			
		} catch (Exception e) {
			result = 6;
//			m.put("success", false);
//			m.put("wafFlag", true);
			e.printStackTrace();
		}finally {
			m.put("result", result);
			m.put("subResult", subResult);
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
	 * 功能描述： 添加资产
	 * 参数描述： Model model
	 * @throws Exception 
	 *		 @time 2015-1-16
	 */
	@RequestMapping(value="/addWafWebSite.html", method=RequestMethod.POST)
	public void addWafWebSite(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		int result = 0; //1:资产数验证失败 2：资产名称验证失败 3：资产地址 4：物理地址 5：用途
		int subResult = 0;
		

		try {
			User globle_user = (User) request.getSession().getAttribute("globle_user");
			String name = request.getParameter("assetName");//资产名称
			String addr = request.getParameter("assetAddr");//资产地址
//			String addrType = request.getParameter("addrType");//资产类型
			String purpose = request.getParameter("purpose");//用途
			String prov = request.getParameter("prov");
			String city = request.getParameter("city");
//			String wafFlag = request.getParameter("wafFlag");
			
			//资产域名转换限制 add by tangxr 2016-11-25
			addr = getNewAddr(addr);
			
			//验证资产数量
			subResult = checkAssetCount(globle_user.getId());
			if (subResult != 0) {
				result = 1;
				return;
			}
			
			//验证资产名称
			// 1:资产名称为空 2：资产名称含有非法字符  3：资产名称重复
			subResult = checkAssetName(name, null, globle_user.getId());
			if (subResult != 0) {
				result = 2;
				return;
			}
			
			//验证资产地址
			subResult = checkAssetAddress(addr, null, globle_user.getId());
			if (subResult != 0) {
				result = 3;
				return;
			}
			
			//验证资产所在物理地址
			subResult = checkPhysicalAddress(prov,city);
			if (subResult != 0) {
				result = 4;
				return;
			}
			
			//验证资产用途
			subResult = checkPurpose(purpose);
			if (subResult != 0) {
				result = 5;
				return;
			}
			
			Asset asset = new Asset();
			asset.setUserid(globle_user.getId());//用户ID
			asset.setCreate_date(new Date());//创建日期
			if(globle_user.getType()==2){
				asset.setStatus(0);//资产状态(1：已验证，0：未验证)
//				asset.setStatus(1);//资产状态(1：已验证，0：未验证)
			}else if(globle_user.getType()==3){
				asset.setStatus(1);//资产状态(1：已验证，0：未验证)
			}

						
//			if(!(addr.startsWith(addrType)) || addr.equals(addrType)){
//				addr = addrType + "://" + addr.trim();
//			}
			//判断资产地址是否包含http
			Pattern pattern2 = Pattern.compile("([hH][tT][tT][pP][sS]?):\\/\\/([\\w.]+\\/?)\\S*");
			Matcher matcher2 =	pattern2.matcher(addr);
			if(!matcher2.find()){
				addr="http://"+addr.trim();
			}
			
			//如果wafFlag!=null 只允许添加域名
			String addInfo = "";
			//判断http协议
			if(addr.indexOf("http://")!=-1){
				if(addr.substring(addr.length()-1).indexOf("/")!=-1){
					addInfo = addr.trim().substring(7,addr.length()-1);
				}else{
					addInfo = addr.trim().substring(7,addr.length());
				}
			}else if(addr.indexOf("https://")!=-1){
				if(addr.substring(addr.length()-1).indexOf("/")!=-1){
					addInfo = addr.trim().substring(8,addr.length()-1);
				}else{
					addInfo = addr.trim().substring(8,addr.length());
				}
			}
			
			//判断资产地址是否是域名
			String hostnameRegex ="^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";
			boolean flag=addInfo.matches(hostnameRegex);
			if(!flag){
				result = 6;
				return;
			}
			
			asset.setName(name);
			asset.setAddr(addr);
			asset.setPurpose(purpose);
			asset.setDistrictId(prov);
			asset.setCity(city);
			assetService.saveAsset(asset);

			//获取服务对象资产
		    List<Asset> serviceAssetList = selfHelpOrderService.findServiceAsset(globle_user.getId());
		    
		    String IpAddressRegex ="^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
		    List assList = new ArrayList();
		    boolean ipflag=false;
		    if(serviceAssetList!=null&&serviceAssetList.size()>0){
		    	for(int i=0;i<serviceAssetList.size();i++){
		    		Asset newAsset = (Asset)serviceAssetList.get(i);
//		    		if (newAsset.getStatus() == 0) {
//						continue;
//					}
		    		String newAddr = newAsset.getAddr();
		    		String addressInfo = "";
		    		//判断http协议
		    		if(newAddr.indexOf("http://")!=-1){
		    			if(newAddr.substring(newAddr.length()-1).indexOf("/")!=-1){
		    				addressInfo = newAddr.trim().substring(7,newAddr.length()-1);
		    			}else{
		    				addressInfo = newAddr.trim().substring(7,newAddr.length());
		    			}
		    		}else if(newAddr.indexOf("https://")!=-1){
		    			if(newAddr.substring(newAddr.length()-1).indexOf("/")!=-1){
		    				addressInfo = newAddr.trim().substring(8,newAddr.length()-1);
		    			}else{
		    				addressInfo = newAddr.trim().substring(8,newAddr.length());
		    			}
		    		}
		    		//判断资产地址是否是域名
//		    		if(addInfo.matches(hostnameRegex)){
//		    			assList.add(newAsset);
//		    		}
		    		
		    		//判断ip地址是否包含端口号
					if(addressInfo.indexOf(":")!=-1){
						String addArr[] = addressInfo.split(":");
						ipflag = addArr[0].matches(IpAddressRegex);
						if(ipflag==false){
							flag=addArr[0].matches(hostnameRegex);
						}
					}else{
						ipflag = addressInfo.matches(IpAddressRegex);
					}
					if(ipflag==false){
                        //判断资产地址是否是域名
							flag=addressInfo.matches(hostnameRegex);
							if(flag){
								Asset  assetInfo = new Asset();
								assetInfo.setAddr(newAsset.getAddr());
								assetInfo.setId(newAsset.getId());
								assetInfo.setName(newAsset.getName());
								assetInfo.setIp(newAsset.getIp());
								assetInfo.setStatus(newAsset.getStatus());
								assList.add(assetInfo);
							}
						}
		    	}
		    }
		    m.put("serviceAssetList", assList);
		}catch (Exception e) {
			result = 7;
			e.printStackTrace();
		}finally {
			m.put("result", result);
			m.put("subResult", subResult);
			
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
     * 功能描述：查询资产对应的省份
     * 参数描述：HttpServletRequest request
	 * @throws Exception 
     *       @time 2016-08-18
     */
	public static String getProvinceInfo(String addr){
		 String IpAddressRegex ="^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
		String hostnameRegex ="^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";
		String addInfo="";
		boolean ipflag=false;
		boolean flag=false;
	  String assetAddr="";
	  String ipAsset="";
	  String province="";
	  List<String> IpInfo = new ArrayList();
	  AddressUtils addressUtils = new  AddressUtils();
	  try{
		//判断http协议
		if(addr.indexOf("http://")!=-1){
		  	if(addr.substring(addr.length()-1).indexOf("/")!=-1){
		  		addInfo = addr.trim().substring(7,addr.length()-1);
		  	}else{
		  		addInfo = addr.trim().substring(7,addr.length());
		  	}
		}else if(addr.indexOf("https://")!=-1){
			if(addr.substring(addr.length()-1).indexOf("/")!=-1){
		  		addInfo = addr.trim().substring(8,addr.length()-1);
		  	}else{
		  		addInfo = addr.trim().substring(8,addr.length());
		  	}
		}	
		//判断ip地址是否包含端口号
		if(addInfo.indexOf(":")!=-1){
			String addArr[] = addInfo.split(":");
			ipflag = addArr[0].matches(IpAddressRegex);
			if(ipflag==false){
				//判断是否是域名
				flag=addArr[0].matches(hostnameRegex);
				if(flag){
					assetAddr=addArr[0];
				}
			}else{
				ipAsset=addArr[0];
			}
		}else{
			//判断是否是ip地址
			ipflag = addInfo.matches(IpAddressRegex);
			if(ipflag){
				ipAsset=addInfo;
			}
		    flag=addInfo.matches(hostnameRegex);
		    if(flag){
		    	assetAddr=addInfo;
		    }
		}
		//循环域名，判断域名对应的ip地址，并根据ip地址获取省份；
		if(assetAddr!=null&&!"".equals(assetAddr)){
			 System.out.println("addInfo=="+addInfo);
			    //根据域名找出域名绑定下得所有ip地址
				InetAddress[] addresses=InetAddress.getAllByName(addInfo);
				for(InetAddress addr1:addresses)
				{
					IpInfo.add(addr1.getHostAddress());
		      }
				
			//根据ip地址查询省份
				if(IpInfo!=null&&!"".equals(IpInfo)){
					System.out.println("ip=="+IpInfo.get(0));
					province=addressUtils.GetAddressByIp(IpInfo.get(0));
				}
				
		}
		if(ipAsset!=null&&!"".equals(ipAsset)){
			province=addressUtils.GetAddressByIp(ipAsset);
		}
	  }catch(Exception e){
		  e.printStackTrace();
		  province="";
	  }
		return province;
	}
	/**
	 * 功能描述： 更新所有的资产对应的省份
	 * 参数描述： Model model
	 * @throws Exception 
	 * @throws Exception 
	 *		 @time 2015-1-16
	 */
	@RequestMapping(value="/updateAssetProvince.html")
	public void updateAssetProvince(HttpServletRequest request) throws Exception{
		//查询所有的资产列表
		List assetInfo = assetService.findAllAssetInfo();
		if(assetInfo!=null&&assetInfo.size()>0){
			for(int i=0;i<assetInfo.size();i++){
				System.out.println("i=="+i);
				Asset asset =(Asset)assetInfo.get(i);
				String assetAddr = asset.getAddr();
				//获得资产地址所在的省份
				String province = getProvinceInfo(assetAddr);
				if(province!=null&&!"".equals(province)){
					System.out.println("i=="+i);
					System.out.println("province=="+province);
					//更新资产对应的省份
					String provinceId = assetService.getProvinceIdByName(province.substring(0,2));
					if(provinceId!=null&&!"".equals(provinceId)){
						asset.setAssetProvince(Integer.parseInt(provinceId));
					}
					
				}else{
				    asset.setAssetProvince(35);
				}
				assetService.updateAsset(asset);
			}
			System.out.println("更新资产所在的省份完成!");
		}
	}
}
