package com.cn.ctbri.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.ctbri.dao.DaoCommon;
import com.cn.ctbri.dao.SelfHelpOrderDao;
import com.cn.ctbri.entity.Alarm;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Factory;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderDetail;
import com.cn.ctbri.entity.OrderList;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.ServiceType;
import com.cn.ctbri.entity.ShopCar;
/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-1-14
 * 描        述：   自助下单数据访问层实现类
 * 版        本：  1.0
 */
@Repository
public class SelfHelpOrderDaoImpl extends DaoCommon implements SelfHelpOrderDao{

	
	/**
	 * 功        能：SelfHelpOrderMapper命名空间
	 */
	private String ns = "com.cn.ctbri.entity.SelfHelpOrderMapper.";
	/**
     * 功        能：ServMapper命名空间
     */
	private String nv = "com.cn.ctbri.entity.ServMapper.";
	
	/**
     * 功能描述：查询服务配置类型
     *       @time 2015-1-14
     * 返回值    ：  List<ServiceType>
     */
	public List<ServiceType> getServiceType() {
		return this.getSqlSession().selectList(ns + "findServiceType");
	}

	/**
     * 功能描述：查询厂商
     *       @time 2015-1-15
     * 返回值    ：  List<Factory>
     */
    public List<Factory> getFactory() {
        return this.getSqlSession().selectList(ns + "findFactory");
    }

    /**
     * 功能描述：查询服务资产
     *       @time 2015-1-15
     * 返回值    ：  List<ServiceAsset>
     */
    public List<Asset> getServiceAsset(int userId ) {
        return this.getSqlSession().selectList(ns + "findServiceAsset",userId);
    }

    /**
     * 功能描述：保存订单
     *       @time 2015-1-16
     */
    public void save(Order order) {
        this.getSqlSession().insert(ns + "insert", order);
    }

    /**
     * 功能描述：查询服务
     *       @time 2015-1-19
     * 返回值    ：  List<Serv>
     */
    public List getService() {
        return this.getSqlSession().selectList(nv + "list");
    }

    /**
     * 功能描述：保存联系人
     *       @time 2015-1-19
     */
    public void saveLinkman(Linkman linkObj) {
        this.getSqlSession().insert(ns + "insertLinkman", linkObj);
    }

    /**
     * 功能描述：查询漏洞个数
     *       @time 2015-3-9
     */
    public int findLeakNum(int i) {
        List<Alarm> leakList = this.getSqlSession().selectList(ns + "findLeakNum",i);
        return leakList.get(0).getCount();
    }

    /**
     * 功能描述：查询网页数
     *       @time 2015-3-9
     */
    public int findWebPageNum() {
        List<HashMap<String, Object>> webPageList = this.getSqlSession().selectList(ns + "findWebPageNum");
        double i = 0;
        if(webPageList.get(0)!=null){
        	i = (Double) webPageList.get(0).get("url");
        }
        return (int)i;
    }

    /**
     * 功能描述：检测网页数
     *       @time 2015-6-29
     */
    public int findWebSite() {
        List webSiteList = this.getSqlSession().selectList(ns + "findWebSite");
        return webSiteList.size();
    }

    /**
     * 功能描述：断网次数
     *       @time 2015-6-29
     */
    public int findBrokenNetwork() {
        List brokenNetworkList = this.getSqlSession().selectList(ns + "findBrokenNetwork");
        return brokenNetworkList.size();
    }

    public void deleteLinkman(int contactId,int userId) {
    	Map map = new HashMap();
    	map.put("contactId", contactId);
    	map.put("userId", userId);
        this.getSqlSession().delete(ns + "deleteLinkman",contactId);
    }

	public Serv findServiceById(int serviceId) {
		return this.getSqlSession().selectOne(nv + "findById", serviceId);
	}

	public List<ShopCar> findShopCarList(String userId, int payFlag,String orderId) {
		Map carMap = new HashMap();
		 carMap.put("userId", userId);
		 carMap.put("payFlag", payFlag);
		 carMap.put("orderId", orderId);
		 return this.getSqlSession().selectList(ns + "findShopCarList",carMap);
	}

	public List<ShopCar> findShopCarAPIList(String userId, int payFlag,String orderId) {
		Map carMap = new HashMap();
		 carMap.put("userId", userId);
		 carMap.put("payFlag", payFlag);
		 carMap.put("orderId", orderId);
		 return this.getSqlSession().selectList(ns + "findShopCarAPIList",carMap);
	}
	public List<ShopCar> findShopCarSysList(String userId, int payFlag, String orderId) {
		// TODO Auto-generated method stub
		Map carMap = new HashMap();
		 carMap.put("userId", userId);
		 carMap.put("payFlag", payFlag);
		 carMap.put("orderId", orderId);
		 return this.getSqlSession().selectList(ns + "findShopCarSysList",carMap);
	}


