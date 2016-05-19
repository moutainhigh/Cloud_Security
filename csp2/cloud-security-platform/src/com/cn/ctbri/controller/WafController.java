package com.cn.ctbri.controller;



import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.util.CommonUtil;


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
	 *     @time 2016-5-18
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
				  		addInfo = addr.trim().substring(8,addr.length()-1);
				  	}else{
				  		addInfo = addr.trim().substring(8,addr.length());
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
	 /**
	 * 功能描述：判断ip地址是否与域名绑定的一致
	 * 参数描述：  无
	 * add gxy
	 * @throws Exception 
	 *     @time 2016-5-19
	 */
	@RequestMapping(value="VerificationIP.html")
	public void VerificationIP(HttpServletRequest request,HttpServletResponse response) throws Exception{
		  Map<String, Object> m = new HashMap<String, Object>();
		//ip地址
		String ipStr = request.getParameter("ipVal");
		//域名
		String domainName = request.getParameter("domainName");
		String addInfo = "";
		//判断http协议
		if(domainName.indexOf("http://")!=-1){
		  	if(domainName.substring(domainName.length()-1).indexOf("/")!=-1){
		  		addInfo = domainName.trim().substring(7,domainName.length()-1);
		  	}else{
		  		addInfo = domainName.trim().substring(7,domainName.length());
		  	}
		}else if(domainName.indexOf("https://")!=-1){
			if(domainName.substring(domainName.length()-1).indexOf("/")!=-1){
		  		addInfo = domainName.trim().substring(8,domainName.length()-1);
		  	}else{
		  		addInfo = domainName.trim().substring(8,domainName.length());
		  	}
		}
	    List<String> IpInfo = new ArrayList();
	    //根据域名找出域名绑定下得所有ip地址
		InetAddress[] addresses=InetAddress.getAllByName(addInfo);
		for(InetAddress addr:addresses)
		{
			IpInfo.add(addr.getHostAddress());
        }
          String array[]=IpInfo.toArray(new String[]{});
          boolean flag=true;
          //页面输入的ip地址
          String ipArr[]= ipStr.split(",");
          for(int i=0;i<ipArr.length;i++){
        	  for(int k=0;k<array.length;k++){
        		    if(!ipArr[i].equals(array[k])){
        		    	flag=false;
        		    	break;
        		    }
        	  }
          }
	         m.put("flag", flag);
		   JSONObject JSON = CommonUtil.objectToJson(response, m);
	        // 把数据返回到页面
            CommonUtil.writeToJsp(response, JSON);
	}
	
}
