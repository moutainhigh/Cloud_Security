package com.cn.ctbri.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IServService;

/**
 * 创 建 人  ：  zsh
 * 创建日期：  2016-11-14
 * 描        述：  SystemServDetailController
 * 版        本：  1.0
 */
@Controller
public class SystemServDetailController {
	@Autowired
    ISelfHelpOrderService selfHelpOrderService;
	
	@Autowired
    IServService servService;
	
	/**
	 * 功能描述：系统安全帮商品详情
	 * 参数描述：  无
	 * add zsh
	 *     @time 2016-11-14
	 */
	@RequestMapping(value="systemAnquanbangDetailUI.html")
	public String systemServDetails(HttpServletRequest request){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		int serviceId = Integer.parseInt(request.getParameter("serviceId"));
		//判断serviceId是否存在
		List<Serv> serList = servService.findAllService();
		boolean flag = false;
		if(serList!=null && serList.size()>0){
			for(int i = 0; i < serList.size(); i++){
				if(serviceId==serList.get(i).getId()){
					flag = true;
				}
			}
		}
		if(!flag){
			return "redirect:/index.html";
		}
		//是否从首页进入
		String indexPage = request.getParameter("indexPage");
		//根据id查询service add by tangxr 2016-3-14
		Serv service = servService.findById(serviceId);
		
		//获取服务对象资产
		List<Asset> serviceAssetList = selfHelpOrderService.findServiceAsset(globle_user.getId());
		//网站安全帮列表
		List shopCarList = selfHelpOrderService.findShopCarList(String.valueOf(globle_user.getId()), 0,"");
		//查询安全能力API
		List apiList = selfHelpOrderService.findShopCarAPIList(String.valueOf(globle_user.getId()), 0,"");
		// 查询系统安全帮
				List sysList = selfHelpOrderService.findShopCarSysList(String.valueOf(globle_user.getId()), 0, "");
				int carnum = shopCarList.size() + apiList.size() + sysList.size();
		
		request.setAttribute("carnum", carnum);  
        request.setAttribute("serviceAssetList", serviceAssetList);
        request.setAttribute("serviceId", serviceId);
        request.setAttribute("indexPage", indexPage);
        request.setAttribute("service", service);
        if(serviceAssetList!=null && serviceAssetList.size()>0){
            request.setAttribute("AssetCount", serviceAssetList.size());
        }else{
        	request.setAttribute("AssetCount",0);
        }
        
        String result = "/source/page/details/systemServDetails";
        return result;
	}

}
