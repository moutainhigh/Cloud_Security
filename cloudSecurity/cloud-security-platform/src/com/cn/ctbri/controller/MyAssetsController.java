package com.cn.ctbri.controller;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.OrderAsset;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderAssetService;
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
		return "/source/page/userCenter/userAssets";
	}
	/**
	 * 功能描述： 添加资产
	 * 参数描述： Model model
	 *		 @time 2015-1-16
	 */
	@RequestMapping("/addAsset.html")
	public String addAsset(Model model,Asset asset,HttpServletRequest request){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		asset.setUserid(globle_user.getId());//用户ID
		asset.setCreate_date(new Date());//创建日期
		asset.setStatus(0);//资产状态(1：已验证，0：未验证)
		String name = "";//资产名称
		//处理页面输入中文乱码的问题
		try {
			//name = URLDecoder.decode(asset.getName(), "UTF-8");
			name=new String(asset.getName().getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		asset.setName(name);
		String addr = "";//资产地址
		try {
			addr=new String(asset.getAddr().getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		asset.setAddr(addr);
		assetService.saveAsset(asset);
		return "redirect:/userAssetsUI.html";
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
	 * 功能描述：删除资产
	 * 参数描述： Model model
	 *		 @time 2015-1-19
	 */
	@RequestMapping("/deleteAsset.html")
	public String delete(Asset asset){
		//检查订单资产表里面是否含有此资产
		List<OrderAsset> list = orderAssetService.findAssetById(asset.getId());
		int count = 0;
		if(list.size()>0){
			count = list.size();
		}
		
		assetService.delete(asset.getId());
		return "redirect:/userAssetsUI.html";
	}
	/**
	 * 功能描述：联合搜索
	 * 参数描述： Model model,Asset asset,HttpServletRequest request
	 *		 @time 2015-1-19
	 */
	@RequestMapping("/searchAssetCombine.html")
	public String searchAssetsCombine(Model model,Asset asset,HttpServletRequest request){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		asset.setUserid(globle_user.getId());//将用户登录用户的id赋值到asset中
		List<Asset> result = assetService.searchAssetsCombine(asset);//根据userid 资产状态 和资产名称联合查询
		model.addAttribute("list",result);		//传对象到页面
		model.addAttribute("status",asset.getStatus());//回显资产类型	
		model.addAttribute("name",asset.getName());//回显资产名称
		return "/source/page/userCenter/userAssets";
	}
	/**
	 * 功能描述：资产验证
	 * 参数描述： Asset asset
	 *		 @time 2015-1-19
	 *	返回值：redirect:/userAssetsUI.html
	 */
	@RequestMapping("/verificationAsset.html")
	public String verificationAsset(Asset asset,HttpServletRequest request){
		int id = asset.getId();
		//获取验证方式:代码验证 上传文件验证
		String verification_msg = asset.getVerification_msg();
		//代码验证
		if(verification_msg.equals("代码验证")){
			//获取已知代码
			String code = (String) request.getAttribute("code");
			
		}
		//上传文件验证
		if(verification_msg.equals("上传文件验证")){
			
		}
		//如果验证成功，将验证状态设为1
		//如果验证失败，提示错误信息
		return "redirect:/userAssetsUI.html";
	}
}
