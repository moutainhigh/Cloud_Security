package com.cn.ctbri.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.User;
import com.cn.ctbri.service.IAlarmService;
import com.cn.ctbri.service.IAssetService;
import com.cn.ctbri.service.IOrderAssetService;
import com.cn.ctbri.service.IOrderService;
import com.cn.ctbri.service.ISelfHelpOrderService;
import com.cn.ctbri.service.IServService;
import com.cn.ctbri.service.IServiceAPIService;
import com.cn.ctbri.service.ITaskHWService;
import com.cn.ctbri.service.ITaskService;
import com.cn.ctbri.service.ITaskWarnService;

/**
 * 创 建 人  ：  tang
 * 创建日期：  2016-3-10
 * 描        述：  订单管理控制层
 * 版        本：  1.0
 */
@Controller
public class shoppingController {
	
    @Autowired
    ISelfHelpOrderService selfHelpOrderService;
    @Autowired
    IOrderService orderService;
    @Autowired
    IOrderAssetService orderAssetService;
    @Autowired
    IAssetService assetService;
    @Autowired
    IServService servService;
    @Autowired
    ITaskService taskService;
    @Autowired
    ITaskHWService taskhwService;
    @Autowired
    IAlarmService alarmService;
    @Autowired
    ITaskWarnService taskWarnService;
    @Autowired
    IServiceAPIService serviceAPIService;
    
	 /**
	 * 功能描述： 购买检测服务
	 * 参数描述：  无
	 *     @time 2016-3-12
	 */
	@RequestMapping(value="selfHelpOrderInit1.html")
	public String selfHelpOrderInit(HttpServletRequest request){
	    User globle_user = (User) request.getSession().getAttribute("globle_user");
	    int serviceId = Integer.parseInt(request.getParameter("serviceId"));
	    //是否从首页进入
	    String indexPage = request.getParameter("indexPage");
	    //根据id查询service add by tangxr 2016-3-14
	    Serv service = servService.findById(serviceId);
	    //获取服务对象资产
	    List<Asset> serviceAssetList = selfHelpOrderService.findServiceAsset(globle_user.getId());
        request.setAttribute("serviceAssetList", serviceAssetList);
        request.setAttribute("serviceId", serviceId);
        request.setAttribute("indexPage", indexPage);
        request.setAttribute("service", service);
        String result = "/source/page/details/vulnScanDetails";
        return result;
	}
	
	 /**
	 * 功能描述： 结算
	 * 参数描述：  无
	 *     @time 2016-3-10
	 */
	@RequestMapping(value="settlement1.html")
	public String settlement(HttpServletRequest request){
		User globle_user = (User) request.getSession().getAttribute("globle_user");
		//资产ids
        String assetIds = request.getParameter("assetIds");
		String orderType = request.getParameter("orderType");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
//       String createDate = DateUtils.dateToString(new Date());
        String scanPeriod = request.getParameter("scanType");
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        //联系人信息
//       String linkname = new String(request.getParameter("linkname").getBytes("ISO-8859-1"),"UTF-8");
//       String phone = request.getParameter("phone");
//       String email = request.getParameter("email");
//       String company = new String(request.getParameter("company").getBytes("ISO-8859-1"),"UTF-8");
//       String address = new String(request.getParameter("address").getBytes("ISO-8859-1"),"UTF-8");
       //华为参数
//       String ip = request.getParameter("ip");
//       String bandwidth = request.getParameter("bandwidth");
       //厂商
//       String websoc = request.getParameter("websoc");
       //任务数
//       String tasknum = request.getParameter("tasknum");
       
        //根据id查询service
	    Serv service = servService.findById(serviceId);
	    String[] assetArray = null;
	    String assetAddr = "";
        assetArray = assetIds.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
        for(int i=0;i<assetArray.length;i++){
        	Asset asset = assetService.findById(Integer.parseInt(assetArray[i]));
        	assetAddr = assetAddr + asset.getAddr()+",";
        }
        assetAddr = assetAddr.substring(0, assetAddr.length()-1);
	    
        request.setAttribute("user", globle_user);
	    request.setAttribute("assetAddr", assetAddr);
	    request.setAttribute("assetIds", assetIds);
	    request.setAttribute("orderType", orderType);
        request.setAttribute("beginDate", beginDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("scanType", scanPeriod);
        request.setAttribute("serviceId", serviceId);
        request.setAttribute("service", service);
        request.setAttribute("mark", "web");//web服务标记
        String result = "/source/page/details/settlement";
        return result;
	}
	
	
	/**
	 * 功能描述： 购物车
	 * 参数描述：  无
	 *     @time 2016-3-15
	 */
	@RequestMapping(value="shoppingCar1.html")
	public String shoppingCar(HttpServletRequest request){
		//资产ids
        String assetIds = request.getParameter("assetIds");
		String orderType = request.getParameter("orderType");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String scanPeriod = request.getParameter("scanType");
        String serviceId = request.getParameter("serviceId");
       
	    request.setAttribute("assetIds", assetIds);
	    request.setAttribute("orderType", orderType);
        request.setAttribute("beginDate", beginDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("scanType", scanPeriod);
        request.setAttribute("serviceId", serviceId);
        String result = "/source/page/details/shoppingCart-order";
        return result;
	}
	
	

	
}
