package com.cn.ctbri.service;

import java.util.List;
import java.util.Map;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Factory;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
import com.cn.ctbri.entity.OrderDetail;
import com.cn.ctbri.entity.Serv;
import com.cn.ctbri.entity.ServiceDetail;
import com.cn.ctbri.entity.ServiceType;
/**
 * 创 建 人  ：  txr
 * 创建日期：  2015-1-14
 * 描        述：  自助下单层接口
 * 版        本：  1.0
 */
public interface ISelfHelpOrderService {
	
	/**
	 * 功能描述：查询服务配置类型
	 *		 @time 2015-1-14
	 * 返回值    ：  List<ServiceType>
	 */
	List<ServiceType> findServiceType();

	/**
     * 功能描述：查询厂商
     *       @time 2015-1-15
     * 返回值    ：  List<Factory>
     */
    List<Factory> findListFactory();

    /**
     * 功能描述：查询服务资产
     * @param userId 
     *       @time 2015-1-15
     * 返回值    ：  List<ServiceAsset>
     */
    List<Asset> findServiceAsset(int userId);

    /**
     * 功能描述：保存订单
     *       @time 2015-1-16
     */
    void insertOrder(Order order);

    /**
     * 功能描述：查询服务
     *       @time 2015-1-19
     * 返回值    ：  List<Serv>
     */
    List<Serv> findService();

    /**
     * 功能描述：保存联系人
     *       @time 2015-1-19
     */
    void insertLinkman(Linkman linkObj);

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
    void deleteLinkman(int contactId);

    /**
     * 查询所有服务
     */
	List findSerList(Map map);
	
	/**
	 * 添加api服务
	 */
	void insertAPI(Map map);
	/**
	 * 添加普通服务
	 */
	void insertServ(Map map);
	/**
	 * 删除服务
	 * @param map
	 */
	void delServ(Map map);
	/**
	 * 修改api服务
	 */
	void updateAPI(Map map);
	/**
	 * 修改普通服务
	 */
	void updateServ(Map map);

	List findShopCarList(String valueOf, int i, String string);

	List findShopCarAPIList(String valueOf, int i, String string);
	
	//保存服务详情页操作信息
	void SaveOrderDetail(OrderDetail orderDetail);
	//根据服务详情操作主键，查询
	OrderDetail getOrderDetailById(String id,int userId,List assetIdsList);
	//根据服务详情操作主键，查询
	OrderDetail findOrderDetailById(String id,int userId);
	/**
	 * 添加服务详情
	 * @param sd
	 */
	void insertServiceDetail(ServiceDetail sd);
	/**
	 * 添加服务频率
	 * @param map
	 */
	void insertScanType(Map<String, Object> map);
	/**
	 * 查询cs_scanType表中是否已存在相应的服务频率
	 * @param insertMap
	 */
	String selectScanType(Map<String, Object> insertMap);

	int selectMaxScanType(Map<String, Object> insertMap);

	ServiceDetail findServiceDetail(Map<String, Object> map);

	int selectParentId(String parent);

	void delScanType(int serviceId);

	void updateServiceDetail(ServiceDetail sd);

	List<Map<String, Object>> findScanTypeList(Map<String, Object> map);

	void delServDetail(Map<String, Object> map);
}
