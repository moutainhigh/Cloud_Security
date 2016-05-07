package com.cn.ctbri.dao;

import java.util.List;

import com.cn.ctbri.entity.Asset;
import com.cn.ctbri.entity.Factory;
import com.cn.ctbri.entity.Linkman;
import com.cn.ctbri.entity.Order;
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

	Serv findServiceById(int serviceId);
	 // 查询购物车列表
	List<ShopCar> findShopCarList(String userId,int payFlag,String orderId);

	  // 查询购物车-安全能力API列表
	List<ShopCar> findShopCarAPIList(String userId,int payFlag,String orderId);
	//查询结算页的订单列表
	List<ShopCar> findBuyShopList(List orderId);
}
