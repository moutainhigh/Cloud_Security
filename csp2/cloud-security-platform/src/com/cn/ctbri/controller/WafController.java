package com.cn.ctbri.controller;



import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAssetService;


/**
 * 创 建 人  ：  tang
 * 创建日期：  2016-5-17
 * 描        述：  wafController
 * 版        本：  1.0
 */
@Controller
public class WafController {
	@Autowired
	IAssetService assetService;
   
	 /**
	 * 功能描述：购买waf服务
	 * 参数描述：  无
	 * add gxy
	 *     @time 2016-3-12
	 */
	@RequestMapping(value="wafDetails.html")
	public String wafDetails(HttpServletRequest request){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		//查找当前用户的资产列表
		List<Asset> list = assetService.findByUserId(globle_user.getId());
		String hostnameRegex ="^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";
		boolean flag=false;
		List assList = new ArrayList();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Asset asset = (Asset)list.get(i);
				String addr = asset.getAddr();
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
				  		addInfo = addr.trim().substring(7,addr.length()-1);
				  	}else{
				  		addInfo = addr.trim().substring(7,addr.length());
				  	}
				}
				//判断资产地址是否是域名
				flag=addInfo.matches(hostnameRegex);
				if(flag){
					Asset  assetInfo = new Asset();
					assetInfo.setAddr(asset.getAddr());
					assetInfo.setId(asset.getId());
					assetInfo.setName(asset.getName());
					assetInfo.setIp(asset.getIp());
					assList.add(assetInfo);
				}
			}
		}
		  request.setAttribute("assList", assList);
	    return  "/source/page/details/wafDetails";
	}
	
	
}
