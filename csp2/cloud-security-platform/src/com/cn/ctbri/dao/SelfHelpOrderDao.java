package com.cn.ctbri.dao;

import java.util.Date;
import java.util.List;

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
/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-1-14
 * 描        述：  自助下单数据访问层接口类
 * 版        本：  1.0
 */
public interface SelfHelpOrderDao {

    /**
     * 功能描述：查询服务配置类型
     *       @time 2015-1-14
     * 返回值    ：  List<ServiceType>
     */
    List<ServiceType> getServiceType();

    /**
     * 功能描述：查询厂商
     *       @time 2015-1-15
     * 返回值    ：  List<Factory>
     */
    List<Factory> getFactory();

    /**
     * 功能描述：查询服务资产
     * @param userId 
     *       @time 2015-1-15
     * 返回值    ：  List<ServiceAsset>
     */
    List<Asset> getServiceAsset(int userId);

    /**
     * 功能描述：保存订单
     *       @time 2015-1-16
     */
    void save(Order order);

    /**
     * 功能描述：查询服务
     * @return 
     *       @time 2015-1-19
     * 返回值    ：  List<Serv>
     */
    List getService();

    /**
     * 功能描述：保存联系人
     *       @time 2015-1-19
     */
    void saveLinkman(Linkman linkObj);

    /**
     * 功能描述：查询漏洞个数
     *       @time 2015-3-9
     */
    int findLeakNum(int i);

    /**
     * 功能描述：查询网页数
     *       @time 2015-3-9
     */
    int findWebPageNum();

    /**
     * 功能描述：检测网页数
     *       @time 2015-6-29
     */
    int findWebSite();

    /**
     * 功能描述：断网次数
     *       @time 2015-6-29
     */
    int findBrokenNetwork();

    //删除联系人
    void deleteLinkman(int contactId,int userId);

	Serv findServiceById(int serviceId);
	 // 查询购物车列表
	List<ShopCar> findShopCarList(String userId,int payFlag,String orderId);

	  // 查询购物车-安全能力API列表
	List<ShopCar> findShopCarAPIList(String userId,int payFlag,String orderId);
	// 查询系统安全帮购物车列表
	List<ShopCar> findShopCarSysList(String userId, int payFlag, String orderId);
	//查询结算页的订单列表
	List<ShopCar> findBuyShopList(List orderId,int userId);
	
	//更新订单资产表
	void updateOrderAsset(String orderId,String newOrderId);
	//更新订单表
	void updateOrder(String orderId,String newOrderId,String isAPI,String status, String orderListId, Date creatDate);
	//更新系统安全帮订单列表
	void updateSysOrder(String orderId,String newOrderId,String isAPI,String status, String orderListId, Date creatDate,String remarks);
	//更新订单api表
	void updateOrderAPI(String orderId,String newOrderId);
	//修改订单状态
	public void updateShopOrder(ShopCar order);
	//更新支付时间
	void updatePayDate(OrderList ol);
	//更新订单支付状态
	void updateOrderPayFlag(String orderIds, int payFlag);
	//修改订单状态
	void updateOrderStatus(String orderIds, int status);
	//修改订单开始时间和结束时间
	void updateOrderDate(ShopCar order);
	//修改订单API开始时间和结束时间
	void updateOrderAPIDate(ShopCar order);

	void updateOrderListId(String newOrderIds);
	
	int findOrderCountByUserId(List<String> orderId, int userId);
	//保存服务详情页操作信息
	void SaveOrderDetail(OrderDetail orderDetail);
	//根据服务详情操作主键，查询
	OrderDetail getOrderDetailById(String id,int userId,List assetIdsList);
	//根据服务详情操作主键，查询
	OrderDetail getOrderAPIDetailById(String id,int userId);
	//根据服务详情操作主键，查询
	OrderDetail findOrderDetailById(String id,int userId);
	
	List<Serv> findServiceByParent(int parent);

	
}
