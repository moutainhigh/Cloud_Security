package com.cn.ctbri.controller;

import java.io.IOException;
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

import com.cn.ctbri.cfg.CspWorker;
import com.cn.ctbri.entity.ApiPrice;
import com.cn.ctbri.entity.Price;
import com.cn.ctbri.entity.ScanType;
import com.cn.ctbri.entity.ServiceDetail;
import com.cn.ctbri.service.IScanTypeService;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.util.CommonUtil;

/**
 * 创 建 人  ：  zhang_shaohua
 * 创建日期：  2016-9-19
 * 描        述：  后台服务管理
 * 版        本：  1.0
 */
@Controller
public class PriceController {
	@Autowired
	IServService servService;
	@Autowired
    ISelfHelpOrderService selfHelpOrderService;
	@Autowired
	IScanTypeService scanTypeService;
	
	/**
	 * 功能描述：服务价格维护页面
	 *		 @time 2016-9-19
	 */
	@RequestMapping("/servicePriceMaintainUI.html")
	public void servicePriceMaintainUI(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		//获取服务类型
		int serviceId = Integer.parseInt(request.getParameter("servId"));
		int parentC = Integer.parseInt(request.getParameter("parentC"));
		
		//获取 类型(0:单次和长期,1:长期,2:单次)
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serviceId", serviceId);
		map.put("parent", parentC);
		ServiceDetail serviceDetail = selfHelpOrderService.findServiceDetail(map);
//		Serv serv = servService.findById(serviceId);
		if (serviceDetail == null) {
			m.put("detailFlag", false);
			return;
		}
		m.put("detailFlag", true);
		m.put("orderType", serviceDetail.getServType());
		//获取单次价格
		if (serviceDetail.getServType() != 1) {
			Map<String,Object> newMap = new HashMap<String,Object>();
			newMap.put("serviceId", serviceId);
			newMap.put("type", 0);    //(0:单次；1：长期；2：大于)
			List<Price> priceList = servService.findPriceByParam(newMap);
			if(priceList.size() !=0) {
				m.put("singlePrice", priceList.get(0).getPrice());
			}
		}
		
		//获取 服务频率
		List<ScanType> scanTypeList = scanTypeService.findByServiceId(serviceId);
		m.put("scanTypeList", scanTypeList);
		//获取长期价格list
		List<Price> priceList = servService.findLongPriceByServiceId(serviceId);
		m.put("longPriceList", priceList);
		
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
	 * 功能描述：服务价格维护
	 *		 @time 2016-9-20
	 */
	@RequestMapping("/servicePriceMaintain.html")
	public void servicePriceMaintain(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		List<Price> priceList = new ArrayList<Price>();
		try {
			//获取服务Id
			int serviceId = Integer.valueOf(request.getParameter("add_serviceId"));
			//变更前的价钱  delFlag设为1(已删除)
			servService.updatePriceDeleteFlag(serviceId);
			
			//获取单次服务价格
			String singlePriceStr = request.getParameter("singlePrice");
			if (singlePriceStr != null) {
				Price price = new Price();
				price.setServiceId(serviceId);
				price.setType(0);   //0:单次；1：长期；2：大于
				price.setPrice(Double.valueOf(singlePriceStr));
				price.setTimesG(0);
				price.setTimesLE(0);
				servService.insertPrice(price);
				priceList.add(price);
			}
			
			int maxPriceIndex = Integer.valueOf(request.getParameter("maxPriceIndex"));
			//获取长期服务价格
			for (int i=0;i <= maxPriceIndex;i++) {
				String timesGStr  = request.getParameter("timesG_" + i);
				if (timesGStr == null || timesGStr.equals("")) {
					continue;
				}
				Integer timesG = Integer.valueOf(timesGStr);
				if(i>=1&&i<=maxPriceIndex){
					String timesGStr1  = request.getParameter("timesG_" +(i+1));
					if(timesGStr1!=null&&!"".equals(timesGStr1)){
						Integer timesG1 = Integer.valueOf(timesGStr1);
						if(timesG==timesG1){
							m.put("success", false);
							return;
						}
					}
				}
				Double priceValue = Double.valueOf(request.getParameter("price_"+ i));
				String scanTypeStr = request.getParameter("price_scanType_"+ i);
				Integer type = Integer.valueOf(request.getParameter("type_"+ i));
				// 价钱类型是 大于 的场合，DB中timesLE设为0
				Integer timesLE = 0;
				if (!type.equals(2)) {   //0:单次；1：区间；2：大于
					timesLE= Integer.valueOf(request.getParameter("timesLE_" + i));
				}
				
				//0:不根据服务频率设置的场合，DB中服务频率设为null
				Integer scanType = null;
				if (scanTypeStr != null && !scanTypeStr.equals("") && !scanTypeStr.equals("0")) {
					scanType = Integer.valueOf(scanTypeStr);
				}
				
				Price price = new Price();
				price.setServiceId(serviceId);
				price.setScanType(scanType);
				price.setType(type);
				price.setTimesG(timesG);
				price.setTimesLE(timesLE);
				price.setPrice(priceValue);
				servService.insertPrice(price);
				priceList.add(price);
			}
			String code = CspWorker.updateServicePrice(serviceId,priceList);
			if(code == null || !code.equals("200")) {
    			m.put("success", false);
    			return;
    		}
			
			m.put("success", true);
			
		} catch(Exception e){
			e.printStackTrace();
			m.put("error", true);
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
	 * 功能描述：API服务价格维护页面
	 *		 @time 2016-9-19
	 */
	@RequestMapping("/serviceAPIPriceMaintainUI.html")
	public void serviceAPIPriceMaintainUI(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		//获取服务类型
		int serviceId = Integer.parseInt(request.getParameter("servId"));
		Map<String,Object> newMap = new HashMap<String,Object>();
		newMap.put("serviceId", serviceId);
		
		//获取长期价格list
		List<ApiPrice> priceList = servService.findApiPriceByServiceId(serviceId);
		m.put("priceList", priceList);
		
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
	 * 功能描述：服务价格维护
	 *		 @time 2016-9-20
	 */
	@RequestMapping("/serviceApiPriceMaintain.html")
	public void serviceAPIPriceMaintain(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> m = new HashMap<String, Object>();
		List<ApiPrice> priceList = new ArrayList<ApiPrice>();
		try {
			//获取服务Id
			int serviceId = Integer.valueOf(request.getParameter("add_serviceApiId"));
			//变更前的价钱  delFlag设为1(已删除)
			servService.updateApiPriceDeleteFlag(serviceId);
			
			int maxPriceIndex = Integer.valueOf(request.getParameter("maxApiPriceIndex"));
			//获取长期服务价格
			for (int i=0;i <= maxPriceIndex;i++) {
				String timesGStr  = request.getParameter("apiPrice_timesG_" + i);
				
				if (timesGStr == null || timesGStr.equals("")) {
					continue; 
				}
				
				Integer timesG = Integer.valueOf(timesGStr);
				Double priceValue = Double.valueOf(request.getParameter("apiPrice_price_"+ i));
				Integer type = Integer.valueOf(request.getParameter("apiPrice_type_"+ i));
				// 价钱类型是 大于 的场合，DB中timesLE设为0
				Integer timesLE = 0;
				if (!type.equals(2)) {   //0:单次；1：区间；2：大于
					timesLE= Integer.valueOf(request.getParameter("apiPrice_timesLE_" + i));
				}
				
				ApiPrice price = new ApiPrice();
				price.setServiceId(serviceId);
				price.setTimesG(timesG);
				price.setTimesLE(timesLE);
				price.setPrice(priceValue);
				servService.insertApiPrice(price);
				priceList.add(price);
			}
			String code = CspWorker.updateServiceAPIPrice(serviceId,priceList);
			if(code == null || !code.equals("200")) {
    			m.put("success", false);
    			return;
    		}
			
			m.put("success", true);
			
		} catch(Exception e){
			e.printStackTrace();
			m.put("success", false);
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

}
