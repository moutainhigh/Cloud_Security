package com.cn.ctbri.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.ctbri.dao.SelfHelpOrderDao;
import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Factory;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderDetail;
import com.cn.ctbri.entity.OrderList;
import com.cn.ctbri.entity.ScanType;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.ServiceType;
import com.cn.ctbri.entity.ShopCar;
import com.cn.ctbri.service.ISelfHelpOrderService;
/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-1-14
 * 描        述：  自助下单层实现类
 * 版        本：  1.0
 */
@Service
public class SelfHelpOrderServiceImpl implements ISelfHelpOrderService{

    @Autowired
    SelfHelpOrderDao selfHelpOrderDao;
    /**
     * 功能描述：查询服务配置类型
     *       @time 2015-1-14
     * 返回值    ：  List<ServiceType>
     */
    public List<ServiceType> findServiceType() {
        return selfHelpOrderDao.getServiceType();
    }
    
    /**
     * 功能描述：查询厂商
     *       @time 2015-1-15
     * 返回值    ：  List<Factory>
     */
    public List<Factory> findListFactory() {
        return selfHelpOrderDao.getFactory();
    }
    
    /**
     * 功能描述：查询服务资产
     * @param userId 
     *       @time 2015-1-15
     * 返回值    ：  List<ServiceAsset>
     */
    public List<Asset> findServiceAsset(int userId) {
        return selfHelpOrderDao.getServiceAsset(userId);
    }

    /**
     * 功能描述：保存订单
     *       @time 2015-1-16
     */
    public void insertOrder(Order order) {
        selfHelpOrderDao.save(order);
    }

    /**
     * 功能描述：查询服务
     *       @time 2015-1-19
     * 返回值    ：  List<Serv>
     */
    public List findService() {
        return selfHelpOrderDao.getService();
    }

    /**
     * 功能描述：保存联系人
     *       @time 2015-1-19
     */
    public void insertLinkman(Linkman linkObj) {
        selfHelpOrderDao.saveLinkman(linkObj);
    }

    /**
     * 功能描述：查询漏洞个数
     *       @time 2015-3-9
     */
    public int findLeakNum(int i) {
        return selfHelpOrderDao.findLeakNum(i);
    }

    /**
     * 功能描述：查询网页数
     *       @time 2015-3-9
     */
    public int findWebPageNum() {
        return selfHelpOrderDao.findWebPageNum();
    }

    /**
     * 功能描述：检测网页数
     *       @time 2015-6-29
     */
    public int findWebSite() {
        return selfHelpOrderDao.findWebSite();
    }

    /**
     * 功能描述：断网次数
     *       @time 2015-6-29
     */
    public int findBrokenNetwork() {
        return selfHelpOrderDao.findBrokenNetwork();
    }

   //删除联系人
    public void deleteLinkman(int contactId,int userId) {
        selfHelpOrderDao.deleteLinkman(contactId,userId);
    }

	public Serv findServiceById(int serviceId) {
		return selfHelpOrderDao.findServiceById(serviceId);
	}
  
	//查询购物车列表
	public List<ShopCar> findShopCarList(String userId, int payFlag,String orderId) {
		// TODO Auto-generated method stub
		
		return selfHelpOrderDao.findShopCarList(userId, payFlag,orderId);
	}

	public List<ShopCar> findShopCarAPIList(String userId, int payFlag,String orderId) {
		// TODO Auto-generated method stub
		return selfHelpOrderDao.findShopCarAPIList(userId, payFlag,orderId);
	}
	
	public List<ShopCar> findShopCarSysList(String userId, int payFlag, String orderId) {
		// TODO Auto-generated method stub
		return selfHelpOrderDao.findShopCarSysList(userId, payFlag,orderId);
	}

	public List<ShopCar> findBuyShopList(List orderId,int userId) {
		// TODO Auto-generated method stub
		return selfHelpOrderDao.findBuyShopList(orderId,userId);
	}

	public void updateOrder(String orderId, String newOrderId,String isAPI,String status,String orderListId,Date creatDate) {
		// TODO Auto-generated method stub
		selfHelpOrderDao.updateOrder(orderId, newOrderId,isAPI,status,orderListId,creatDate);
	}
	
	public void updateSysOrder(String orderId, String newOrderId, String isAPI,
			String status, String orderListId, Date creatDate, String remarks) {
		// TODO Auto-generated method stub
		selfHelpOrderDao.updateSysOrder(orderId, newOrderId,isAPI,status,orderListId,creatDate,remarks);
	}

	public void updateOrderAPI(String orderId, String newOrderId) {
		// TODO Auto-generated method stub
		selfHelpOrderDao.updateOrderAPI(orderId, newOrderId);
	}

	public void updateOrderAsset(String orderId, String newOrderId) {
		// TODO Auto-generated method stub
		selfHelpOrderDao.updateOrderAsset(orderId, newOrderId);
	}

	public void updateShopOrder(ShopCar order) {
		// TODO Auto-generated method stub
		selfHelpOrderDao.updateShopOrder(order);
	}

	public void updateOrderPayFlag(String orderIds, int payFlag) {
		selfHelpOrderDao.updateOrderPayFlag(orderIds, payFlag);
	}
	
	public void updatePayDate(OrderList ol) {
		selfHelpOrderDao.updatePayDate(ol);
	}

	public void updateOrderStatus(String orderIds, int status) {
		selfHelpOrderDao.updateOrderStatus(orderIds, status);
	}
	
	//修改订单开始时间和结束时间
	public void updateOrderDate(ShopCar order){
		selfHelpOrderDao.updateOrderDate(order);
	}
	//修改订单API开始时间和结束时间
	public void updateOrderAPIDate(ShopCar order){
		selfHelpOrderDao.updateOrderAPIDate(order);
	}

	public void updateOrderListId(String newOrderIds) {
		selfHelpOrderDao.updateOrderListId(newOrderIds);
	}
	
	public int findOrderCountByUserId(List<String> orderId, int userId){
		return selfHelpOrderDao.findOrderCountByUserId(orderId, userId);
	}

	public void SaveOrderDetail(OrderDetail orderDetail) {
		selfHelpOrderDao.SaveOrderDetail(orderDetail);
	}

	
	public OrderDetail getOrderDetailById(String id,int userId,List assetIdsList) {
		return selfHelpOrderDao.getOrderDetailById(id,userId,assetIdsList);
	}

	public OrderDetail getOrderAPIDetailById(String id, int userId) {
		return selfHelpOrderDao.getOrderAPIDetailById(id, userId);
	}

	
	public OrderDetail findOrderDetailById(String id, int userId) {
		return selfHelpOrderDao.findOrderDetailById(id, userId);
	}
	
	public List<Serv> findServiceByParent(int parent) {
		return selfHelpOrderDao.findServiceByParent(parent);
	}

	

	

	
}