	public List<ShopCar> findBuyShopList(List orderIds,int userId) {
		Map map = new HashMap();
		map.put("orderIds", orderIds);
		map.put("userId", userId);
		return this.getSqlSession().selectList(ns + "findBuyShopList",map);
	}

	public void updateOrder(String orderId, String newOrderId,String isAPI,String status,String orderListId, Date createDate) {
		Map map = new HashMap();
		map.put("orderId", orderId);
		map.put("newOrderId", newOrderId);
		map.put("isAPI", isAPI);
		map.put("status", status);
		map.put("orderListId", orderListId);
		map.put("createDate", createDate);
		this.getSqlSession().update(ns+"updateOrder",map);
	}

	public void updateSysOrder(String orderId, String newOrderId, String isAPI,
			String status, String orderListId, Date creatDate, String remarks) {
		
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("orderId", orderId);
		map.put("newOrderId", newOrderId);
		map.put("isAPI", isAPI);
		map.put("status", status);
		map.put("orderListId", orderListId);
		map.put("createDate", creatDate);
		map.put("remarks", remarks);
		this.getSqlSession().update(ns+"updateSysOrder",map);
	}

	
	public void updateOrderAPI(String orderId, String newOrderId) {
		Map map = new HashMap();
		map.put("orderId", orderId);
		map.put("newOrderId", newOrderId);
		this.getSqlSession().update(ns+"updateOrderAPI",map);
	}

	public void updateOrderAsset(String orderId, String newOrderId) {
		Map map = new HashMap();
		map.put("orderId", orderId);
		map.put("newOrderId", newOrderId);
	
		this.getSqlSession().update(ns+"updateOrderAsset",map);
	}

	public void updateShopOrder(ShopCar order) {
		this.getSqlSession().update(ns+"updateShopOrder",order);
	}
	
	public void updatePayDate(OrderList ol) {
		getSqlSession().update(ns + "updatePayDate", ol);
	}
	
	public void updateOrderPayFlag(String orderIds, int payFlag) {
		Map map = new HashMap();
		map.put("orderId", orderIds);
		map.put("payFlag", payFlag);
		
		
		this.getSqlSession().update(ns + "updatePayFlag", map);
	}

	public void updateOrderStatus(String orderIds, int status) {
		Map map = new HashMap();
		map.put("orderId", orderIds);
		map.put("status", status);
		
		this.getSqlSession().update(ns + "updateOrderStatus", map);
	}
	
	//修改订单开始时间和结束时间
	public void updateOrderDate(ShopCar order) {
		this.getSqlSession().update(ns+"updateOrderDate",order);
	}
	//修改订单API开始时间和结束时间
	public void updateOrderAPIDate(ShopCar order){
		this.getSqlSession().update(ns+"updateOrderAPIDate",order);
	}

	public void updateOrderListId(String newOrderIds) {
		this.getSqlSession().update(ns+"updateOrderListId",newOrderIds);
	}

	public int findOrderCountByUserId(List<String> orderIds, int userId){
		Map map = new HashMap();
		map.put("orderIds", orderIds);
		map.put("userId", userId);
		return this.getSqlSession().selectOne(ns+"findOrderCountByUserId",map);
	}

	public void SaveOrderDetail(OrderDetail orderDetail) {
		this.getSqlSession().insert(ns+"SaveOrderDetail",orderDetail);
	}

	
	public OrderDetail getOrderDetailById(String id,int userId,List assetIdsList) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("userId", userId);
		map.put("assetIdsList", assetIdsList);
		return this.getSqlSession().selectOne(ns+"getOrderDetailById", map);
	}
	
	public OrderDetail getOrderAPIDetailById(String id, int userId) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("userId", userId);
		return this.getSqlSession().selectOne(ns+"getOrderAPIDetailById", map);
	}

	
	public OrderDetail findOrderDetailById(String id, int userId) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("userId", userId);
		return this.getSqlSession().selectOne(ns+"findOrderDetailById", map);
	}
	
	public List<Serv> findServiceByParent(int parent) {
		Map map = new HashMap();
		map.put("parentC", parent);
		return this.getSqlSession().selectList(nv+"getServiceByParent", map);
	}

	
	
}
