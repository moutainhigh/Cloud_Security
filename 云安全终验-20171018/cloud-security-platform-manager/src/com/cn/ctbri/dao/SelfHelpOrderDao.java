package com.cn.ctbri.dao;

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
    List<Serv> getService();

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
    void deleteLinkman(int contactId);
    /**
     * 查询所有服务
     */
	List findSerList(Map  map);
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
	void delServ(String serviceId);
	/**
	 * 删除服务API
	 * @param map
	 */
	void delServAPI(String serviceId);
	/**
	 * 修改api服务
	 */
	void updateAPI(Map map);
	/**
	 * 修改普通服务
	 */
	void updateServ(Map map);

	List findShopCarList(String userId, int payFlag, String orderId);

	List findShopCarAPIList(String userId, int payFlag, String orderId);

	void SaveOrderDetail(OrderDetail orderDetail);

	OrderDetail getOrderDetailById(String id, int userId, List assetIdsList);

	OrderDetail findOrderDetailById(String id, int userId);

	void insertServiceDetail(ServiceDetail sd);

	void insertScanType(Map<String, Object> map);

	String selectScanType(Map<String, Object> insertMap);

	int selectMaxScanType(Map<String, Object> insertMap);

	ServiceDetail findServiceDetail(Map<String, Object> map);

	int selectParentId(String parent);

	void delScanType(int serviceId);

	void updateServiceDetail(ServiceDetail sd);

	List<Map<String, Object>> findScanTypeList(Map<String, Object> map);

	void delServDetail(Map<String, Object> map);
	/**
     * 功能描述：防护网战数
     *       @time 2017-8-17
     */
    int findWafPageNum();
}
