package com.cn.ctbri.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAssetService;
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
		String name = "";
		//处理页面输入中文乱码的问题
		try {
			name = URLDecoder.decode(asset.getName(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		asset.setName(name);
		assetService.saveAsset(asset);
		return "redirect:/userAssetsUI.html";
	}
	/**
	 * 功能描述：删除资产
	 * 参数描述： Model model
	 *		 @time 2015-1-19
	 */
	@RequestMapping("/deleteAsset.html")
	public String delete(Asset asset){
		//检查订单资产表里面是否含有次资产
		
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
		model.addAttribute("name1",asset.getName());//回显资产名称
		return "/source/page/userCenter/userAssets";
	}
}
